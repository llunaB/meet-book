<template>
  <div class="card-container">
      <div class="signup-form-group row">
        <div class="col-lg-4 col-md-6 col-sm-8 mx-auto">
          <!-- 회원가입 전체 Form Start-->
          <div class="card flex-grid signup">
            <main><h2>Sign Up</h2></main>
            <form class="form-group my-2" @submit.prevent="handleRegister">
              <!-- username 회원가입 Form -->
              <v-text-field
                type="text" label="Username" hide-details="auto"
                v-model="user.username" id="username-signup" required/>
              <!-- nickname 회원가입 Form -->
              <v-text-field
                type="text" label="Nickname" hide-details="auto"
                v-model="user.nickname" id="nickname-signup" required/>
              <!-- Email 회원가입 Form -->
              <v-text-field
                type="email" label="Email" hide-details="auto"
                v-model="user.useremail" id="useremail-signup" required/>
              <!-- Password 회원가입 Form -->
              <v-text-field
                type="password" label="Password" hide-details="auto"
                v-model="user.password" id="password-signup" required/>
              <!-- PasswordConfirm 회원가입 Form -->
              <v-text-field
                type="password" label="passwordConfirm" hide-details="auto"
                v-model="user.passwordConfirm" id="passwordConfirm-signup" required/>
              <!-- 회원가입 제출 버튼 -->
              <div class="field" id="submit-signup-form">
                <v-btn type="submit" class="primary">Submit</v-btn>
                <br>
                <a role="link" :href="'Login'">Already have an account?</a>
              </div>
            </form>
            <!-- 소셜 회원가입 전체 Form Start-->
            <form class="social-form-group">
              <div class="hr-sect">Sign Up With</div>
            </form>
            <!-- 소셜 회원가입 전체 Form End -->
          </div>
            <!-- 회원가입 전체 Form End -->
        </div>
      </div>
  </div>
</template>

<script>
import User from '@/api/users'
// import axios from "axios"

export default {
 name: 'Signup',
 data() {
   return{
     user: new User("", "", "", "", "", ""),
     submitted: false,
     successful: false,
     message: "",
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
    handleRegister() {
      this.message = ""
      this.submitted = true
      console.log(this.user)
      // axios.post('http://localhost:8080/register', this.user)
      //   .then(res => {
      //     console.log(res)
      //   })
      // .catch(e => {console.log(e)})
      this.$store.dispatch("auth/register", this.user).then(
        data => {
          this.message = `${data.nickname}님 가입을 축하드립니다`
          this.successful = true
          setTimeout(()=>{this.$router.push({name: "Login"})}, 3000)
        }).catch(e => {
          this.message = (e.response && e.response.data) || e.message || e.toString()
          this.successful = false
          })
    },
  },

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
	margin: 8px 0px;
}
.hr-sect::before,
.hr-sect::after {
	content: "";
	flex-grow: 1;
	background: rgba(0, 0, 0, 0.35);
	height: 1px;
	font-size: 0px;
	line-height: 0px;
	margin: 0px 16px;
}
</style>