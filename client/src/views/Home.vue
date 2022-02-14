<template>
  <div class="container">
    <carousel>
    </carousel>
    <h2 class="my-3">지금 가장 핫한 모임</h2>
    <!-- <conference-slide :conferences="conferences">
    </conference-slide> -->
    <h2 class="my-3">화상회의 test</h2>
    <div>
      <v-btn @click="goToMeeting()">
        입장하기
      </v-btn>
    </div>
  </div>  
</template>

<script>
// import HelloWorld from '../components/HelloWorld'
import Carousel from '../components/home/Carousel'
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
      Carousel,      
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
