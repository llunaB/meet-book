<template>
  <div class="card-container">
      <div class="login-form-group row">
        <div class="col-lg-4 col-md-6 col-sm-8 mx-auto">
          <!-- 로그인 전체 Form Start-->
          <div class="card flex-grid login">
            <main>
              <h2>Sign In</h2>
            </main>
            <form class="form-group my-3">
              <!-- Email 로그인 Form -->
              <v-text-field
                type="email" label="Email" hide-details="auto"
                v-model="useremail" id="useremail-login" required/>
              <br>
              <!-- Password 로그인 Form -->
              <v-text-field
                type="password" label="Password" hide-details="auto"
                v-model="password" id="passwordLogin" required/>
              <!-- 로그인 제출 버튼 -->
              <div class="field" id="submit-login-form">
                <v-btn type="submit" class="primary" @click="loginSubmit">Submit</v-btn>
                <br>
                <p>Don't have an accounts? <a role="link" :href="'signup'">Sign Up</a></p>
                <p><a href="#">Forgot your Password?</a></p>
              </div>
            </form>
            <!-- 소셜 로그인 전체 Form Start-->
            <!-- <form class="social-form-group">
              <div class="hr-sect">Log In With</div>
              <i icon="brands facebook" />
              <font-awesome-icon icon="fa-brands fa-google" />
              <font-awesome-icon icon="fa-brands fa-github" />
            </form> -->
            <!-- 소셜 로그인 전체 Form End -->
          </div>
            <!-- 로그인 전체 Form End -->
        </div>
      </div>
  </div>
</template>

<script>

// const storage = window.sessionStorage


export default {
    name: 'Login',
    data() {
      return {
          useremail: null,
          password: null,
      }
    },
    methods: {
      loginSubmit() {
        this.$store.dispatch(
          'login', {
            useremail: this.useremail,
            password: this.password
        }).then(() => {this.$router.push({name: 'Home'})})
      },
      // 
        // login() {
        //     storage.setItem('jwt-auth-token', '')
        //     storage.setItem('login-user', '')
        //     ai.post('user/login', {
        //         email: this.email,
        //         password: this.password,
        //     }).then(res => {
        //         if (res.data.status) {
        //             this.message = res.data.data.email + '로그인'
        //             console.dir(res.headers['jwt-auth-token'])
        //             storage.setItem('jwt-auth-token', res.headers['jwt-auth-token'])
        //             storage.setItem('login-user', res.data.data.email)
        //         } else {
        //             alert('가입 정보 확인 요망')
        //         }
        //     }).catch(e => {
        //         console.log('fail' + e.message)
        //     })
        // }
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