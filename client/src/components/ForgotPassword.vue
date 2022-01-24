<template>
  <v-card v-if="dialog">
    <v-card-title class="text-h5 grey lighten-2">Forgot My Password</v-card-title>
    <form>
      <div class="form-group">
        <!-- Email Form -->
        <v-text-field
            type="email" label="Email" hide-details="auto"
            v-model="user.email" id="useremailInput" required/>
        <br>
        <!-- username Form -->
        <v-text-field
            type="text" label="Username" hide-details="auto"
            v-model="user.username" id="usernameInput" required/>
      </div>

      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" text @click="handleSubmit">
          Submit
        </v-btn>
      </v-card-actions>
    </form>
  </v-card>
</template>

<script>
import User from "@/api/users";
import axios from "axios";

export default {
  name: "ForgotPassword",
  data() {
    return {
      user: new User('', ''),
      dialog: true,
    }
  },
  methods: {
    handleSubmit() {
      this.dialog = !this.dialog
      axios.post('https://localhost:8080/forgotpassword', this.user)
      .then(res =>{
        console.log(res)
      })
      .catch(e => {
        console.log(e)
      })
    }
  }
}
</script>

<style scoped>

</style>