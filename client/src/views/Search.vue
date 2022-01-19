<template>
  <div>
    <v-row class="searchtabs">
      <v-tabs>
        <v-tab>회의</v-tab>
        <v-tab>도서</v-tab>
        <v-tab>사용자</v-tab>
      </v-tabs>
    </v-row>
    <v-row>
      <v-col>
        <v-tabs-items>
          <v-tab-item
            v-for="i in 3"
            :key="i"
            :value="`tab-${i}`">
          </v-tab-item>

        </v-tabs-items>
      </v-col>
      <v-col>

      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  name: 'Search',

}
</script>

<style>

</style>