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

      <v-autocomplete
        v-model="book"
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

      <v-menu
        v-model="date_menu"
        :close-on-content-click="false"
        :nudge-right="40"
        transition="scale-transition"
        offset-y
        min-width="auto"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            v-model="date"
            label="회의 일시"
            :rules="dateRules"
            
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="date"
          @input="menu2 = false"
          :min="nowDate"
          locale="ko"
          no-title
        ></v-date-picker>
      </v-menu>

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
        :rules="[v => !!v && conf_question || '비밀번호 문제를 설정할 시 비밀번호를 반드시 입력해야 합니다.']"
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
import _ from 'lodash'
import axios from 'axios'
export default {
  name: 'CreateConference',

  data: () => ({
    valid: true,
    title: '',
    titleRules: [
      v => !!v || '회의 제목은 비울 수 없습니다.',
      v => (v && v.length < 30) || '회의 제목이 너무 깁니다.',
      // v => (v && v.length <= 10) || 'Name must be less than 10 characters',
    ],
    // email: '',
    // emailRules: [
    //   v => !!v || 'E-mail is required',
    //   v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
    // ],
    max_members: null,
    max_member_items: _.range(2, 31),

    date_menu: false,
    date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
    nowDate: new Date().toISOString().slice(0,10),
    dateRules: [
      v => !!v || '날짜를 설정해주세요.',
      // v => v - this.nowDate < 0 || '오늘 이후의 날짜를 설정해주세요.',
    ],
    conf_question: null,
    conf_answer: null,
    description: '',
    book: '',
    tags: '',

  }),

  methods: {
    validate () {
      this.$refs.form.validate()
    },
    reset () {
      this.$refs.form.reset()
    },
    resetValidation () {
      this.$refs.form.resetValidation()
    },

    createConference: function () {
      // 요청값 전처리하기


      // 회의 개설 요청 보내기
      axios({
        method: 'POST',
        url: ``,
        data: {
          conference: {
            // 회의 개설 정보
            title: this.title,
            book: this.book,
            description: this.description,
            max_members: this.max_members,
            question: this.conf_question,
            password: this.conf_answer,
            call_start_time: this.date,
            tags: this.tags,

            // 서버에서 후처리할 것으로 예상되는 정보
            // call_end_time
            // 
          },
        },

      })
      .then(response => {
        // 요청이 이루어지면
        // 1. 회의가 개설되었다는 알림 (Snackbar? Modal?) 띄우기
        console.log(response)
        // 2. 일정 페이지 등으로 리다이렉트하기
      })
      .catch(error => {
        console.log(error)
      })
    }
  },
}
</script>

<style>

</style>