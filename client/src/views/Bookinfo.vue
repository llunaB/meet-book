<template>
  <div class="row mt-5">
    <div class="col-sm-12 col-10 offset-md-1">
      <!-- 도서 정보 탑재 부분 -->
      <div class="row">
        <div class="col-4">
          <v-img v-if="book.thumbnail_url" :src="book.thumbnail_url" max-width="420px"></v-img>
          <span v-else>썸네일 이미지가 없습니다.</span>
        </div>
        <div class="col-8">
          <h1>{{ book.title }}</h1>
          <p>책 설명~</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const BASE_URL = ''
import axios from 'axios'
export default {
  name: "Bookinfo",
  data: function () {
    return {
      book: {
        id: this.$route.params.id,
        title: '책 제목',
        author: '작가',
        contents: '여기는 책의 설명이 들어가는 부분입니다. 적당히 길 수도 있습니다.',
        publisher: '출판사',
        isbn: '123-12345-123124',
        pubdate: '2000',
        thumbnail_url: 'https://image.aladin.co.kr/product/25975/45/cover/k682737225_1.jpg',
      }
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

    // getConfByBook: function (bookId) {
      // 책 ID로 Conf 조회하기
    // },


  },

  mounted: function () {
    // this.getBookDetail(this.$route.params.id)
  },
}
</script>

<style>

</style>