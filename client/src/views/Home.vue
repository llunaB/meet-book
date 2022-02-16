<template>
  <div>
    <div class="" style="background-color:#568D6C;">
      <v-container>

        <div class="d-flex flex-column justify-center align-center">

          <div class="font-white mt-7 mb-6">
            <div class="main-sub mt-3" style="text-align:center;">책으로 나누는 우리 이야기</div>
            <h2 class="main-title">MEETBOOK</h2>
          </div>

          <div class="my-1">
            <div class="main-sub font-white main-text text-center">
              모임을 찾으시나요?
              책 제목을 입력해보세요.</div>
            <div class="my-3">
              <SearchBar />
            </div>
          </div>

          <div class="my-3">
            <div class="main-sub font-white main-text text-center">
              어떤 모임이 있는지 궁금하신가요?
              모임을 확인해보세요.
            </div>
            <div class="text-center">
              <v-btn rounded x-large :to="{name: 'ConferenceIndex'}" class="button-white my-5">
                <span class="font-green px-2 box-shadow-none"> 👋 도서별 모임 확인하기 </span>
              </v-btn>
            </div>
          </div>
          <div class="home-image my-5" style="text-align:center;">
            <img class="main_img" src="../assets/girl.svg" alt="">
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
      </v-container>

    </div>
    <!-- <div class="" style="background-color:#F1F6F3;">
  </div> -->
  </div>
</template>

<script>
  import SearchBar from '@/components/SearchBar'


  // import HelloWorld from '../components/HelloWorld'
  // import Carousel from '../components/home/Carousel'
  // import ConferenceSlide from '../components/home/ConferenceSlide'


  export default {
    name: 'Home',
    components: {
      SearchBar,
    },

    data() {
      return {
        conferences: [{
            "id": 1,
            "title": "오이디푸스",
            "thumbnailUrl": "https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg",
            "description": "Welcome",
            "isActive": true
          },
          {
            "id": 2,
            "title": "정의란 무엇인가",
            "thumbnailUrl": "https://image.yes24.com/goods/15156691/XL",
            "description": "welcome2",
            "isActive": true
          },
          {
            "id": 3,
            "title": "오이디푸스",
            "thumbnailUrl": "https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg",
            "description": "Welcome",
            "isActive": true
          },
          {
            "id": 4,
            "title": "정의란 무엇인가",
            "thumbnailUrl": "https://image.yes24.com/goods/15156691/XL",
            "description": "welcome2",
            "isActive": false
          },
          {
            "id": 5,
            "title": "오이디푸스",
            "thumbnailUrl": "https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg",
            "description": "Welcome",
            "isActive": false
          },
          {
            "id": 6,
            "title": "정의란 무엇인가",
            "thumbnailUrl": "https://image.yes24.com/goods/15156691/XL",
            "description": "welcome2",
            "isActive": false
          },
          {
            "id": 7,
            "title": "오이디푸스",
            "thumbnailUrl": "https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg",
            "description": "Welcome",
            "isActive": false
          },
          {
            "id": 8,
            "title": "정의란 무엇인가",
            "thumbnailUrl": "https://image.yes24.com/goods/15156691/XL",
            "description": "welcome2",
            "isActive": false
          },
          {
            "id": 9,
            "title": "정의란 무엇인가",
            "thumbnailUrl": "https://image.yes24.com/goods/15156691/XL",
            "description": "welcome2",
            "isActive": false
          },
        ]

      }
    },

    // 나중에 지울 것
    methods: {
      goToMeeting: function () {
        if (this.$store.state.auth.user !== null) {
          this.$router.push({
            name: 'ConferenceMeeting',
            params: "4"
          })
        } else {
          this.$router.push({
            name: 'Login'
          })
        }
      }
    }
  }
</script>

<style>
  .main_img {
    max-width: 80%;
    height: auto;

  }

  .text-center .v-size--x-large {
    margin-top:10px !important;
  }

  .v-btn--router {
    margin: 0 !important;
  }
  .v-size--x-large {
    box-shadow: none !important;
  }

  .bg {
    background-color: #568D6C;
  }

  .button-white {
    background-color: white;
  }

  .block {
    margin-top: 0;
    height: 300px;
  }

  .font-black {
    color: #1E1E1E !important;
  }

  .font-white {
    color: #fff;
  }

  .font-green {
    color: #568D6C;
  }

  .main-title {
    font-size: 4rem;
  }

  .main-sub {
    font-size: 1rem;
    margin-bottom: -0.5rem;
  }

  .main-text {
    line-height: 2rem;
  }

  /* .btn-outline {
  color: $primary;
  border-color: $primary;
} */
</style>