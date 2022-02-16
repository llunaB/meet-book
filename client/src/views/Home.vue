<template>
<div>
  <div class="" style="background-color:#568D6C;">
    <v-container>

    <div class="d-flex flex-column justify-center align-center">
      <div class="font-white my-5">
        <div class="main-sub" style="text-align:center;">책으로 나누는 우리 이야기</div>
        <h2 class="main-title">MEETBOOK</h2>
      </div>

      <div class="mx-5">
        <div class="main-sub font-white" style="text-align:center">모임을 찾으시나요?<br>책 제목을 입력해보세요.</div>

        <div style="">
          <SearchBar />
        </div>  
      </div>
      <div>
      <div class="main-sub font-white" style="text-align:center">어떤 모임이 있는지 궁금하신가요?<br>모임을 확인해보세요.</div>
          <div class="text-center">
            <v-btn
              rounded
              text plain to="/conference"
              class="button-white"
            >
              도서별 모임 확인하기
            </v-btn>
          </div>
      </div>
      <div class="">
        <img src="../assets/girl.svg" alt="">
      </div>
    </div>
    <div>
    </div>
    </v-container>

  </div>
  </div>
</template>

<script>
import SearchBar from '@/components/SearchBar'


  export default {
    name: 'Home',
    components: {
    SearchBar,
    },

    data() {
      return {
        conferences: [],
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
  .bg {
    background-color: #568D6C;
  }

  .button-white {
    background-color:white;
  }

  .block {
    margin-top: 0;
    height: 300px;
  }

  .font-black {
    color: #1E1E1E;
  }

  .font-white {
    color: #fff;
  }

  .main-title {
    font-size: 3rem;
  }

  .main-sub {
    font-size: 1rem;
  }

</style>