<template>
  <v-card v-if="dialog">
    <v-card-title class="text-h5 grey lighten-2">Forgot My Password</v-card-title>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <!-- Email Form -->
        <v-text-field
            type="email" label="Email" hide-details="auto"
            v-model="email" id="useremailInput" required/>
        <br>
      </div>

      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn type="submit" color="primary" text>
          Submit
        </v-btn>
      </v-card-actions>
    </form>
  </v-card>
</template>

<script>
import axios from "axios";

export default {
  name: "ForgotPassword",
  data() {
    return {
      email: "",
      dialog: true,
    }
  },
  methods: {
    handleSubmit() {
      axios.post('https://localhost:8080/users/findpwd', { "email": this.email })
      .then(res =>{
        console.log(res)
      })
      .catch(e => {
        console.log(e)
      })
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>