<template>
  <v-app-bar app color="#FAF6EA">
    <v-toolbar-title>
      <router-link :to="{name: 'Home'}" class="text-decoration-none">
      Meetbook
      </router-link>
    </v-toolbar-title>
    <v-btn text plain to="/conference" class="hidden-sm-and-down">
      <span class="mr-2">모임</span>
    </v-btn>
    <v-spacer></v-spacer>

    <!-- 검색 -->
    <v-text-field
      solo dense flat
      placeholder="검색"
      append-outer-icon="mdi-magnify"
      hide-details="true"
      style="max-width: 450px;"
      v-model="keyword"
      @keyup.enter="searchKeyword(keyword)"
      >
    </v-text-field>

    <v-spacer></v-spacer>

    <div class="hidden-sm-and-down">
      <div v-if="loggedIn" class="navbar-menu-loggedin">
        <v-btn
            :to="{name: 'CreateConference'}"
            text
            plain
          >
          <span class="mr-2">모임 개설</span>
        </v-btn>
        <v-btn
            to="#"
            text
            plain
          >
          <span class="mr-2">나의 일정</span>
        </v-btn>

      </div>
      <div v-else class="navbar-menu-not-loggedin">
        <v-btn
            :to="{name: 'Login'}"
            text
            plain
          >
          <span class="mr-2">로그인</span>
        </v-btn>
        <v-btn
            :to="{name: 'Signup'}"
            text
            plain
          >
          <span class="mr-2">회원가입</span>
        </v-btn>
      </div>


    </div>

    <!-- 계정 아이콘 -->
    <v-menu offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          icon
          v-bind="attrs"
          v-on="on">
          <v-avatar>
            <v-icon>
              mdi-account-circle
            </v-icon>
          </v-avatar>
        </v-btn>

      </template>

      <v-list v-if="loggedIn">
        <v-list-item
          v-for="(item, index) in profileMenuItems" :key="index" :to="item.to">
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
// import axios from 'axios'
// import { mapGetters } from 'vuex'
export default {
  name: "navbar",
  data: function () {
    return {
      login: this.$store.state.login,
      profileMenuItems: [
        { title: '내 프로필', to:'/profile',},
        { title: '계정 설정', to:'/accounts/settings',},
      ],
      shortMenuItems: [
        { title: '모임 개설', to:'CreateConference', needLogin: true},
        { title: '나의 일정', to:'#', needLogin: true},
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

    // 검색
    // Navbar에서의 검색은 실제 검색을 수행하는 것이 아니라,
    // 검색 페이지에 검색 키워드로 전달하는 방식으로 수행됩니다.
    searchKeyword: function (keyword) {
      console.log(keyword)
      this.$router.push({name: 'Search', params: {'type': 'conference'}, query: {'keyword': String(keyword), 'page': parseInt(1)}})
    },
  },

  computed: {
    // 로그인 여부 확인
    loggedIn: function () {
      return this.$store.state.auth.status.loggedIn
    },
  },
}
</script>

<style>

</style>