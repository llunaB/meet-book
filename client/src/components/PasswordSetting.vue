<template>
  <h3>Reset My Password</h3>
  <form>
    <div class="form-group">

      <v-text-field
          type="text" label="Password" hide-details="auto"
          v-model="user.password" id="passwordInput" required/>
      <br>

      <v-text-field
          type="password" label="PasswordConfirm" hide-details="auto"
          v-model="user.passwordConfirm" id="passwordConfirmInput" required/>
    </div>

    <v-divider></v-divider>
    <v-spacer></v-spacer>
    <v-btn color="primary" text @click="passwordChange">
      Submit
    </v-btn>
  </form>
</template>

<script>
import User from "@/api/users";
import axios from "axios";

export default {
  name: "ForgotPassword",
  data() {
    return {
      user: new User('', ''),
    }
  },
  methods: {
    passwordChange() {
      if (this.user.password === this.user.passwordConfirm) {
        axios.patch('https://localhost:8080/users/' + this.$store.state.auth.user.id, { 'password': '!!'})
        .then(res => console.log(res))
        .catch(e => console.log(e))
      }
    }
  }
}
</script>

<style scoped>

</style>