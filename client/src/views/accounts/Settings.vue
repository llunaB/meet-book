<template>
  <v-container style="padding: 3rem">
    <form>
      <div class="form-group">
        <v-text-field
          type="text" label="닉네임" hide-details="auto"
          v-model="user.nickname" id="nicknameInput" required/>
        <br>
        <v-text-field
          type="text" label="비밀번호 수정" hide-details="auto"
          v-model="user.password" id="passwordEditInput"
          oninput="this.value = this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' )" />

        <v-text-field
          type="password" label="비밀번호 수정 확인" hide-details="auto"
          v-model="user.passwordConfirm" id="passwordConfirmEditInput"
          oninput="this.value = this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' )" />

        <br>
        <v-textarea
          type="text" label="나의 한마디" auto-grow outlined rows="3" row-height="100" shaped
          v-model="user.profileDescription" id="profileDescriptionInput"/>
      </div>

      <v-btn color="primary" text @click="informationChange">
        수정하기
      </v-btn>
    </form>
  </v-container>
</template>

<script>
import axios from "axios"
const SERVER_URL = process.env.VUE_APP_SERVER_URL


export default {
  name: "Settings",
  data() {
    return {
      user: {},
      name: '',
      errorMessage: ['비밀번호가 다릅니다.'],
    }
  },
  methods: {
    informationChange() {
      this.passwordEditChange()
      if (this.user.nickname.length > 0) {
        axios({
          baseURL: SERVER_URL,
          url: `/users/${this.$store.state.auth.user.id}`,
          method: 'PUT',
          data: {
            'nickname': this.user.nickname,
            'profileDescription': this.user.profileDescription,
          }
        })
          .then(res => console.log(res))
          .catch(e => console.log(e))
      }
      else {
        alert('닉네임을 입력하셔야 합니다.')
      }
    },
    passwordEditChange() {
      if ((this.user.password === this.user.passwordConfirm) && (this.user.password)) {
        axios({
          baseURL: SERVER_URL,
          url: `/users/${this.$store.state.auth.user.id}/detail`,
          method: 'PUT',
          data: {'newPassword': this.user.password}
        })
        .then(() => alert("비밀번호가 수정되었습니다."))
        .catch(e => console.log(e))
      }
    },
    userProfile() {
      axios({
        baseURL: SERVER_URL,
        url:`/users/${this.$store.state.auth.user.id}/detail`
      })
        .then(res => this.user = res.data)
        .catch(e => console.log(e))
    },
  },
  beforeMount() {
    this.userProfile()
  }
}
</script>

<style scoped>

</style>