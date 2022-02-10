<template>
  <v-card class="card-flip">
    <div class="card-front" v-bind:class="{frontLocked: locked}">
      <v-img :src="conference.thumbnail" class="border" :eager="true" max-width="219">
      <div v-if="conference.isActive">
        <v-btn color="red" dark>
          <v-icon dark left>mdi-broadcast</v-icon>
          LIVE
        </v-btn>
      </div>
      </v-img>
    </div>
    <div class="card-back flex-column" v-bind:class="{backLocked: locked}">
      <v-card-text>
        <p class="text-h5 mt-7">{{conference.title}}</p>
        <p>{{conference.id}}</p>
        <p>date - time</p>
        <p>참여인원 / 전체인원</p>
        <p>{{conference.description}}</p>        
      </v-card-text>
      <v-btn class="mx-3 lock" @click="lockClick">
        <v-icon v-if="locked === false">mdi-lock-open-outline</v-icon>
        <v-icon v-else>mdi-lock</v-icon>
      </v-btn>
      <v-btn class="mx-5 mb-2 enter" @click="goToMeeting(conference.id)" v-if="conference.isActive">
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
export default {
  name: "ConferenceCard",
  data(){
    return{
      locked: false,
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
      this.bookmarked = !this.bookmarked
    },
    goToMeeting: function(conferenceId){
      this.$router.push({ name: 'ConferenceMeeting', params: {conferenceId: conferenceId}})
    }
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
</style>