<template>
  <div>
    <v-row class="searchtabs">
      <v-tabs
      color="#798F88">
        <v-tabs-slider color="#798F88"></v-tabs-slider>
        <v-tab
          @click="function () {searchType = 'conf'}"
          :to="`conf?keyword=${keyword}`">회의</v-tab>
        <v-tab
          @click="function () {searchType = 'book'}"
          :to="`book?keyword=${keyword}`">도서</v-tab>
        <v-tab
          @click="function() {searchType = 'users'}"
          :to="`users?keyword=${keyword}`">사용자</v-tab>
      </v-tabs>
    </v-row>
    <v-row>
      <v-col class="col-9">
        <div v-if="searchType == 'conf'">
          <p>회의 검색 결과입니다.</p>
          
        </div>
        <div v-else-if="searchType == 'book'">
          <p>도서 검색 결과입니다.</p>
        </div>
        <div v-else>
          <p>사용자 검색 결과입니다.</p>
        </div>

      </v-col>
      <v-col class="col-3">
        <p>"{{ $route.query.keyword }}"에 대해 {{$route.params.type}}로 검색한 결과입니다.</p>
        <p>{{ $route.query.title }}</p>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Search',
  data: function () {
    return {
      searchType: this.$route.params.type,
      keyword: this.$route.query.keyword,
      searchResult: null,
    }
  },
  methods: {
    search: function (type, keyword) {
      console.log(type, keyword)
      axios({
        url: '',
        method: 'get',
        params: '',
      })
      .then(response => {
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
    },


  },

}
</script>

<style>

</style>