<template>
  <v-card
    class="bookcard"
    outlined
    min-width="560px"
    max-width="560px"
    :to="'/book/' + book.id"
    >
    <div class="d-flex flex-no-wrap justify-space-between">
      <div class="mx-1 my-auto">
        <v-img width="160px" :src="book.thumbnail_url"></v-img>
      </div>
      <div>
        <v-card-title :title="book.title">{{ title_trim }}</v-card-title>
        <v-card-subtitle class="text-right">{{ book.author }}<br>{{ book.pubdate }}</v-card-subtitle>
        <v-divider />
        <v-card-text>
          <p>{{ contents_trim }}</p>
        </v-card-text>
        
      </div>
    </div>

  </v-card>
</template>

<script>
// 변수 설정
const title_max_length = 20
const content_max_length = 120


export default {
  name: 'TheBookcard',

  props: {
    book: Object,
  },


  computed: {
    // title과 content에서 긴 내용을 잘라냅니다.
    title_trim: function () {
      return (this.book.title.length < title_max_length ? this.book.title : this.book.title.slice(0, title_max_length) + ' ...')
    },
    contents_trim: function () {
      return (this.book.contents.length < content_max_length ? this.book.contents : this.book.contents.slice(0, content_max_length) + ' ...')
    },
  },
}
</script>

<style>

</style>