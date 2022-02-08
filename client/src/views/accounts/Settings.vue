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
          v-model="user.password" id="passwordEditInput" />

        <v-text-field
          type="password" label="비밀번호 수정 확인" hide-details="auto"
          v-model="user.passwordConfirm" id="passwordConfirmEditInput" />

        <br>
        <v-textarea
          type="text" label="나의 한마디" auto-grow outlined rows="3" row-height="100" shaped
          v-model="user.profileDescription" id="profileDescriptionInput"/>
      </div>

      <v-btn color="primary" text @click="{informationChange, passwordEditChange}">
        수정하기
      </v-btn>
    </form>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "Settings",
  data() {
    return {
      user: {},
      name: '',
    }
  },
  methods: {
    informationChange() {
      if (this.user.nickname.length > 0) {
        axios.put('https://localhost:8080/users/' + this.$store.state.auth.user.id,
          {
            'nickname': this.nickname,
            'profileDescription': this.profileDescription,
          })
          .then(res => console.log(res))
          .catch(e => console.log(e))
      }
      else {
        alert('닉네임을 입력하셔야 합니다.')
      }
    },
    passwordEditChange() {
      if ((this.password === this.passwordConfirm) && (this.user.password)) {
        axios.put('https://localhost:8080/users/' + this.$store.state.auth.user.id + '/detail',
          {'newPassword': this.password})
        .then(res => {
          console.log(res)
          alert("비밀번호가 수정되었습니다.")
        })
        .catch(e => console.log(e))
      }
    },
    userProfile() {
      axios.get('https://localhost:8080/users/' + this.$store.state.auth.user.id + "/detail")
        .then(res => {
          this.user = res.data
        })
        .catch(e => {
          console.log(e)
        })
    }
  },
  beforeMount() {
    this.userProfile()
  }
}
</script>

<style scoped>

</style>