<template>
  <div class="card-container text-center loginClass">
      <v-container>
      <div class="signup-form-group row">
        <div class="col-lg-4 col-md-6 col-sm-8 mx-auto">
          <!-- 회원가입 전체 Form Start-->
          <div class="card flex-grid signup">
            <main><h2><strong>회원가입</strong></h2></main>

            <form class="form-group my-2" @submit.prevent="handleRegister">
              <!-- nickname 회원가입 Form -->
              <v-text-field class="row" type="text" label="* 별명" hide-details="auto" :rules="Message"
                            v-model="user.nickname" id="nickname-signup" required />
              <!-- Email 회원가입 Form && 인증번호 받기 버튼 -->
              <div class="row m-3" style="align-items: baseline">
                <v-text-field type="email" label="* Email" hide-details="auto" :rules="Message"
                              v-model="user.email" id="email-signup" required />

                <v-btn rounded elevation="3" class="row-2" color="#fffdad"
                       @click="emailDuplication">이메일 중복확인</v-btn>
              </div>
              <!--       인증번호 전송 후, 값 확인하는 Form       -->
              <div v-if="cert_key" class="row my-3" style="align-items: baseline">

                <v-text-field type="text" label="* 인증번호" hide-details="auto" :rules="Message"
                              v-model="confirm_key" id="cert-key" required />

                <v-btn rounded @click="confirmKey" elevation="5" color="#fffdad"
                       :disabled="validated_key">인증번호 확인</v-btn>
              </div>
              <div class="row">
                <!-- Password 회원가입 Form -->
                <v-text-field type="password" label="* 비밀번호" hide-details="auto" :rules="Message"
                              v-model="user.password" id="password-signup" required
                              oninput="this.value = this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' )" />
                <!-- PasswordConfirm 회원가입 Form -->
                <hr>
                <v-text-field type="password" label="* 비밀번호 확인" hide-details="auto"
                              :rules="errorMessages" v-model="user.passwordConfirm" id="passwordConfirm-signup" required
                              oninput="this.value = this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' )"/>
              </div>
              <!-- 회원가입 제출 버튼 -->
              <br>
              <h5><strong>* 표시는 필수 입력값입니다.</strong></h5>
              <div class="field text-center" id="submit-signup-form">
                <v-btn elevation="11" type="submit" color="#fffdad">가입하기</v-btn>
                <br>
                <a role="link" :href="'Login'">계정이 이미 있으신가요?</a>
              </div>
            </form>
            <!-- 스낵바 Form -->
            <v-snackbar color="pink" v-model="submitted">
              <template>{{ snackMessage }}</template>
            </v-snackbar>
          </div>
            <!-- 회원가입 전체 Form End -->
        </div>
      </div>
      </v-container>
  </div>
</template>

<script>
import axios from "axios"
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
 name: 'Signup',
 data() {
   return{
     user: {gender: 0},
     submitted: false,
     cert_key: false,
     confirm_key: "",
     validated_key: false,
     snackMessage: '',
     Message: [ value => !!value || '필수 입력사항입니다.' ],
     errorMessages: [
       value => !!value || '필수 입력사항입니다.',
       value => (value === this.user.password) || "비밀번호가 다릅니다."]
  }
 },
 computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn
    },
  },
  mounted() {
    if (this.loggedIn){
      this.$router.push({name: "Login"})
    }
  },
  methods: {
   // 가입하기 버튼 클릭
   handleRegister() {
     if (this.validated_key) {
       this.$store.dispatch("auth/register", this.user)
         .then(() => {
           alert(`${this.user.nickname}님 가입을 축하드립니다`)
           setTimeout(() => this.$router.push({name: "Login"}))
         })
         .catch(() => {
           this.submitted = true
           this.snackNum = '가입 정보를 한 번 확인해보시기 바랍니다.'
           setTimeout(() => this.submitted = false, 1000)
         })
     }
    },
    // 이메일 중복 확인 및 인증코드 확인
    emailDuplication() {
      this.confirm_key = ''
      this.validated_key = false
      if (this.user.email && this.user.email.indexOf('@') !== -1) {
        this.cert_key = true
        axios({
          baseURL: SERVER_URL,
          url: '/email/key',
          method: 'POST',
          data: {'email': this.user.email},
        })
          .then(() => {
            this.submitted = true
            this.snackMessage = "사용가능한 이메일입니다.\n인증 메일이 전송 되었습니다."
            setTimeout(() => this.submitted = false, 1000)
          })
          .catch(() => {
            this.submitted = true
            this.snackMessage = '다른 이메일을 사용해주세요.'
            setTimeout(() => this.submitted = false, 1000)
            this.cert_key = false
          })
      } else {
        this.submitted = true
        this.snackMessage = '이메일을 입력해주세요'
        setTimeout(() => this.submitted = false, 1000)
      }
    },
    // 이메일 인증코드 확인 함수
    confirmKey() {
     axios({
       baseURL: SERVER_URL,
       url: '/email/key-check',
       method: 'POST',
       data: {
         'email': this.user.email,
         'key': this.confirm_key },
     })
      .then(() => {
        this.submitted = true
        this.snackMessage = '인증되었습니다.'
        setTimeout(() => this.submitted = false, 1000)
        this.validated_key = true
      })
      .catch(() => {
        this.submitted = true
        this.snackMessage = '인증번호가 잘못되었습니다.'
        setTimeout(() => this.submitted = false, 1000)
        this.validated_key = false
      })
   }},
  }
</script>

<style>
p {
  line-height: 1rem;
}

.card {
  padding: 3rem;
  border-radius: 10%;
}

.form-group > input {
  margin-bottom: 20px;
  /* background: black; */
}

.field {
  margin-top: 2rem;
}

.field > button {
  margin-bottom: 1rem;
}

.field > button:hover {
  background: rgb(80, 87, 80);
  color: #000000;
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
}

.hr-sect {
	display: flex;
	flex-basis: 100%;
	align-items: center;
	color: rgba(0, 0, 0, 0.35);
	font-size: 12px;
	margin: 8px 0;
}
.hr-sect::before,
.hr-sect::after {
	content: "";
	flex-grow: 1;
	background: rgba(0, 0, 0, 0.35);
	height: 1px;
	font-size: 0;
	line-height: 0;
	margin: 0 16px;
}


</style>