<template>
  <v-container style="padding: 3rem">
    <form>
      <div class="form-group">
        <v-text-field
          type="text" label="닉네임" hide-details="auto"
          v-model="user.nickname" id="nicknameInput" required/>
        <br>
        <v-text-field
          type="password" label="현재 비밀번호" hide-details="auto"
          v-model="oldPassword" id="oldPasswordEditInput"
          oninput="this.value = this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' )" />

        <v-text-field
          type="password" label="비밀번호 수정" hide-details="auto"
          v-model="newPassword" id="passwordEditInput"
          oninput="this.value = this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' )" />

        <v-text-field
          type="password" label="비밀번호 수정 확인" hide-details="auto"
          v-model="newPasswordConfirm" id="passwordConfirmEditInput" :rule="rule"
          oninput="this.value = this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' )" />

        <br>
        <!-- 나이 및 성별 값 입력 Form -->
        <div class="row align-self-center">
          <div class="col">
            <p>나이</p>
            <v-text-field style="padding-top: 0" id="user-birth-input" dense
                          type="date" v-model="user.birth" /></div>
          <div class="col">
            <p>성별</p>
            <v-container fluid style="padding-top: 0">
              <v-radio-group v-model="user.gender" row style="margin: 0;">
                <v-radio v-for="n in 2" :key="n" :label=genderGroup[n] :value="n" />
              </v-radio-group>
            </v-container>
          </div>
        </div>
        <v-textarea
          type="text" label="나의 한마디" auto-grow outlined rows="3" row-height="100" shaped
          v-model="user.profileDescription" id="profileDescriptionInput"/>
      </div>

      <div class="col justify-around">
        <v-btn color="primary" text @click="informationChange">
          수정하기
        </v-btn>

        <v-dialog
          v-model="resignBtn" persistent max-width="290"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark v-bind="attrs" v-on="on"
            >탈퇴하기</v-btn>
          </template>
          <v-card>
            <v-card-title class="text-h5">
              정말 탈퇴하시겠습니까??
            </v-card-title>
            <v-card-text>탈퇴를 하시면, 저장된 정보가 모두 사라집니다. 그래도 탈퇴하시겠습니까?
              <v-text-field style="padding-top: 0" id="user-resign-input" dense
                            type="password" v-model="resign" />
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="green darken-1" text @click="userResign">탈퇴하기</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>>
      </div>

      <v-snackbar
        v-model="snackbar">
        {{ errorMessage[messageNum] }}
        <template v-slot:action="{ attrs }">
          <v-btn
            color="pink"
            text
            v-bind="attrs"
            @click="snackbar = false"
          >
            Close
          </v-btn>
        </template>
      </v-snackbar>
    </form>

  </v-container>
</template>

<script>
import axios from "axios"
import User from "@/api/users"
const SERVER_URL = process.env.VUE_APP_SERVER_URL


export default {
  name: "Settings",
  data() {
    return {
      user: new User(),
      genderGroup: ['', '남자', '여자'],
      name: this.$store.state.auth.user.id,
      userEmail: this.$store.state.auth.user,
      oldPassword: '',
      newPassword: '',
      newPasswordConfirm: '',
      messageNum:0,
      snackbar: false,
      resign: '',
      resignBtn: false,
      errorMessages: [
        value => !!value || 'Required.',
        value => (value === this.newPassword) || "비밀번호가 다릅니다."],
      submitMessage: ['비밀번호가 수정되었습니다.', '닉네임을 입력하셔야 합니다.', '닉네임이 수정되었습니다..'],
    }
  },
  methods: {
    informationChange() {
      if (this.user.nickname.length > 0) {
        axios({
          baseURL: SERVER_URL,
          url: `/users/${this.name}`,
          method: 'PUT',
          data: {
            'nickname': this.user.nickname,
            'profileDescription': this.user.profileDescription,
          }
        })
          .then(() => {
            this.messageNum = 4
            this.snackbar = !this.snackbar
            this.passwordEditChange()
          })
        .catch(e => {
          console.log(e.data)
          this.messageNum = 3
          this.snackbar = !this.snackbar
          this.passwordEditChange()
        })
      }
      this.passwordEditChange()
    },
    passwordEditChange() {
      if (this.oldPassword.length > 0) {
        this.$store.dispatch('auth/login', {'email': this.userEmail.email, 'password':this.oldPassword})
          .then(() => {
            if ((this.newPassword === this.newPasswordConfirm) && (this.newPassword.length > 0)) {
              axios({
                baseURL: SERVER_URL,
                url: `/users/${this.name}/detail`,
                method: 'PUT',
                data: {'newPassword': this.newPassword}
              })
                .then(() => this.messageNum = 2)
                .catch(e => console.log(e.data))
            }
            else {
              this.messageNum = 0
            }
          })
          .catch(e => {
            console.log(e.data)
            this.messageNum = 1
            this.snackbar = !this.snackbar
        })
      }
    },
    userProfile() {
      axios({
        baseURL: SERVER_URL,
        url:`/users/${this.$store.state.auth.user.id}/detail`,
        method: 'GET',
      })
        .then(res => {
          this.user = res.data
          console.log(this.user)
        })
        .catch(e => console.log(e.data))
    },
    userResign() {
      axios({
        baseURL: SERVER_URL,
        url:`/users/${this.$store.state.auth.user.id}`,
        method: 'DELETE'
      })
        .then(() => {
          alert('탈퇴되었습니다.')
          setTimeout(() => this.$router.push({name: "Home"}), 1000)
        })
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