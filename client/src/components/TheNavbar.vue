<template>
  <v-app-bar app elevate-on-scroll color='#568D6C' width="100%">
    <v-toolbar-title style="align-items: flex-end;">
      <router-link :to="{name: 'Home'}" class="text-decoration-none">
        <v-img class="logo" src='@/assets/MEETBOOK.svg'/>
      </router-link>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <div class="hidden-sm-and-down">
      <div v-if="loggedIn" class="navbar-menu-loggedin d-flex">
        <v-btn
            :to="{name: 'CreateConference'}"
            text
            plain
          >
          <span class="mr-2">모임 개설</span>
        </v-btn>
        <v-btn
            :to="{name: 'ConferenceSchedule'}"
            text
            plain
          >
          <span class="mr-2">나의 일정</span>
        </v-btn>

      </div>
      <div v-else class="navbar-menu-not-loggedin d-flex">
        <v-btn
            :to="{name: 'Login'}"
            text
          >
          <span class="mr-2 font-white">로그인</span>
        </v-btn>
        <v-btn
            :to="{name: 'Signup'}"
            raised=0
          >
          <span class="mr-2 font-green">회원가입</span>
        </v-btn>
      </div>


    </div>

    <!-- 계정 아이콘 -->
    <v-menu v-if="loggedIn" offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          icon v-bind="attrs" v-on="on">
          <v-avatar v-if="user.profileImage">
            <v-img :src="user.profileImage" contain />
          </v-avatar>
          <v-avatar v-else>
            <v-img src="@/assets/host_img/HostImg.png" />
          </v-avatar>
        </v-btn>
      </template>

      <v-list v-if="loggedIn">
        <v-list-item
          v-for="(item, index) in profileMenuItems" :key="index" :to="{path: item.to, query: {data: JSON.stringify({userId: user.id})}}">
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>

        <!-- router 대신 다른 작업을 해야 하는 로그아웃만 따로 떼어서 작성 -->
        <v-list-item @click="logout">
          <v-list-item-title>로그아웃</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

    <!-- 작은 화면에서 보일 공간 -->
    <v-menu offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-btn  class="hidden-md-and-up"
          icon
          v-bind="attrs"
          v-on="on">
            <v-icon>
              mdi-dots-vertical
            </v-icon>
        </v-btn>
      </template>

      <v-list>
        <v-list-item :to="{name: 'ConferenceIndex'}">
          <v-list-item-title>모임</v-list-item-title>
        </v-list-item>
        <div v-for="(item, index) in shortMenuItems" :key="index">
          <v-list-item v-if="item.needLogin === loggedIn" :to="{name: item.to}">
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </div>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
export default {
  name: "navbar",
  data: function () {
    return {
      user: null,
      login: this.$store.state.login,
      profileMenuItems: [
        { title: '내 프로필', to: '/profile' },
        { title: '계정 설정', to:'/accounts/settings' },
      ],
      shortMenuItems: [
        { title: '모임 개설', to:'CreateConference', needLogin: true},
        { title: '나의 일정', to:'ConferenceSchedule', needLogin: true},
        { title: '로그인', to:'Login', needLogin: false},
        { title: '회원가입', to:'Signup', needLogin: false},
      ],
      keyword: '',
    }
  },

  methods: {
    // 로그아웃
    logout: function () {
      this.$store.dispatch('auth/logout')
      this.$router.push('/')
    },

    
  },

  computed: {
    // 로그인 여부 확인
    loggedIn: function () {
      return this.$store.state.auth.status.loggedIn
    },
  },
  mounted() {
    this.user = this.$store.state.auth.user
  }
}
</script>

<style>

.logo {
  margin-left: 1rem;
}
.v-application--wrap > .v-app-bar--hide-shadow {
  height: 65px !important;
}
.v-toolbar__content {
  padding: 1rem !important;
}
.v-btn--is-elevated {
  box-shadow: none !important;
}
.v-app-bar--hide-shadow {
  border-bottom: 1px solid #C8DBCF !important;
  
}
span {
  font-size: 20px;
}

</style>