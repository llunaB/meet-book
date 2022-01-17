<template>
  <v-card
    class="TheBookcard"
    elevation="2">
    {{ book.title }}
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
        title: "주기율표"
      },
    }
  },
}
</script>

<style>

</style>