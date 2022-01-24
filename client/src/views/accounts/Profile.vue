<template>
  <v-container style="padding: 3rem">
    <v-row>
      <v-col class="text-center" cols="3">
        <v-img v-if="user.profile_image" :src="user.profile_image" />
        <div v-else class="img-upload">
          <label for="file-input">
            <v-icon size="7rem">mdi-account-edit</v-icon>
          </label>
          <input id="file-input" type="file" />
        </div>

      </v-col>
      <v-col cols="8" style="margin-left: 2rem">
        <h2>{{ user.nickname }} hi</h2>
        <p>{{ user.profile_description }} hi</p>
        <p>지난 한달간 {p}권의 책을 읽었어요!</p>
      </v-col>
    </v-row>
    <v-col style="padding-top:2rem">
      <h3>최근 모임에서 읽은 책</h3>
      <v-item-group>
        <v-container>
          <v-row>
            <v-col v-for="item in conferences.slice(0, 5)" :key="item" md="2.2">
              <v-item v-slot:default="active">
                <v-card
                    :color="active ? 'primary' : ''"
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
    <v-col v-if="conference">
      <h3>선택한 모임 정보</h3>
      <br>
      <v-card>
        <v-col class="d-flex">
          <v-img :src="conference.thumbnail_url" max-width="256px" height="100%"></v-img>
          <v-container class="overflow-auto">
            <h2>{{ conference.title }}</h2>
            <br>
            <p>모임 시작 시간: {{ conference.call_start_time }}</p>
            <p>모임 시작 시간: {{ conference.call_end_time }}</p>
            <br>
            <p>{{ conference.description }}</p>
          </v-container>
        </v-col>
        <span>관련 Tag들: {{ conference.tag }}</span>
      </v-card>
    </v-col>
  </v-container>
</template>

<script>
export default {
  name: 'Profile',
  data() {
    return {
      user: '',
      conference: '',
      conferences: [
        {"id":1, "title":"오이디푸스1", "call_start_time": "2020-12-29", "thumbnail_url":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"WelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcome","isActive":true, "tag": "#하루#종일"},
        {"id":2, "title":"오이디푸스2", "call_start_time": "2020-12-29", "thumbnail_url":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"WelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcomeWelcome","isActive":true, "tag": "#하루#종일"},
        {"id":3, "title":"정의란 무엇인가", "call_start_time": "2020-12-29", "thumbnail_url":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":true},
        {"id":4, "title":"오이디푸스", "thumbnail_url":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome","isActive":true},
        {"id":5, "title":"정의란 무엇인가", "thumbnail_url":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
        {"id":6, "title":"오이디푸스", "thumbnail_url":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":7, "title":"정의란 무엇인가", "thumbnail_url":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2", "isActive":false},
        {"id":8, "title":"오이디푸스", "thumbnail_url":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":9, "title":"정의란 무엇인가", "thumbnail_url":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2", "isActive":false},]
    }
  },
  methods: {
    onclick(item) {
      this.conference = item
    }
  }
}
</script>

<style>

.img-upload > input {
  display: none;
}


</style>