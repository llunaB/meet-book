<template>
  <v-container class="settingForm" style="padding-inline: 10%;">
    <form>
      <div class="form-group">
        <div class="img-upload">
          <v-avatar v-if="user.profileImage" size="150">
            <v-img :src="user.profileImage" contain />
          </v-avatar>
          <v-avatar v-else size="150">
            <v-img src="@/assets/host_img/HostImg.png" />
          </v-avatar>
          <input id="file-input" type="file" />
        </div>

        <v-text-field
          type="text" label="닉네임" hide-details="auto"
          v-model="user.nickname" id="nicknameInput" />
        <br>
        <div v-if="!snsLogin">
          <v-text-field
            type="password" label="비밀번호 수정" hide-details="auto"
            v-model="newPassword" id="passwordEditInput"
            oninput="this.value = this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' )" />

          <v-text-field
            type="password" label="비밀번호 수정 확인" hide-details="auto"
            v-model="newPasswordConfirm" id="passwordConfirmEditInput" :rules="errorMessages"
            oninput="this.value = this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' )" />
          <br>
          <div style="text-align: end;">
            <v-btn color="primary" text @click="passwordEditChange">비밀번호 수정</v-btn>
          </div>
        </div>
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
          v-model="resignBtn" max-width="290"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="secondary" text dark v-bind="attrs" v-on="on"
            >탈퇴하기</v-btn>
          </template>
          <v-card>
            <v-card-title class="text-h5">
              정말 탈퇴하시겠습니까??
            </v-card-title>
            <v-card-text>탈퇴를 하시면, 저장된 정보가 모두 사라집니다. 그래도 탈퇴하시겠습니까?</v-card-text>
            <v-card-text>
              비밀번호를 한번 더 입력해주세요.
              <v-text-field style="padding-top: 0" id="user-resign-input" dense
                          type="password" v-model="resign" />
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="secondary" text @click="userResign">탈퇴하기</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </div>

      <v-snackbar
        v-model="snackbar">
        {{ submitMessage }}        
      </v-snackbar>
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
      genderGroup: ['', '남자', '여자'],
      name: this.$store.state.auth.user.id,
      userEmail: this.$store.state.auth.user,
      newPassword: '',
      newPasswordConfirm: '',
      snackbar: false,
      resign: '',
      snsLogin: this.$store.state.auth.user.SNS,
      resignBtn: false,
      errorMessages: [
        value => !!value || 'Required.',
        value => (value === this.newPassword) || "비밀번호가 다릅니다."],
      submitMessage: '',
    }
  },
  methods: {
    informationChange() {
      if (this.user.nickname.length) {
        axios({
          baseURL: SERVER_URL,
          url: `/users/${this.user.id}`,
          method: 'PUT',
          data: {
            'nickname': this.user.nickname,
            'profileDescription': this.user.profileDescription ? this.user.profileDescription : '',
            'profileImage': '',
          },
          headers: {
            'X-AUTH-TOKEN': this.$store.state.auth.user.token
          }
        })
        .then(() => setTimeout(() => this.$router.push({name:'Profile'}), 1000))
        .catch(e => {
          console.log(e)
          this.submitMessage = '값을 입력해주세요'
          setTimeout(() => this.snackbar = !this.snackbar, 2000);
        })
      }
      else {
        this.submitMessage = '닉네임을 입력해주세요'
        setTimeout(() => this.snackbar = !this.snackbar, 2000);
      }
    },
    passwordEditChange() {
      if ((this.newPassword === this.newPasswordConfirm) && (this.newPassword.length > 0)) {        
        axios({
          baseURL: SERVER_URL,
          url: `/users/${this.user.id}/Password`,
          method: 'PUT',
          data: {'newPassword': this.newPassword},
          headers: {
            'X-AUTH-TOKEN': this.$store.state.auth.user.token
          }
        })
        .then(() => {
          this.submitMessage = '비밀번호가 변경되었습니다.'
          setTimeout(() => this.snackbar = !this.snackbar, 2000)
          })
      }
      else {
        this.submitMessage = '비밀번호를 입력해주세요'
        setTimeout(() => this.snackbar = !this.snackbar, 2000)
      }
    },
    userProfile() {
      axios({
        baseURL: SERVER_URL,
        url: '/users/' + this.$store.state.auth.user.id,
        method: 'GET',
        headers: {
            'X-AUTH-TOKEN': this.$store.state.auth.user.token
          },
      })
      .then(res => {
        res.data['token'] = this.$store.state.auth.user.token
        this.user = res.data
      })
      .catch(() => {})
    },
    userResign() {
      axios({
        baseURL: SERVER_URL,
        url:`/users/${this.user.id}`,
        method: 'DELETE',
        headers: {
          'X-AUTH-TOKEN': this.$store.state.auth.user.token
        },
        data: {
          "password": this.resign,
        },
        
      })
      .then(() => {
        alert('탈퇴되었습니다.')
        setTimeout(() => {
          localStorage.removeItem('vuex')
          this.$router.go(0)
          this.$router.push({name: "Home"})}, 100)
      })
      .catch(() => {
        this.submitMessage = '비밀번호를 확인해주세요'
        setTimeout(() => this.snackbar = !this.snackbar, 2000)
      })
    },
  },
  beforeMount() {
    this.userProfile()
  },
}
</script>

<style>
.settingForm {
  padding: 3rem;
  border-radius: 10%;
  /* glass effect */
  background-color: #ffffff30;
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
}

</style>
