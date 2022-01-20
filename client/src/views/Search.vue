<template>
  <div>
    <v-row class="searchtabs">
      <v-tabs>
        <v-tab
          :to="`conf?keyword=${keyword}`">회의</v-tab>
        <v-tab
          :to="`book?keyword=${keyword}`">도서</v-tab>
        <v-tab
          :to="`users?keyword=${keyword}`">사용자</v-tab>
      </v-tabs>
    </v-row>
    <v-row>
      <v-col class="col-9">
        <v-tabs-items>
          <v-tab-item
            v-for="i in 3"
            :key="i"
            :value="`tab-${i}`">
          </v-tab-item>

        </v-tabs-items>
      </v-col>
      <v-col class="col-3">
        <p>"{{ keyword }}"에 대해 {{searchType}}로 검색한 결과입니다.</p>
        <p>{{ $route.query.title}}</p>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  name: 'Search',
  data: function () {
    return {
      searchType: this.$route.params.type,
      keyword: this.$route.query.keyword
    }
  },
}
</script>

<style>

</style>