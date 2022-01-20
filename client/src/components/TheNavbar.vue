<template>
  <v-app-bar
    app
    color="#FAF6EA"
    >
    <v-toolbar-title>Meetbook</v-toolbar-title>
    <v-btn
        to="/conf/list"
        text
        plain
      >
      <span class="mr-2">모임</span>
    </v-btn>
    <v-spacer></v-spacer>

    <!-- 검색 -->
    <v-text-field
      solo
      placeholder="검색"
      append-outer-icon="mdi-magnify"
      hide-details="true"
      style="max-width: 450px;"

      :model="keyword"
      >
    </v-text-field>

    <v-spacer></v-spacer>

    <div v-if="login" class="navbar-menu-loggedin">
      <v-btn
          to="/conf/create"
          text
          plain
        >
        <span class="mr-2">모임 개설</span>
      </v-btn>
      <v-btn
          to="/conf/schedule"
          text
          plain
        >
        <span class="mr-2">나의 일정</span>
      </v-btn>

    </div>
    <div v-else class="navbar-menu-not-loggedin">
      <v-btn
          to="/login"
          text
          plain
        >
        <span class="mr-2">로그인</span>
      </v-btn>
      <v-btn
          to="/signup"
          text
          plain
        >
        <span class="mr-2">회원가입</span>
      </v-btn>
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

      <v-list>
        <v-list-item
          v-for="(item, index) in profileMenuItems" :key="index">
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

  </v-app-bar>
</template>

<script>
export default {
  name: "navbar",
  data: function () {
    return {
      login: this.$store.state.login,
      profileMenuItems: [
        { title: '내 프로필', to:'/profile',},
        { title: '계정 설정', to:'/accounts/settings',},
        { title: '로그아웃', to:'/logout',},
      ],
      keyword: '',
    }
  },

  methods: {

  },
}
</script>

<style>

</style>