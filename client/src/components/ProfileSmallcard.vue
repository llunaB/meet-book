<template>
  <v-card
  max-width="344"
  elevation="0"
  :to="{
    name: 'Profile',
    query: {data: JSON.stringify( {userId: person.id} ) },
  }">
  <v-card-title>
    <v-avatar
    color="#F7DBCE"
    size="48">
      <img v-show="!!person.profile_image" :src="person.profile_image">
      <span v-show="!person.profile_image">
        <v-icon>
          mdi-account-circle
        </v-icon>
      </span>
    </v-avatar>
    <p class="ml-3">
      {{ person.nickname }}
    </p>
  </v-card-title>
  <v-card-subtitle>
    <p class="caption text-right">
      {{ person.profileDescription }}
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