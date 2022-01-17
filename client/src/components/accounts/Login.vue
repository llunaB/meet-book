<template>
  <div class="card-container">
      <div class="login-form-group row">
        <div class="col-lg-4 col-md-6 col-sm-8 mx-auto">
          <!-- 로그인 전체 Form Start-->
          <div class="card flex-grid login">
            <main>
              <h2>Sign In</h2>
            </main>
            <form class="form-group">
              <!-- Email 로그인 Form -->
              <div class="field text-start" id="email-login-form">
                <label for="email-login">Email</label>
                <input type="email" v-model="email" class="form-control" placeholder="Email" id="email-login" required>
              </div>
              <!-- Password 로그인 Form -->
              <div class="field text-start" id="password-login-form">
                <label for="password-login">Password</label>
                <input type="password" v-model="password" class="form-control" placeholder="Password" id="password-login" required>
              </div>
              <p>{{ email }} {{ password }}</p>
              <!-- 로그인 제출 버튼 -->
              <div class="field" id="submit-login-form">
                <button type="submit" @click="login" class="btn btn-dark action-button expand-right">Submit</button>
                <p>Don't have an accounts? <a :href="'Signup'">Sign Up</a></p>
                <p><a href="#">Forgot your Password?</a></p>
              </div>
            </form>
            <!-- 소셜 로그인 전체 Form Start-->
            <form class="social-form-group">
              <div class="hr-sect">Log In With</div>
              <a><vue-feather type="facebook" class="m-3"></vue-feather></a>
              <vue-feather type="chrome" class="m-3"></vue-feather>
              <vue-feather type="github" class="m-3"></vue-feather>
            </form>
            <!-- 소셜 로그인 전체 Form End -->
          </div>
            <!-- 로그인 전체 Form End -->
        </div>
      </div>
  </div>
</template>

<script>
import axios from 'axios'

const storage = window.sessionStorage

const ai = axios.create({
    baseURL: 'http://localhost:8080/api/'
})

export default {
    name: 'Login',
    data() {
        return {
            email: '',
            password: '',
        }
    },
    methods: {
        login() {
            storage.setItem('jwt-auth-token', '')
            storage.setItem('login-user', '')
            ai.post('user/login', {
                email: this.email,
                password: this.password,
            }).then(res => {
                if (res.data.status) {
                    this.message = res.data.data.email + '로그인'
                    console.dir(res.headers['jwt-auth-token'])
                    storage.setItem('jwt-auth-token', res.headers['jwt-auth-token'])
                    storage.setItem('login-user', res.data.data.email)
                } else {
                    alert('가입 정보 확인 요망')
                }
            }).catch(e => {
                console.log('fail' + e.message)
            })
        }
    }
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

.login-page {
  align-items: center;
  /* background-color: black; */
}

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