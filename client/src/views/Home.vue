<template>
  <div class="container" >
    <div class="row align-items-start">
    <div class="font-white mx-10 block col">
      <h2 class="main-title">
      함께하는 독서모임<br>
      MEETBOOK</h2>
    </div>
    <div class="col">
      <img src="../assets/girl.svg" alt="">
    </div>
    </div>
    <!-- <conference-slide :conferences="conferences">
    </conference-slide> -->
    <!-- <h2 class="my-3">화상회의 test</h2> -->
    <div>
      <!-- <v-btn class="primary" @click="goToMeeting()">
        입장하기
      </v-btn> -->
    </div>
  </div>  
</template>

<script>
// import HelloWorld from '../components/HelloWorld'
// import Carousel from '../components/home/Carousel'
// import ConferenceSlide from '../components/home/ConferenceSlide'


export default {
    name: 'Home',
    data(){
      return {
        conferences: [{"id":1, "title":"오이디푸스", "thumbnailUrl":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome","isActive":true},
        {"id":2, "title":"정의란 무엇인가", "thumbnailUrl":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":true},
        {"id":3, "title":"오이디푸스", "thumbnailUrl":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome","isActive":true},
        {"id":4, "title":"정의란 무엇인가", "thumbnailUrl":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
        {"id":5, "title":"오이디푸스", "thumbnailUrl":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":6, "title":"정의란 무엇인가", "thumbnailUrl":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2", "isActive":false},
        {"id":7, "title":"오이디푸스", "thumbnailUrl":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":8, "title":"정의란 무엇인가", "thumbnailUrl":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2", "isActive":false},
        {"id":9, "title":"정의란 무엇인가", "thumbnailUrl":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2", "isActive":false},]
        
      }
    },
    components: {
      // ConferenceSlide,
      // Carousel,
    },
    // 나중에 지울 것
    methods: {
      goToMeeting: function(){
        if (this.$store.state.auth.user !== null) {
          this.$router.push({ name: 'ConferenceMeeting', params:"4"})
        }
        else{
          this.$router.push({ name: 'Login' })
        }
      }
    }
  }
</script>

<style>
.bg {
  background-color: #568D6C;
}
.block {
  margin-top:0;
  height: 300px;
}

.font-black {
  color : #1E1E1E;
}
.font-white {
 color: #fff;
}

.main-title {
  font-size: 3rem;
}

/* .btn-outline {
  color: $primary;
  border-color: $primary;
} */
</style>

