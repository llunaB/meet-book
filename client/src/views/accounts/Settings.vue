<template>
  <v-container style="padding: 3rem">
    <v-row>
      <v-col class="text-center align-self-center justify-center text-center" cols="3">
        <v-avatar v-if="loggedinUser.profile_image" :src="loggedinUser.profile_image" />
        <div v-else class="img-upload">
          <label for="file-input">
            <v-avatar size="150" color="primary">{{ loggedinUser.nickname }}</v-avatar>
          </label>
          <input id="file-input" type="file" />
        </div>

      </v-col>
      <v-col cols="8" style="margin-left: 2rem">
        <h2>{{ loggedinUser.nickname }}님의 개인 프로필</h2>
        <br>
        <v-card>
          <strong>한마디</strong>
          <p>{{ loggedinUser.profile_description }}</p>
        </v-card>
      </v-col>
    </v-row>
    <v-col style="padding-top:2rem">
      <h3>최근 모임에서 읽은 책</h3>
      <v-item-group>
        <v-container>
          <v-row>
            <v-col v-for="(item, idx) in conferences.slice(0, 5)" :key="idx" md="2.2">
              <v-item>
                <v-card
                    class="d-flex align-center"
                    width="10%"
                >
                  <v-img
                      max-width="128px"
                      @click="onclick(item)"
                      :src="item.thumbnail_url"
                  />
                </v-card>
              </v-item>
            </v-col>
          </v-row>
        </v-container>
      </v-item-group>
    </v-col>
    <v-col style="padding-top: 2rem">

    </v-col>
  </v-container>
</template>

<script>
export default {
  name: "Settings",
  data() {
    return {
      name: '',
    }
  }
}
</script>

<style scoped>

</style>