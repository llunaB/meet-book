<template>
  <v-app >
    <Navbar />
    <div style="align-self: center; align-items: flex-end; padding-top: 150px; text-align: center;">
      <SearchBar />
      <v-btn text plain to="/conference" class="hidden-sm-and-down mt-5">
        <p class="mr-2"><strong>모임 참가하기</strong></p>
      </v-btn>
    </div>
    <v-main style="flex: 3 1 auto;">
      <v-container fluid>
        <router-view />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Navbar from '@/components/TheNavbar'
import SearchBar from '@/components/SearchBar'


export default {
  name: 'App',
  components: {
    Navbar,
    SearchBar,
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
  background-color:#568D6C;
  /* background-image: url("assets/books-436513_1920.jpg"); */
  background-repeat : no-repeat;
  background-size : cover;
}
</style>