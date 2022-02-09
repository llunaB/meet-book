<template>
  <div class="card-container">
      <div class="signup-form-group row">
        <div class="col-lg-4 col-md-6 col-sm-8 mx-auto">
          <!-- 회원가입 전체 Form Start-->
          <div class="card flex-grid signup">
            <main><h2> <strong>회원가입</strong></h2></main>
            <form class="form-group my-2" @submit.prevent="handleRegister">
              <!-- nickname 회원가입 Form -->
              <v-text-field
                type="text" label="별명" hide-details="auto"
                v-model="user.nickname" id="nickname-signup" required />
              <!-- Email 회원가입 Form && 인증번호 받기 버튼 -->
              <div class="row my-3" style="align-items: baseline">
                <v-text-field
                  type="email" label="Email" hide-details="auto"
                  v-model="user.email" id="email-signup" required />
                <v-btn rounded @click="sendCheckKey" class="row-2">인증번호 받기</v-btn>
              </div>
              <!--       인증번호 전송 후, 값 확인하는 Form       -->
              <div v-if="cert_key.length > 0" class="row my-3" style="align-items: baseline">
                <v-text-field
                  type="text" label="인증번호" hide-details="auto"
                  v-model="confirm_key" id="cert-key" required />
                <v-icon v-if="(cert_key === confirm_key) && cert_key.length > 0"> mdi-check-circle </v-icon>
                <v-icon v-else> mdi-check-circle-outline </v-icon>
              </div>
              <!-- Password 회원가입 Form -->
              <v-text-field
                type="password" label="비밀번호" hide-details="auto"
                v-model="user.password" id="password-signup" required
                oninput="this.value = this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' )" />
              <!-- PasswordConfirm 회원가입 Form -->
              <v-text-field
                type="password" label="비밀번호 확인" hide-details="auto" :error-messages=errorMessages
                v-model="user.passwordConfirm" id="passwordConfirm-signup" required
                oninput="this.value = this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' )"/>
              <!-- 회원가입 제출 버튼 -->
              <div class="field" id="submit-signup-form">
                <v-btn type="submit" class="primary">가입하기</v-btn>
                <br>
                <a role="link" :href="'Login'">계정이 이미 있으신가요?</a>
              </div>
            </form>
            <!-- 소셜 회원가입 전체 Form Start-->
            <form class="social-form-group">
              <div class="hr-sect">SNS로 로그인 하기</div>
              <v-icon> mdi-facebook </v-icon>
              <v-icon> mdi-google </v-icon>

            </form>
            <!-- 소셜 회원가입 전체 Form End -->
          </div>
            <!-- 회원가입 전체 Form End -->
        </div>
      </div>
  </div>
</template>

<script>
import axios from "axios"
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
 name: 'Signup',
 data() {
   return{
     user: {},
     submitted: false,
     successful: false,
     message: "",
     cert_key: "",
     confirm_key: ""
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
  methods: {handleRegister() {
      this.message = ""
      this.submitted = true
      if (this.cert_key === this.confirm_key) {
        this.$store.dispatch("auth/register", this.user).then(
          data => {
            this.message = `${data.nickname}님 가입을 축하드립니다`
            this.successful = true
            alert(this.message)
            setTimeout(() => {
              this.$router.push({name: "Login"})
            }, 3000)
          }).catch(e => {
          this.message = (e.response && e.response.data) || e.message || e.toString()
          this.successful = false
          alert(this.message)
        })
      }
    }, sendCheckKey: function () {
      axios({
        baseURL: SERVER_URL,
        url: '/email/emailcheck',
        method: 'GET',
        params: {'mail': this.user.email}
      })
        .then(res => {
          alert(`해당 메일로 인증번호를 전송했습니다.`)
          this.cert_key = res.data.key
        })
        .catch(()=>alert(`이메일을 확인해주세요`))
    }, errorMessages() {
      if ((this.user.password !== this.user.passwordConfirm) && (this.user.passwordConfirm.length > 0))
        return '비밀번호가 다릅니다.'
    }}
  }
</script>

<style>
p {
  line-height: 1rem;
}

.card {
  padding: 3rem;
  border-radius: 10%;
  /* glass effect */
  background-color: #ffffff10;
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
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
  color: white;
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
}

/* .signup-page {
  align-items: center;
} */

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