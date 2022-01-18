<template>
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
          :rules="[v => !!v || '날짜를 설정해주세요.']"
          readonly
          v-bind="attrs"
          v-on="on"
        ></v-text-field>
      </template>
      <v-date-picker
        v-model="date"
        @input="menu2 = false"
        :min="nowDate"
      ></v-date-picker>
    </v-menu>

    <v-text-field
      v-model="name"
      :counter="10"
      :rules="nameRules"
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


    <!-- <v-checkbox
      v-model="checkbox"
      :rules="[v => !!v || 'You must agree to continue!']"
      label="Do you agree?"
      required
    ></v-checkbox> -->

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
      @click="resetValidation"
    >
      뒤로
    </v-btn>
  </v-form>
</template>

<script>
import _ from 'lodash'
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

      conf_question: null,
      conf_answer: null,

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
    },
}
</script>

<style>

</style>