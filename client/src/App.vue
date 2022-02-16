<template>
  <v-app>
    <Navbar />

    <v-main style="flex: 3 1 auto;">
      <router-view />
    </v-main>

    <Footer />
  </v-app>
</template>

<script>
import Navbar from '@/components/TheNavbar'
import Footer from '@/components/TheFooter'


export default {
  name: 'App',
  components: {
    Navbar,
    Footer,
  },
  data: () => ({
    //
  }),
};
</script>

<style>
#app {
  font-family: 'Noto Sans KR', sans-serif;
  font-size: 90%;
  background-repeat : no-repeat;
  background-size : cover;
}

</style>