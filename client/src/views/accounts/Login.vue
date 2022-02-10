<template>
  <div class="card-container">
      <div class="login-form-group row">
        <div class="col-lg-4 col-md-6 col-sm-8 mx-auto">
          <!-- 로그인 전체 Form Start-->
          <div class="card flex-grid login">
            <main>
              <h2>로 그 인</h2>
            </main>
            <form class="form-group my-3" @submit.prevent="handleLogin">
              <!-- Email 로그인 Form -->
              <v-text-field
                type="email" label="이메일" hide-details="auto"
                v-model="user.email" id="useremail-login" required/>
              <br>
              <!-- Password 로그인 Form -->
              <v-text-field
                type="password" label="비밀번호" hide-details="auto"
                v-model="user.password" id="passwordLogin" required
                oninput="this.value = this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' )"/>
              <!-- 로그인 제출 버튼 -->
              <div class="field" id="submit-login-form">
                <v-btn type="submit" class="primary">로그인하기</v-btn>
                <br>
                <p>계정이 없으신가요? <a role="link" :href="'signup'">회원가입 히기</a></p>
                <v-dialog v-model="dialog" width="500">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn color="red lighten-2" dark
                    v-bind="attrs" v-on="on">
                      비밀번호를 잊어버리셨다면..
                    </v-btn>
                  </template>
                  <ForgotPassword v-if="dialog" @close="dialog = false"/>
                </v-dialog>


              </div>
            </form>
            <!-- 소셜 로그인 전체 Form Start-->
            <form class="social-form-group">
              <div class="hr-sect">SNS 로그인 하기</div>
              <v-icon>mdi-facebook</v-icon>
              <v-icon>mdi-google</v-icon>
            </form> -->
            <!-- 소셜 로그인 전체 Form End -->
          </div>
            <!-- 로그인 전체 Form End -->
        </div>
      </div>
  </div>
</template>

<script>
import ForgotPassword from "@/components/ForgotPassword";

export default {
    "name": 'Login',
  "components": {ForgotPassword},
  "data"() {
      return {
        "user": {},
        "loading": false,
        "dialog": false,
      }
    },
    "computed": {
      "loggedIn"() {
        return this.$store.state.auth.status.loggedIn
      }
    },
    "mounted"() {
      if (this.loggedIn) this.$router.push({"name": 'Home'})
    },
    "methods": {
      "handleLogin"() {
        this.loading = true
        this.$store.dispatch('auth/login', this.user)
          .then(() => this.$router.push({"name": "Home"}))
          .catch(() => {
            this.loading = false;
            alert('Email 혹은 Password 잘못입력하셨습니다.')
          })
      }
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
	margin: 16px;
}
</style>