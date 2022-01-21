<template>
  <v-card
  max-width="344"
  elevation="0"
  :to="{name: 'Profile', params: person.userId}">

  <v-card-title>
    <v-avatar
    color="primary"
    size="48">
      <img v-if="person.profile_image != ''" :src="person.profile_image" :alt="person.nickname">
      <div v-else>
        <v-icon>
          mdi-account-circle
        </v-icon>
      </div>
    </v-avatar>
    <p class="ml-3">
      {{ person.nickname }}
    </p>
  </v-card-title>
  <v-card-subtitle>
    <p class="caption text-right">
      {{ person.email }}
    </p>
  </v-card-subtitle>

  </v-card>
</template>

<script>
export default {
  name: 'ProfileSmallcard',
  props: {
    person: Object,
  },

  data: function () {
    return {
      // person: {
      //   nickname: '닉네임',
      //   email: 'email@mail.com',
      //   profile_image: ''
      // },
    }
  },

  methods: {

  },
}
</script>

<style>

</style>