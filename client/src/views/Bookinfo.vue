<template>
  <div class="row pa-1">
    <div class="col-12 col-lg-6">
      <!-- 도서 정보 탑재 부분 -->
      <div class="row">
        <div class="d-flex col-12 col-sm-4 col-lg-12 text-center justify-center">
          <v-img v-if="book.thumbnailUrl" :src="book.thumbnailUrl" max-width="240px"></v-img>
          <span v-else>썸네일 이미지가 없습니다.</span>
        </div>
        <div class="col-12 col-sm-8 col-lg-12">
          <h1>{{ book.name }}</h1>
          <h3>{{ book.author }}</h3>
          <h5 class="text-right">{{ book.publisher }} | {{ book.pubYear }}</h5>
          <v-divider light class="my-5" />
          <p class="caption text--secondary">ISBN {{ book.isbn }}</p>
          <p>{{ book.contents }}</p>
          <p v-show="!book.contents">현재 책 내용이 제공되지 않습니다.</p>
        </div>
      </div>
      
    </div>
    <!-- 도서 관련 회의 탑재 부분 -->
    <div class="col-12 col-lg-6">
      <h3>{{ book.name }}을 읽은/읽을 모임</h3> <br>

      <div v-if="conferences.length === 0">
        <div class="text-center">
          <p>아직 이 책에 관한 모임이 없어요!</p>
          <router-link :to="{name: 'CreateConference', query: {bookId: book.id}}">지금 이 책 모임 만들기</router-link>
        </div>
      </div>

      <ConferenceSlide v-else :conferences="conferences" />

    </div>
      
    <div class="col-12">
      <h3>{{ book.name }}을 읽은 사람들</h3> <br>
        <div v-if="users.length == 0">
          <div class="text-center">
            <p>아직 이 책에 대해 이야기 나눈 사람이 없어요!</p>
          </div>
        </div>
        <div v-else>
          <ProfileSmallcard v-for="(person, idx) in users" :key="idx" :person="person" />
        </div>
    </div>
  </div>
    
  
</template>

<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL
import axios from 'axios'
import ConferenceSlide from '@/components/home/ConferenceSlide'
import ProfileSmallcard from '@/components/ProfileSmallcard'
export default {
  name: "Bookinfo",
  components: {
    ConferenceSlide, ProfileSmallcard,
  },
  data: function () {
    return {
      book: {
        id: 0,
        name: '',
        author: '',
        contents: '',
        publisher: '',
        isbn: '',
        pubYear: '',
        thumbnailUrl: '',
      },

      conferences: [

      ],
      users: [
        {id: 1,
        nickname: '닉네임',
        email: 'email@mail.com',
        profile_image: ''},
        {id: 2,
        nickname: '다른사람',
        email: 'wow@logemailaddress.com',
        profile_image: ''},
      ],
    }
  },

  methods: {
    getBookDetail: function (bookId) {
      axios({
        method: 'GET',
        baseURL: SERVER_URL,
        url: `books/${bookId}`,
      })
      .then(response => {
        this.book = response.data
      })
      .catch(error => {
        console.log(error)
      })
    },

    getConfByBook: function (bookId) {
      // 책 ID로 Conf 조회하기
      axios({
        method: 'GET',
        baseURL: SERVER_URL,
        url: `conference?bookId=${bookId}/expecting_conf?page=0&size=4`,

      })
      .then(response => {
        // this.book = response.data.book
        console.log(response)
        this.conferences = response.data.content
      })
      .catch(error => {
        console.log(error)
      })
    },


  },

  mounted: function () {
    this.getBookDetail(this.$route.params.id)
    this.getConfByBook(this.$route.params.id)
  },
}
</script>

<style>
</style>