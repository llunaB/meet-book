<template>
  <div class="container">
    <div class="row pa-1">
      <!-- <div class="col-12 col-lg-6"> -->
      <div class="col-12">
        <!-- 도서 정보 탑재 부분 -->
        <div class="row">
          <div class="d-flex col-12 col-sm-4 text-center justify-center">
          <!-- <div class="d-flex col-12 col-sm-4 col-lg-12 text-center justify-center"> -->
            <v-img v-if="book.thumbnailUrl" :src="book.thumbnailUrl" max-width="240px"></v-img>
            <span v-else>썸네일 이미지가 없습니다.</span>
          </div>
          <div class="col-12 col-sm-8">
          <!-- <div class="col-12 col-sm-8 col-lg-12"> -->
            <h1>{{ book.nameFront ? book.nameFront : book.name }}</h1>
            <h2>{{ book.nameBack ? book.nameBack : '' }}</h2>
            <h3 class="text-right">{{ book.author }}</h3>
            <h3 class="text-right">{{ genreList[book.genreId] }}</h3>
            <h5 class="text-right">{{ book.publisher }} | {{ book.pubYear }}</h5>
            <v-divider light class="my-5" />
            <p class="caption text--secondary">ISBN {{ book.isbn }}</p>

            <p class="body-2">{{ book.contents }}</p>
            <p v-show="!book.contents">현재 책 내용이 제공되지 않습니다.</p>
          </div>
        </div>
        
      </div>
      <!-- 도서 관련 회의 탑재 부분 -->
      <!-- <div class="col-12 col-lg-6"> -->
      <div class="col-12">
        <h3>《{{ book.nameFront ? book.nameFront : book.name }}》을 읽은/읽을 모임</h3> <br>

        <div v-if="conferences.length === 0">
          <div class="text-center">
            <p>아직 이 책에 관한 모임이 없어요!</p>
            <router-link :to="{name: 'CreateConference', query: {bookId: book.id}}">지금 이 책 모임 만들기</router-link>
          </div>
        </div>
        <!-- <div class="d-flex flex-row">
          <div v-for="conference in conferences" :key="conference.conferenceId">
            <ConferenceCard :conference="conference" class="d-inline" />
          </div>
        </div> -->

        <ConferenceList :conferences="conferences" />

      </div>
        
      <!-- <div class="col-12">
        <h3>《{{ book.nameFront ? book.nameFront : book.name }}》을 읽은 사람들</h3> <br>
          <div v-if="users.length == 0">
            <div class="text-center">
              <p>아직 이 책에 대해 이야기 나눈 사람이 없어요!</p>
            </div>
          </div>
          <div v-else>
            <ProfileSmallcard v-for="(person, idx) in users" :key="idx" :person="person" />
          </div>
      </div> -->
    </div>
  </div>
    
  
</template>

<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL
import axios from 'axios'
// import ConferenceCard from '@/components/home/ConferenceCard'
import ConferenceList from '@/components/home/ConferenceSlide'
// import ProfileSmallcard from '@/components/ProfileSmallcard'
export default {
  name: "Bookinfo",
  components: {
    ConferenceList,
    // ProfileSmallcard,
  },

  data: function () {
    return {
      book: {
        id: 0,
        name: '로드 중입니다...',
        nameFront: undefined,
        nameBack: undefined,
        author: '',
        contents: '',
        publisher: '',
        isbn: '',
        pubYear: '',
        thumbnailUrl: '',
      },

      genreList: {
        1: '총류',
        2: '철학',
        3: '종교',
        4: '사회과학',
        5: '순수과학',
        6: '기술과학',
        7: '예술',
        8: '언어',
        9: '문학',
        10: '역사'
      },

      conferences: [],
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
        console.log(response)
        const book = response.data
        const [a, b] = this.getSlicedName(book.name)
        this.book = book
        this.book.nameFront = a
        this.book.nameBack = b

      })
      .catch(error => {
        console.log(error)
      })
    },

    getExpectingConfByBook: function (bookId) {
      // 책 ID로 Conf 조회하기
      axios({
        method: 'GET',
        baseURL: SERVER_URL,
        url: `books/${bookId}/expecting_conf?page=0&size=4`,

      })
      .then(response => {
        // this.book = response.data.book
        this.conferences.push(...response.data.content)
      })
      .catch(error => {
        console.log(error)
      })
    },

    getFinishedConfByBook: function (bookId) {
      axios({
        method: 'GET',
        baseURL: SERVER_URL,
        url: `books/${bookId}/finished_conf?page=0&size=4`,
      })
      .then(response => {
        // this.book = response.data.book
        this.conferences.push(...response.data.content)
      })
      .catch(error => {
        console.log(error)
      })
    },

    getSlicedName: function (String) {
      const slicePoint = String.indexOf(':')
      if (slicePoint === -1) {
        return [undefined, undefined]
      } else {
        return [String.slice(0, slicePoint), String.slice(slicePoint+1)]
      }
    }

  },

  mounted: function () {
    this.getBookDetail(this.$route.params.id)
    this.getExpectingConfByBook(this.$route.params.id)
    this.getFinishedConfByBook(this.$route.params.id)
  },
}
</script>

<style scoped>
  .card-base > div {
    max-width: 280px;
    max-height: 320px;
  }

</style>