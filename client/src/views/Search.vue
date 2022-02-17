<template>
  <div class="container">
    <v-row>
      <v-col class="col-12 col-md-6 offset-md-3">
        <SearchBar />
      </v-col>
    </v-row>

    <v-row class="searchtabs">
      <v-tabs
      color="#798F88">
        <v-tabs-slider color="#798F88"></v-tabs-slider>
        <v-tab
          @click="function() {search('conference', $route.query.keyword, parseInt($route.query.page))}"
          :to="{name: 'Search', params: {type: 'conference'}, query: {keyword: $route.query.keyword, page: parseInt($route.query.page)}}"><span>회의</span></v-tab>
        <v-tab
          @click="function() {search('book', $route.query.keyword, parseInt($route.query.page))}"
          :to="{name: 'Search', params: {type: 'book'}, query: {keyword: $route.query.keyword, page: parseInt($route.query.page)}}"><span>도서</span></v-tab>
        <v-tab
          @click="function() {search('users', $route.query.keyword, parseInt($route.query.page))}"
          :to="{name: 'Search', params: {type: 'users'}, query: {keyword: $route.query.keyword, page: parseInt($route.query.page)}}"><span>사용자</span></v-tab>
      </v-tabs>
    </v-row>
    <v-row>
      <v-col class="col-12">
        <div v-show="searchResult.content.length === 0">
          <p>검색 결과가 없습니다.</p>
        </div>
        <div v-show="searchResult.totalElements > 0">
          <p>총 {{ searchResult.totalElements }}건의 검색 결과가 있습니다.</p>
        </div>

        <div v-if="searchType === 'conference'">
          <p>"{{$route.query.keyword}}"의 회의 검색 결과입니다.</p>

          <div class="d-flex flex-wrap justify-space-around">
            <span v-for="(conference, idx) in searchResult.content" :key="idx" class="card-container">
              <ConferenceCard :conference="conference" class="conference-card" />
            </span>
          </div>

          <v-pagination v-model="page" :length="searchResult.totalPages"></v-pagination>

        </div>
        <div v-else-if="searchType === 'book'">
          <p>"{{$route.query.keyword}}"의 도서 검색 결과입니다.</p>
          <div class="d-flex flex-wrap justify-space-around">
            <span v-for="(book, idx) in searchResult.content" :key="idx" class="card-container">
              <TheBookcard :book="book" class="my-3 mx-auto" />
            </span>
          </div>
          <!-- <v-row>
            <template v-for="(book, idx) in searchResult.content">
              <v-col :key="idx" cols="12" sm="6" align-self="">
                <TheBookcard tile :book="book" class="my-3" />
              </v-col>
            </template>
          </v-row> -->
          <v-pagination v-model="page" :length="searchResult.totalPages"></v-pagination>
        </div>
        <div v-else>
          <p>"{{keyword}}"의 사용자 검색 결과입니다.</p>
          <div class="d-flex flex-wrap justify-space-around">
            <span v-for="(user, idx) in searchResult.content" :key="idx" class="user-container">
              <ProfileSmallcard  :person="user" class="my-3 mx-auto" />
            </span>
          </div>
          <v-pagination v-model="page" :length="searchResult.totalPages" @input="onPageChange" ></v-pagination>
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
import SearchBar from '@/components/SearchBar'
import ConferenceCard from '@/components/home/ConferenceCard'
import TheBookcard from '@/components/TheBookcard'
import ProfileSmallcard from '@/components/ProfileSmallcard'
export default {
  name: 'Search',
  components: {
    SearchBar, ConferenceCard, TheBookcard, ProfileSmallcard
  },
  data: function () {
    return {
      // searchType: this.$route.params.type,
      // keyword: this.$route.query.keyword,
      page: parseInt(this.$route.query.page),
      searchResult: {
        content: [],
        totalElements: 0
      },
    }
  },
  methods: {
    search: function (type, keyword, page) {
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
        this.searchResult = response.data
      })
      .catch(() => {})
    },

    onPageChange(page) {
      this.search(this.searchType, this.keyword, parseInt(page))
    },
  },

  mounted: function () {
    
    this.search(this.searchType, this.keyword, parseInt(this.page))
  },

  watch: {
    page: function (newPage) {
      this.onPageChange(parseInt(newPage))
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
<style scoped>
  .v-input__prepend-inner {
    margin-right: 5px !important;
  }

  .card-container {
    margin: 1rem;
  }

  .user-container {
    margin: 1rem;
    min-width: 320px;
    max-width: 320px;
  }

  .conference-card {
    min-width: 220px;
    max-width: 220px;
  }
</style>