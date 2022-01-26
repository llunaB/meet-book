<template>
<v-container>
  <div v-if="!session">
    <v-btn color="primary" @click="joinSession()">
      준비 다 되셨나요???
    </v-btn>
  </div>  
  <div v-if="session">
    <div>
      <h1>{{ mySessionId }}</h1>
      <v-btn @click="leaveSession" color="error">
        방에서 나가기
      </v-btn>
    </div>
    <v-row>
      <v-col cols="6">
        <user-video :stream-manager="mainStreamManager"/>
      </v-col>
      <v-col cols="6">
        <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
      </v-col>
    </v-row>
  </div>
</v-container>
</template>
<script>
import axios from 'axios'
import { OpenVidu } from 'openvidu-browser'
import UserVideo from '@/components/conference/UserVideo'
axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "ConferenceMeeting",
  data(){
    return {
      OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],

      mySessionId: null,
      myUserName: 'chan'
    }
  },
  components:{
    UserVideo
  },
  created() {
    this.mySessionId = String(this.$route.params.conferenceId)
  },
  methods: {
    joinSession () {
      this.OV = new OpenVidu();
      this.session = this.OV.initSession();

      this.session.on('streamCreated', ({ stream }) => {
        const subscriber = this.session.subscribe(stream)
        this.subscribers.push(subscriber)
      })

      this.session.on('streamDestroyed', ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0)
        if (index >= 0) {
          this.subscribers.splice(index, 1)
        }
      })

      this.session.on('exception', ({ exception }) => {
        console.warn(exception)
      })

      // user token
      this.getToken(this.mySessionId).then(token => {
        this.session.connect(token, { clientData: this.myUserName})
          .then(() => {
            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
            })

            this.mainStreamManager = publisher
            this.publisher = publisher

            this.session.publish(this.publisher)
          })
          .catch(error => {
            console.log('There was an error connecting to the session:', error.code, error.message);
          })
      })

      window.addEventListener('beforeunload', this.leaveSession)
    },
    // 위까지가 joinSession

    leaveSession () {
      if (this.session) this.session.disconnect()

      this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

      window.removeEventListener('beforeunload', this.leaveSession);
      this.$router.push({ name: 'Home'})
    },

    // 여기까지 leaveSession

    updateMainVideoStreamManager (stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    // 여기서부터 server와 상호작용
    getToken (mySessionId) {      
      return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId))
    },

    createSession (sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
            customSessionId: sessionId,
          }), {
            auth: {
              username: 'OPENVIDUAPP',
              password: OPENVIDU_SERVER_SECRET,
            },
          })
          .then(response => response.data)
          .then(data => resolve(data.id))
          .catch(error => {
            if(error.response.status === 409) {
              resolve(sessionId)
            } else {
              console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
							reject(error.response);
            }
          })
      })
    },

    //여기까지 createSession

    createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},
  }
}
</script>