<template>
  <div class="row mt-5">
    <div class="col-12 col-md-10 offset-md-1">
      <!-- 도서 정보 탑재 부분 -->
      <div class="row">
        <div class="col-2">
          <v-img v-if="book.thumbnail_url" :src="book.thumbnail_url" max-width="420px"></v-img>
          <span v-else>썸네일 이미지가 없습니다.</span>
        </div>
        <div class="col-10">
          <h1>{{ book.title }}</h1>
          <h3>{{ book.author }}</h3>
          <h5 class="text-right">{{ book.publisher }} | {{ book.pubdate }}</h5>
          <v-divider light class="my-5" />
          <p class="caption text--secondary">ISBN {{ book.isbn }}</p>
          <p>{{ book.contents }}</p>
        </div>
      </div>
      <!-- 도서 관련 회의 탑재 부분 -->
      <div class="row mt-5">
        <div class="col-12">
          <h3>{{ book.title }}을 읽은/읽을 모임</h3> <br>

          <div v-if="conferences.length == 0">
            <div class="text-center">
              <p>아직 이 책에 관한 모임이 없어요!</p>
              <router-link to="#">지금 이 책 모임 만들기</router-link>
            </div>
          </div>

          <ConferenceSlide v-else :conferences="conferences" />

        </div>
      </div>
      <div class="row mt-5">
        <div class="col-12">
          <h3>{{ book.title }}을 읽은 사람들</h3> <br>
            <div v-if="people.length == 0">
              <div class="text-center">
                <p>아직 이 책에 대해 이야기 나눈 사람이 없어요!</p>
              </div>
            </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const BASE_URL = ''
import axios from 'axios'
import ConferenceSlide from '@/components/home/ConferenceSlide'
export default {
  name: "Bookinfo",
  components: {
    ConferenceSlide,
  },
  data: function () {
    return {
      book: {
        id: this.$route.params.id,
        title: '책 제목',
        author: '작가',
        contents: '여기는 책의 설명이 들어가는 부분입니다. 적당히 길 수도 있습니다.',
        publisher: '출판사',
        isbn: '9791190893442',
        pubdate: '2000',
        thumbnail_url: 'https://image.aladin.co.kr/product/25975/45/cover/k682737225_1.jpg',
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

      ],
    }
  },

  methods: {
    getBookDetail: function (bookId) {
      axios({
        method: 'GET',
        url: `${BASE_URL}/api/book/${bookId}`,
      })
      .then(response => {
        this.book = response.data
      })
      .catch(error => {
        console.log(error)
      })
    },

    getConfByBook: function (bookId) {
      axios({
        method: 'GET',
        // 정확히 책의 ID를 알고 있을 때 회의 정보를 불러올 수 있는 API 서버 응답이 필요함 -- 백엔드 분들 도와줘요!
        // 이런 느낌의 URL이 되지 않을까?
        url: `${BASE_URL}/conference?bookId=${bookId}`
      })
      .then(response => {
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
    this.getConfByBook(this.$route.params.id)
  },
}
</script>

<style>

</style>