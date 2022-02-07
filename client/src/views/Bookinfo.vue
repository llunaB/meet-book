<template>
  <div class="row mt-5 pa-1">
    <div class="col-12 col-lg-6">
      <!-- 도서 정보 탑재 부분 -->
      <div class="row">
        <div class="d-flex col-12 col-sm-4 col-lg-12 text-center justify-center">
          <v-img v-if="book.bookThumbnailUrl" :src="book.bookThumbnailUrl" max-width="240px"></v-img>
          <span v-else>썸네일 이미지가 없습니다.</span>
        </div>
        <div class="col-12 col-sm-8 col-lg-12">
          <h1>{{ book.bookName }}</h1>
          <h3>{{ book.bookAuthor }}</h3>
          <h5 class="text-right">{{ book.bookPublisher }} | {{ book.bookPubYear }}</h5>
          <v-divider light class="my-5" />
          <p class="caption text--secondary">ISBN {{ book.isbn }}</p>
          <p>{{ book.bookContents }}</p>
          <p v-show="!book.bookContents">현재 책 내용이 제공되지 않습니다.</p>
        </div>
      </div>
      
    </div>
    <!-- 도서 관련 회의 탑재 부분 -->
    <div class="col-12 col-lg-6">
      <h3>{{ book.title }}을 읽은/읽을 모임</h3> <br>

      <div v-if="conferences.length == 0">
        <div class="text-center">
          <p>아직 이 책에 관한 모임이 없어요!</p>
          <router-link to="#">지금 이 책 모임 만들기</router-link>
        </div>
      </div>

      <ConferenceSlide v-else :conferences="conferences" />

    </div>
      
    <div class="col-12">
      <h3>{{ book.title }}을 읽은 사람들</h3> <br>
        <div v-if="people.length == 0">
          <div class="text-center">
            <p>아직 이 책에 대해 이야기 나눈 사람이 없어요!</p>
          </div>
        </div>
        <div v-else>
          <ProfileSmallcard v-for="(person, idx) in people" :key="idx" :person="person" />
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
        id: 1,
        bookName: '책 제목',
        bookAuthor: '작가',
        bookContents: '여기는 책의 설명이 들어가는 부분입니다. 적당히 길 수도 있습니다.',
        bookPublisher: '출판사',
        isbn: '9791190893442',
        bookPubYear: '2000',
        bookThumbnailUrl: 'https://image.aladin.co.kr/product/25241/8/cover/k872633007_1.jpg',
      },
      // conferences: [{"id":1, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome","isActive":true},
      //   {"id":2, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":true},
      //   {"id":3, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome","isActive":true},
      //   {"id":4, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
      //   {"id":5, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
      //   {"id":6, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2", "isActive":false},
      //   {"id":7, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
      //   {"id":8, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2", "isActive":false},
      // ],
      conferences: [

      ],
      people: [
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
        console.log(response.data)
        this.book = response.data
      })
      .catch(error => {
        console.log(error)
      })
    },

    getConfByBook: function (bookId) {
      axios({
        method: 'GET',
        baseURL: SERVER_URL,
        url: `conference?bookId=${bookId}`
      })
      .then(response => {
        // this.book = response.data.book
        
        console.log(response)
        // this.conferences = response.data.---
      })
      .catch(error => {
        console.log(error)
      })
      console.log(bookId)
      // 책 ID로 Conf 조회하기
    },


  },

  mounted: function () {
    this.getBookDetail(this.$route.params.id)
    // this.getConfByBook(this.$route.params.id)
  },
}
</script>

<style>

</style>