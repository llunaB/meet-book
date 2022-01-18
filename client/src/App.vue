<template>
  <v-app>
    <Navbar />
    <v-main>
<<<<<<< HEAD
      <v-container>
=======
      <v-container fluid>
>>>>>>> cb53a0f (feat: add create conference)
        <router-view/>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Navbar from '@/components/TheNavbar'

export default {
  name: 'App',
  components: {
    Navbar,
  },
  data: () => ({
    //
  }),
};
</script>
