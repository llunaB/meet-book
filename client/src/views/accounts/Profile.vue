<template>
  <v-container style="padding: 3rem">
    <v-row>
      <v-col class="text-center align-self-center justify-center text-center" cols="3">
        <v-avatar v-if="user.profile_image" :src="user.profile_image" />
        <div v-else class="img-upload">
          <label for="file-input">
            <v-avatar size="150" color="primary">{{ user.nickname }}</v-avatar>
          </label>
          <input id="file-input" type="file" />
        </div>

      </v-col>
      <v-col cols="8" style="margin-left: 2rem">
        <h2>{{ user.nickname }}님의 개인 프로필</h2>
        <br>
        <v-card>
          <strong>한마디</strong>
          <p>{{ user.profile_description }}</p>
        </v-card>
        <p>지난 한달간 {p}권의 책을 읽었어요!</p>
      </v-col>
    </v-row>
    <v-col style="padding-top:2rem">
      <h3>최근 모임에서 읽은 책</h3>
      <v-item-group>
        <v-container>
          <v-row>
            <v-col v-for="item in conferences.slice(0, 5)" :key="item" md="2.2">
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
            <p>모임 종료 시간: {{ conference.call_end_time }}</p>
            <br>
            <p>{{ conference.description }}</p>
          </v-container>
        </v-col>
        <span>관련 Tag들: {{ conference.tag }}</span>
        <v-footer color="white" class="item-center">
          <v-col class="text-end">
            <a @click="GoToConference(conference.id)">더보기</a>
          </v-col>
        </v-footer>
      </v-card>
    </v-col>
    <v-col>
      <h3>등급</h3>
      <br>
      <v-row>
        <v-img :src="getImg(user.host_point)" contain aspect-ratio="3" />
        <v-img :src="getImg(user.guest_point)" contain aspect-ratio="3" />
      </v-row>

    </v-col>
  </v-container>
</template>

<script>

export default {
  name: 'Profile',
  data() {
    return {
      user: {"name": 'eonyong', "nickname": "yong", "host_point": 10, "guest_point": 11, "profile_description": "hihihihi"},
      conference: '',
      conferences: [
        {"id":1, "title":"오이디푸스1", "call_start_time": "2020-12-29", "thumbnail_url":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"asdasd","isActive":true, "tag": "#하루#종일"},
        {"id":2, "title":"오이디푸스2", "call_start_time": "2020-12-29", "thumbnail_url":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"asdasd","isActive":true, "tag": "#하루#종일"},
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
    },
    GoToConference(id) {
      this.$router.push("conference/" + id)
    },
    getImg(host_point) {
      if (host_point > 10) {
        return require('@/assets/host_img/images.jpeg')
      }
      else {
        return require('@/assets/host_img/host2.jpeg')
      }
    }
  }
}
</script>

<style>

.img-upload > input {
  display: none;
}


</style>