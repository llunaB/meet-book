<template>
  <v-app>
    <Navbar />

    <v-main style="flex: 3 1 auto; background-color:#fff;">
      <router-view />
      <!-- <v-container fluid style="background-color:blue;">
      </v-container> -->
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
  /* font-size: 100%; */
  /* background-color:#568D6C; */
  /* background-image: url("assets/books-436513_1920.jpg"); */
  background-repeat : no-repeat;
  background-size : cover;
}
.v-btn {
  padding: 20px !important;
}
.v-btn__content.v-btn__content {
  letter-spacing: 0 !important;
  padding: 5px !important;
}

</style>