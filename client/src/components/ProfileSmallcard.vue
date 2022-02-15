<template>
  <v-card
  max-width="344"
  elevation="0"
  :to="{
    name: 'Profile',
    params: { userId: person.id }
  }">
  <v-card-title>
    <v-avatar v-if="person.profileImage" size="48">
      <v-img :src="person.profileImage" contain />
    </v-avatar>
    <v-avatar v-else size="48">
      <v-img src="@/assets/host_img/HostImg.png" />
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