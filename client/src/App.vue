<template>
  <v-app >
    <Navbar />
    <v-main>
      <v-container fluid >
        <router-view />
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

<style>
#app {
  font-family: 'Noto Sans KR', sans-serif;
  font-size: 90%;
  background-image: url("assets/books-436513_1920.jpg");
  background-repeat : no-repeat;
  background-size : cover;
}
.v-main__wrap {
  align-self: center;
}
</style>