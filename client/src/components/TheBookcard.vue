<template>
  <v-card
    class="TheBookcard"
    max-width="400"
    elevation="2">
    <v-card-title>
      {{ book.title }}
    </v-card-title>
    <v-card-text>
      <p>텍스트 연습입니다.</p>
      <p>{{ book.author }}</p>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: 'TheBookcard',

  // props: {
  //   book: String,
  // },

  data: function () {
    return {
      book: {
        title: "주기율표",
        author: "프리모 레비",
      },
    }
  },
}
</script>

<style>

</style>