<template>
  <div class="card-container">
      <div class="signup-form-group row">
        <div class="col-lg-4 col-md-6 col-sm-8 mx-auto">
          <!-- 회원가입 전체 Form Start-->
          <div class="card flex-grid signup">
            <main>
              <h2>Sign Up</h2>
            </main>
            <form class="form-group">
              <!-- username 회원가입 Form -->
              <div class="field text-start" id="username-signup-form">
                <label for="username-signup">Username</label>
                <input type="text" v-model="state.username" class="form-control" placeholder="Username" id="username-signup" required>
              </div>
              <!-- Email 회원가입 Form -->
              <div class="field text-start" id="email-signup-form">
                <label for="email-signup">Email</label>
                <input type="email" v-model="state.email" class="form-control" placeholder="Email" id="email-signup" required>
              </div>
              <!-- Password 회원가입 Form -->
              <div class="field text-start" id="password-signup-form">
                <label for="password-signup">Password</label>
                <input type="password" v-model="state.password" class="form-control" placeholder="Password" id="password-signup" required>
              </div>
              <!-- PasswordConfirm 회원가입 Form -->
              <div class="field text-start" id="password-signup-confirm-form">
                <label for="password-signup-confirm">Password Confirm</label>
                <input type="password" v-model="state.passwordConfirm" class="form-control" placeholder="Password Confirm" id="password-signupconfirm-" required>
              </div>
              <p>{{ state }}</p>
              <!-- 회원가입 제출 버튼 -->
              <div class="field" id="submit-signup-form">
                <button type="submit" class="btn btn-dark action-button expand-right">Submit</button><br>
                <a role="link" :href="'Login'">Already have an account?</a>
              </div>
            </form>
            <!-- 소셜 회원가입 전체 Form Start-->
            <form class="social-form-group">
              <div class="hr-sect">Sign Up With</div>
              <vue-feather type="chrome" class="m-3"></vue-feather>
              <vue-feather type="github" class="m-3"></vue-feather>
              <vue-feather type="facebook" class="m-3"></vue-feather>
            </form>
            <!-- 소셜 회원가입 전체 Form End -->
          </div>
            <!-- 회원가입 전체 Form End -->
        </div>
      </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
export default {
 name: 'Signup',
 setup() {
     const state = reactive({
         username: '',
         email: '',
         password: '',
         passwordConfirm: '',
     })

     return { state }
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

.signup-page {
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