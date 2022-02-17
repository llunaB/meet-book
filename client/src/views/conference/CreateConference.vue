<template>
  <div class="col-12 col-md-10 offset-md-1">
    <v-form
      ref="form"
      v-model="valid"
      lazy-validation
    >
      <v-text-field
        v-model="title"
        :rules="titleRules"
        label="회의 제목"
        required
      ></v-text-field>

      <v-img v-if="!!book" :src="book.thumbnailUrl" max-height="540px" contain></v-img>

      <v-autocomplete
        v-model="book"
        clearable
        hide-no-data
        return-object
        :items="bookItems"
        :loading="bookLoading"
        :search-input.sync="bookSearch"
        item-text="shortName"
        item-value="id"
        label="도서">
      </v-autocomplete>
      <!-- Autocomplete는 작성할 때 DB에서 책 제목을 찾게 되므로 서버 부하를 고려하여 여유 시간을 두고 로드하게 만들 것 -->

      <v-select
        v-model="max_members"
        :items="max_member_items"
        :rules="[v => !!v || '인원을 설정해주세요.']"
        label="제한 인원"
        required
      ></v-select>

      <v-text-field type="datetime-local" :value="date_time" v-model="date_time" :min="date_time_now" required>
      </v-text-field>


      <v-text-field
        v-model="tags"
        :counter="10"
        label="태그"
        required
      ></v-text-field>

      <v-textarea
        v-model="description"
        label="설명"
        required
      ></v-textarea>

      <!-- 회의 비밀번호 설정 -->

      <v-text-field
        v-model="conf_question"
        label="비밀번호 문제"
      ></v-text-field>

      <v-text-field
        v-model="conf_answer"
        label="모임 비밀번호"
      ></v-text-field>

      <v-btn
        :disabled="!valid"
        color="success"
        class="mr-4"
        @click="validate"
      >
        모임 개설하기
      </v-btn>

      <v-btn
        color="error"
        class="mr-4"
        @click="reset"
      >
        내용 비우기
      </v-btn>

      <v-btn
        color="warning"
        @click="$router.go(-1)"
      >
        뒤로
      </v-btn>
    </v-form>
  </div>
</template>

<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL

import _ from 'lodash'
import axios from 'axios'
import moment from 'moment'
import { mapState } from 'vuex'
// import authheader from "@/services/auth-header"
export default {
  name: 'CreateConference',
  data: () => ({
    valid: true,
    title: '',
    titleRules: [
      v => !!v || '회의 제목은 비울 수 없습니다.',
      v => (v && v.length < 30) || '회의 제목이 너무 깁니다.',
    ],
    
    max_members: null,
    max_member_items: _.range(2, 31),

    date_time: moment().add(1, 'hours').format('YYYY-MM-DDTHH:00'),
    date_time_now: moment().format('YYYY-MM-DDTHH:mm'),

    // date_time: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 14) + "00",
    // date_time_now: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 14) + "00",

    date_menu: false,
    date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
    nowDate: new Date().toISOString().slice(0,10),
    dateRules: [
      v => !!v || '날짜를 설정해주세요.',
      // v => v - this.nowDate < 0 || '오늘 이후의 날짜를 설정해주세요.',
    ],
    conf_question: '',
    conf_answer: '',

    // conf_answerRules: [
    //   v => !(!!this.conf_question&& !v) || '모임 비밀번호 없이 비밀번호 문제만 설정할 수 없습니다.',
    // ],


    description: '',
    thumbnailUrl: '',

    book: {},
    bookSearch: null,
    bookLoading: false,
    nameLimit: 20,
    bookEntries: [],
    tags: '',

  }),

  methods: {
    validate () {
      const validate = this.$refs.form.validate()
      if (validate) {
        this.createConference()
      }
    },
    reset () {
      this.$refs.form.reset()
    },
    resetValidation () {
      this.$refs.form.resetValidation()
    },


    createConference: function () {
      
      // 로그인한 사용자인지 확인
      if (!this.$store.state.auth.status.loggedIn) {
        this.$router.push({name: 'Login'})
        return
      }
      // date_time: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 14) + "00",
      
      const endTime = moment(this.date_time, 'YYYY-MM-DDTHH:mm').add(1, 'hours').format('YYYY-MM-DDTHH:mm')
      
      

      const conference = {
        userId: this.$store.state.auth.user.id,
        title: this.title,
        bookId: this.book.id,
        callEndTime: endTime,
        callStartTime: this.date_time,
        description: this.description,
        maxMembers: this.max_members,
        password: this.conf_answer,
        question: this.conf_question,
        tags: this.tags,
        thumbnailUrl: this.thumbnailUrl ? this.thumbnailUrl : this.book.thumbnailUrl,
      }

      console.log(conference)
      // console.log(authheader())
      // 회의 개설 요청 보내기
      axios({
        method: 'POST',
        baseURL: SERVER_URL,
        url: '/conference',
        headers: {
          'X-AUTH-TOKEN': this.$store.state.auth.user.token
        },
        // headers: authheader(),
        data: conference,
      })
      .then(response => {
        // 요청이 이루어지면
        // 1. 회의가 개설되었다는 알림 (Snackbar? Modal?) 띄우기
        
        console.log(response)
        // 2. 일정 페이지 등으로 리다이렉트하기
        this.$router.push({name: 'Profile'})
      })
      .catch(error => {
        console.log(error)
        
      })
    },

    fetchEntriesDebounced: function () {
      clearTimeout(this._timerId)
      this._timerId = setTimeout(() => {
        axios({
        method: 'GET',
        baseURL: SERVER_URL,
        url: `/search/book?book_name=${this.bookSearch}&page=0&size=10`,
      })
      .then(response => {
        console.log('searching')
        this.bookEntries = response.data.content
      })
      .catch(error => {
        console.log(error)
      })
      .finally(() => (this.bookLoading = false))
        
      }, 500)
      },
  },

  computed: {
    user: function () {
      return this.$store.state.auth.status.user
    },

    bookItems: function () {
      return this.bookEntries.map(entry => {
        const id = entry.id
        const shortName = entry.name.length > this.nameLimit
          ? entry.name.slice(0, this.nameLimit) + '...'
          : entry.name
        
        return Object.assign({}, entry, {id: id, shortName: shortName})
      })
    },
    ...mapState(['auth'])
  },

  watch: {
    bookSearch () {
      if (this.bookLoading) return
      this.bookLoading = true

      this.fetchEntriesDebounced()
    },
  },

  created: function () {
    if (!this.auth.status.loggedIn) {
      alert('로그인이 필요한 기능입니다.')
      this.$router.push({name: 'Home'})
    }
  },

  mounted: function () {
    if (this.$route.query.bookId) {
      axios({
        method: 'GET',
        baseURL: SERVER_URL,
        url: `books/${this.$route.query.bookId}`,
      })
      .then(response => {
        this.book = response.data
        this.bookEntries = [ this.book ]
      })
      .catch(error => {
        console.log(error)
      })
    } else {
      axios({
        method: 'GET',
        baseURL: SERVER_URL,
        url: `/search/book?book_name=&page=0&size=10`,
      })
      .then(response => {
        console.log('searching')
        this.bookEntries = response.data.content
      })
      .catch(error => {
        console.log(error)
      })
      .finally(() => (this.bookLoading = false))
    }
  }
  
}
</script>

<style>

</style>