<template>
  <v-card class="card-flip">
    <div class="card-front" v-bind:class="{frontLocked: locked}">
      <v-img v-if="conference.thumbnailUrl" :src="conference.thumbnailUrl" class="border" eager max-width="219">
        <div v-if="isActive">
          <v-btn color="red livecast" dark>
            <v-icon dark left>mdi-broadcast</v-icon>
            LIVE
          </v-btn>
        </div>
      </v-img>
      <v-img v-else :src="conference.book.bookThumbnailUrl" class="border" eager max-width="219">
        <div v-if="isActive">
        <v-btn color="red" dark>
          <v-icon dark left>mdi-broadcast</v-icon>
          LIVE
        </v-btn>
      </div>
      </v-img>
    </div>
    <div class="card-back flex-column" v-bind:class="{backLocked: locked}">
      <v-card-text class="description">
        <h3>{{conference.title}}</h3>
        <h4>{{conference.user.nickname}}</h4>
        <p class="mt-3">{{conference.description}}</p>
        <p class="mt-5">책: {{conference.book.bookName}}</p>
        <!-- <p>{{conference.id}}</p> -->
        <p>시작예정: {{formating(conference.callStartTime)}}</p>
        <p>종료예정: {{formating(conference.callEndTime)}}</p>
        <p>참여인원 / 최대인원: {{conference.attendMember}} / {{conference.maxMembers}}</p>
      </v-card-text>
      <v-btn class="mx-3 lock" @click="lockClick">
        <v-icon v-if="locked === false">mdi-lock-open-outline</v-icon>
        <v-icon v-else>mdi-lock</v-icon>
      </v-btn>
      <v-btn class="mx-5 mb-2 enter" @click="goToMeeting(conference.id)" v-if="isActive && (conference.attendMember < conference.maxMembers)">
        참여하기        
      </v-btn>
      <v-btn class="mx-5 mb-2 enter" v-else-if="conference.attendMember >= conference.maxMembers">
        최대인원        
      </v-btn>
      <v-btn class="mx-5 mb-2 enter" @click="goToMeeting(conference.id)" v-else-if="(userId === conference.user.id) 
      && canOpen()">
        개설하기
      </v-btn>
      <v-btn class="mx-5 mb-2 enter" v-else @click="setBookmark">
        <v-icon v-show="isBookmarked">mdi-bookmark-check</v-icon>
        <v-icon v-show="!isBookmarked">mdi-bookmark-check-outline</v-icon>
      </v-btn>
    </div>    
  </v-card>
</template>
<script>
import axios from 'axios'
import { mapState } from 'vuex'
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "ConferenceCard",
  data(){
    return{
      userId: -1,
      locked: false,            
      isActive: false,      
      isBookmarked: false,
    }
  },
  props: {
    conference: Object,
  },

  methods: {
    lockClick: function() {      
      this.locked = !this.locked
    },

    setBookmark: function () {
      // 로그인되어야 동작
      if (this.auth.user) {
        console.log(this.auth.user)
        axios({
          baseURL: SERVER_URL,
          method: 'POST',
          // 추후 수정되기를...
          url: `/users/${this.auth.user.id}/bookmark/${this.conference.id}/toggle`,
          headers: {'X-AUTH-TOKEN': this.auth.user.token}
        })
        .then(() => {
          // toggle
          // 응답값을 넣는 방식으로 추후 수정할 것 (실제로 반영이 되었는지 check하기 위함)
          this.isBookmarked = !this.isBookmarked
        })
        .catch(error => {
          console.log(`SetBookmarking ${this.conference.id} failed.`)
          console.log(error)
        })
      } else {
        // 로그인해주세요!
        this.$router.push({name: 'Login'})
      }
    },
    
    goToMeeting: function(conferenceId){
      if (this.$store.state.auth.user !== null) {
        this.$router.push({ name: 'ConferenceMeeting', params: {conferenceId: conferenceId}})
      }
      else{
        this.$router.push({name: 'Login'})
      }
    },
    formating: function(time){
      if(time){
        const date = time.split("T")[0]
        const clock = time.split("T")[1].split(".")[0]
        const dateSplited = date.split("-")
        const year = dateSplited[0]
        const month = dateSplited[1]
        const day = dateSplited[2]
        return `${year.slice(2,4)}.${month}.${day} ${clock.slice(0,5)}`
      } else {
        return null
      }
    },
    
    canOpen: function(){
      const startTime = new Date(this.conference.callStartTime)
      const startval = startTime.valueOf() - 32400000
      const endTime = new Date(this.conference.callEndTime)
      const endval = endTime.valueOf() - 32400000
      const now = new Date()
      const nowval = now.valueOf()
      return (startval < nowval) && (nowval < endval)
    }
  },

  computed: {
    ...mapState(['auth'])
  },

  created: function () {
    
    this.userId = this.auth.user ? this.auth.user.id : null
    // 로그인한 유저라면
    if (this.auth.user) {
      // 북마크 유저 목록에 내가 존재하는지 확인
      this.isBookmarked = this.conference.bookmark.some((e) => {
        if (e === this.auth.user.id) {
          return true
        }
      })
    }
    
    // 참여 인원이 있다면 Active(=)
    if (this.conference.attendMember > 0) {
      this.isActive = true
    }
  },

}
</script>
<style scoped>
.card-flip > div {
  backface-visibility: hidden;
  transition: transform 300ms;
  transition-timing-function: linear;
  min-width: 220px;

  width: 100%;
  height: 100%;
  margin: 0;
  display: flex;
}

.card-front {
  transform: rotateY(0deg);
  justify-content: center;
}

.card-back {
  transform: rotateY(180deg);
  position: absolute;
  top: 0;
}

.card-flip:hover .card-front {
  transform: rotateY(-180deg);
}

.card-flip:hover .card-back {
  transform: rotateY(0deg);
}

.frontLocked {
  transform: rotateY(-180deg);
}

.backLocked {
  transform: rotateY(0deg);
}

.lock {
  position: absolute;
  right: 0;
}

.enter {
  position: absolute;
  right: 0;
  bottom: 0;
}

.description p{
  margin-bottom: 8px;
}

.livecast {
  animation: pulse 1500ms infinite;
}

@keyframes pulse {
  0% {
    box-shadow: red 0 0 0 0;
  }
  75% {
    box-shadow: #ff000000 0 0 0 16px;
  }
}

</style>