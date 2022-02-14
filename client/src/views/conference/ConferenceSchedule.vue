<template>
  <div class="row">
    <div class="col-12 col-md-10 offset-md-1">
      <p>
      {{ auth.user.nickname }}님의 회의 정보입니다.
      </p>
    </div>

    <div class="col-12 col-md-10 offset-md-1">
      <p>북마크한 모임</p>
      <div v-show="bookmarkedConference.length === 0"><p>현재 북마크한 모임이 없습니다. <br> <router-link :to="{name: 'ConferenceIndex'}">모임 찾아보기</router-link></p></div>
      <div v-if="bookmarkedConference.length > 0">
        <ConferenceCard class="mx-2 my-2 col-2" v-for="conference in bookmarkedConference" :key="conference.id" :conference="conference" />
      </div>
    </div>

    <div class="col-12 col-md-10 offset-md-1">
      <p>개설한 모임</p>
      <div v-show="createdConference.length === 0"><p>아직 개설한 모임이 없습니다. <br> <router-link :to="{name: 'CreateConference'}">모임 만들기</router-link></p></div>
      <div v-if="createdConference.length > 0">
        <ConferenceCard class="mx-2 my-2 col-2" v-for="conference in createdConference" :key="conference.id" :conference="conference" />
      </div>
    </div>
    
    <div class="col-12 col-md-10 offset-md-1">
      <p>참여한 모임</p>
      <div v-show="joinedConference.length === 0"><p>아직 참여한 모임이 없습니다. <br> <router-link :to="{name: 'ConferenceIndex'}">모임 찾아보기</router-link></p></div>
      <div v-if="joinedConference.length > 0">
        <ConferenceCard class="mx-2 my-2 col-2" v-for="conference in joinedConference" :key="conference.id" :conference="conference" />
      </div>
    </div>

  </div>
</template>

<script>
import { mapState } from 'vuex'
import axios from 'axios'
import ConferenceCard from '@/components/home/ConferenceCard'
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  name: 'ConferenceSchedule',
  components: {
    ConferenceCard,
  },
  data: function () {
    return {
      // 표시할 세 가지 정보
      bookmarkedConference: [],
      createdConference: [],
      joinedConference: [],
    }
  },
  methods: {
    getBookmarkedConference: function (userId) {
      axios({
        method: 'GET',
        baseURL: SERVER_URL,
        url: `/users/${userId}/bookmark`
      })
      .then(response => {
        // 북마크한 회의 담기
        this.bookmarkedConference = response.data
      })
      .catch(error => {
        console.log(error)
      })

    }
  },

  computed: {

    // Vuex > auth.module.js에 담긴 정보 사용
    ...mapState([ 'auth' ])
  },

  created: function () {
    // 페이지 로드 시 로그인한 유저 기준으로 정보 불러오기
    this.getBookmarkedConference(this.auth.user.id)
  }

}
</script>

<style>

</style>