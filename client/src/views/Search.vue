<template>
  <div>
    <v-row class="searchtabs">
      <v-tabs
      color="#798F88">
        <v-tabs-slider color="#798F88"></v-tabs-slider>
        <v-tab
          @click="function() {search('conference', $route.query.keyword, parseInt($route.query.page))}"
          :to="{name: 'Search', params: {type: 'conference'}, query: {keyword: $route.query.keyword, page: parseInt($route.query.page)}}">회의</v-tab>
        <v-tab
          @click="function() {search('book', $route.query.keyword, parseInt($route.query.page))}"
          :to="{name: 'Search', params: {type: 'book'}, query: {keyword: $route.query.keyword, page: parseInt($route.query.page)}}">도서</v-tab>
        <v-tab
          @click="function() {search('users', $route.query.keyword, parseInt($route.query.page))}"
          :to="{name: 'Search', params: {type: 'users'}, query: {keyword: $route.query.keyword, page: parseInt($route.query.page)}}">사용자</v-tab>
      </v-tabs>
    </v-row>
    <v-row>
      <v-col class="col-12">
        <div v-if="searchType === 'conference'">
          <p>"{{$route.query.keyword}}"의 회의 검색 결과입니다.</p>
          <ConferenceCard v-for="(conference, idx) in searchResult.content" :key="idx" :conference="conference" />
          <v-pagination v-model="page" :length="searchResult.totalPages"></v-pagination>

        </div>
        <div v-else-if="searchType === 'book'">
          <p>"{{$route.query.keyword}}"의 도서 검색 결과입니다.</p>
          <TheBookcard v-for="(book, idx) in searchResult.content" :key="idx" :book="book" class="my-3 mx-auto" />
          <v-pagination v-model="page" :length="searchResult.totalPages"></v-pagination>
        </div>
        <div v-else>
          <p>"{{keyword}}"의 사용자 검색 결과입니다.</p>
          <v-row>
            <ProfileSmallcard v-for="(user, idx) in searchResult.content" :key="idx" :person="user" class="my-3 mx-auto" />
          </v-row>

          <v-pagination v-model="page" :length="searchResult.totalPages" @input="onPageChange" ></v-pagination>
        </div>

        <div v-show="searchResult.content.length === 0">
          <p>검색 결과가 없습니다.</p>
        </div>
        
      </v-col>
      <!-- <v-col class="col-3">
        <p>"{{ $route.query.keyword }}"에 대해 {{$route.params.type}}로 검색한 결과입니다.</p>
        <p>{{ $route.query.title }}</p>
      </v-col> -->
    </v-row>
  </div>
</template>

<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL

import axios from 'axios'
import ConferenceCard from '@/components/home/ConferenceCard'
import TheBookcard from '@/components/TheBookcard'
import ProfileSmallcard from '@/components/ProfileSmallcard'
export default {
  name: 'Search',
  components: {
    ConferenceCard, TheBookcard, ProfileSmallcard
  },
  data: function () {
    return {
      // searchType: this.$route.params.type,
      // keyword: this.$route.query.keyword,
      page: this.$route.query.page,
      searchResult: {
        content: [],
      },
    }
  },
  methods: {
    search: function (type, keyword, page) {
      console.log(type, keyword, page)
      let typeString = ''
      let goParams = {}
      switch (type) {
        case 'book':
          typeString = 'book'
          goParams = {book_name: keyword, size: 10}
          break
        case 'users':
          typeString = 'users'
          goParams = {nickname: keyword, size: 20}
          break
        default:
          typeString = 'conference'
          goParams = {title: keyword, size: 12}
          break
      }

      axios({
        baseURL: SERVER_URL,
        url: `/search/${typeString}`,
        method: 'GET',
        params: Object.assign(goParams, {page: parseInt(page)-1}), 
      })
      .then(response => {
        console.log(response)
        this.searchResult = response.data
      })
      .catch(error => {
        console.log(error)
      })
    },

    onPageChange(page) {
      this.search(this.searchType, this.keyword, page)
    },
  },

  mounted: function () {
    
    this.search(this.searchType, this.keyword, this.page)
  },

  watch: {
    page: function (newPage) {
      this.onPageChange(newPage)
    },
    keyword: function () {
      this.search('conference', this.keyword, 1)
    }
    
  },
  computed: {
    keyword: function () {return this.$route.query.keyword},
    searchType: function () {return this.$route.params.type},
  }

}
</script>

<style>

</style>