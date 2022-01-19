<template>
  <div class="card-container">
      <div class="signup-form-group row">
        <div class="col-lg-4 col-md-6 col-sm-8 mx-auto">
          <!-- 회원가입 전체 Form Start-->
          <div class="card flex-grid signup">
            <main><h2>Sign Up</h2></main>
            <form class="form-group my-2">
              <!-- username 회원가입 Form -->
              <v-text-field
                type="text" label="Username" hide-details="auto"
                v-model="username" id="username-login" required/>
              <!-- Email 회원가입 Form -->
              <v-text-field
                type="email" label="Email" hide-details="auto"
                v-model="useremail" id="useremail-login" required/>
              <!-- Password 회원가입 Form -->
              <v-text-field
                type="password" label="Password" hide-details="auto"
                v-model="password" id="password-login" required/>
              <!-- PasswordConfirm 회원가입 Form -->
              <v-text-field
                type="password" label="passwordConfirm" hide-details="auto"
                v-model="passwordConfirm" id="passwordConfirm-login" required/>
              <!-- 회원가입 제출 버튼 -->
              <div class="field" id="submit-signup-form">
                <v-btn type="submit" class="primary" @click="login">Submit</v-btn>
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
export default {
 name: 'Signup',
 data() {
   return{
    username: null,
    useremail: null,
    password: null,
    passwordConfirm: null,
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