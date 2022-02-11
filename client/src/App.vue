<template>
  <v-app>
    <Navbar />
    <v-main>
      <v-container fluid>
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