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
        <p class="mt-10">책이름: {{conference.book.bookName}}</p>
        <p>회의명: {{conference.title}}</p>
        <p>개설자: {{conference.user.nickname}}</p>
        <p>{{conference.id}}</p>
        <p>시작예정: {{formating(conference.callStartTime)}}</p>
        <p>종료예정: {{formating(conference.callEndTime)}}</p>
        <p>참여인원 / 최대인원: 0 / {{conference.maxMembers}}</p>
        <p>설명: {{conference.description}}</p>
      </v-card-text>
      <v-btn class="mx-3 lock" @click="lockClick">
        <v-icon v-if="locked === false">mdi-lock-open-outline</v-icon>
        <v-icon v-else>mdi-lock</v-icon>
      </v-btn>
      <v-btn class="mx-5 mb-2 enter" @click="goToMeeting(conference.id)" v-if="isActive">
        참여하기        
      </v-btn>
      <v-btn class="mx-5 mb-2 enter" v-else @click="bookmarking">
          <v-icon v-if="bookmarked">mdi-bookmark-check</v-icon>
          <v-icon v-else>mdi-bookmark-check-outline</v-icon>
      </v-btn>
    </div>    
  </v-card>
</template>
<script>
import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "ConferenceCard",
  data(){
    return{
      locked: false,            
      isActive: null,      
      bookmarkId: null,
      bookmarked: false,
    }
  },
  props: {
    conference: Object,
  },
  methods: {
    lockClick: function() {      
      this.locked = !this.locked
    },
    bookmarking: function() {      
      if(this.bookmarked) {
        axios({
          baseURL: SERVER_URL,
          method: 'delete',
          url:`/users/${this.$store.state.auth.user.id}/bookmark/${this.bookmarkId}`,
          headers: {
          'X-AUTH-TOKEN': this.$store.state.auth.user.token
          }
        })
        .then(res=>{
          this.bookmarked = false
          console.log(res)})
        .catch(err => console.error(err))
      } else {
        axios({
          baseURL: SERVER_URL,
          method: 'post',
          url:`/users/${this.$store.state.auth.user.id}/bookmark/${this.conference.id}`,
          headers: {
          'X-AUTH-TOKEN': this.$store.state.auth.user.token
          }
        })
        .then(res=>{
          this.bookmarked = true
          this.bookmarkId = res.data.bookmarkId})
        .catch(err => console.error(err))
      }
    },
    goToMeeting: function(conferenceId){
      this.$router.push({ name: 'ConferenceMeeting', params: {conferenceId: conferenceId}})
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
    // bookCall: function(){
    //   axios({
    //     baseURL: SERVER_URL,
    //     method: 'get',
    //     url: `/books/${this.conference.bookId}`
    //   })
    //   .then(res => {
    //     console.log(this.conference.id,"bookload!!")
    //     this.book = res.data})
    //   .catch(err => console.error(err))
    // },
    activeCheck: function(){
      axios({
        baseURL: SERVER_URL,
        method: 'get',
        url: `/conference/${this.conference.id}/live`,
        headers: {
          'X-AUTH-TOKEN': this.$store.state.auth.user.token
        }
      })
      .then(res=>this.isActive=res.data)
      .catch(err=>console.error(err))
    },
    // findFounder: function(){
    //   axios({
    //     baseURL: SERVER_URL,
    //     method: 'get',
    //     url: `/users/${this.conference.userId}`,        
    //   })
    //   .then(res=>{
    //     console.log(this.conference.id,"findNickname!!")
    //     this.founder=res.data.nickname})
    //   .catch(err=>console.error(err))
    // },
    isbookmarked: function(){
      axios({
        baseURL: SERVER_URL,
        method: 'get',
        url: `/users/${this.$store.state.auth.user.id}/bookmark/${this.conference.id}`
      })
      .then(res=>{
        this.bookmarkId = res.data.id
        this.bookmarked = true
      })
      .catch(err=>console.error(err))
    }
  },  
  mounted(){
    this.$nextTick(function(){      
      this.activeCheck()      
      this.isbookmarked()
    })
  }
}
</script>
<style scoped>
.card-flip > div {
  backface-visibility: hidden;
  transition: transform 300ms;
  transition-timing-function: linear;
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