<template>
  <v-container style="padding: 3rem">
    <v-row>
      <v-col class="text-center align-self-center justify-center text-center" cols="3">
        <v-avatar v-if="user.profileImage" :src="user.profileImage" />
        <div v-else class="img-upload">
          <v-avatar size="150">
            <v-img src="@/assets/host_img/HostImg.png" />
          </v-avatar>
          <input id="file-input" type="file" />
        </div>

      </v-col>
      <v-col cols="8" style="margin-left: 2rem">
        <h2>{{ user.nickname }}님의 개인 프로필</h2>
        <br>
        <v-card>
          <strong>한마디</strong>
          <p>{{ user.profileDescription }}</p>
        </v-card>
        <span>{{ conferences.length }}개의 모임이 예약되어 있어요!!</span>
      </v-col>
    </v-row>
    <v-col style="padding-top:2rem">
      <v-item-group v-if="!this.conferences.length" style="text-align-last: center">
        <h2 style="color: #ff3170;">아직 함께 참여한 모임이 없어요 ㅠㅠ </h2>
        <br>
        <v-btn v-if="searchUser === name" rounded class="primary" href="conference">참여하러가기</v-btn>
      </v-item-group>
      <div v-else>
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
      </div>
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
import axios from "axios";
import User from "@/api/users"
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'Profile',
  data() {
    return {
      user: new User(),
      conference: '',
      conferences: [],
      name: this.$store.state.auth.user.id,
      searchUser: this.$route.params.userId,
    }
  },
  methods: {
    onclick(item) {
      this.conference = item
    },
    GoToConference(id) {
      this.$router.push("conference/" + id)
    },
    getImg(point) {
      if (point > 10) {
        return require('@/assets/host_img/images.jpeg')
      } else {
        return require('@/assets/host_img/host2.jpeg')
      }
    },
    // 일단은 본인 프로필로 입장이여서 this.$store.state.auth.user.id를 사용 했습니다.
    // 이후에 다른 유저가 들어올 경우에는 해당 부분을 수정하여 props한 값을 넣으면 됩니다.
    userProfile() {
      if (!this.searchUser) {
        this.searchUser = this.name
      }
      axios({
        baseURL: SERVER_URL,
        url:`/users/${this.name}/detail`,
        method: 'GET',
        headers: {
        "X-Auth-Token": this.$store.state.auth.user.token,
        "content-type": "application/json"}
      })
        .then(res => this.user = res.data)
        .catch(() => console.log('hhhhh'))
    },
    userBookmark() {
      axios({
        baseURL: SERVER_URL,
        url:`/users/${this.searchUser}/bookmark`,
        method: 'GET'
      })
      .then(res => this.conferences = res.data)
      .catch(() => console.log('asdasdasd'))}
  },
  beforeMount() {
    this.userProfile()
  }
}
</script>

<style>

.img-upload > input {
  display: none;
}


</style>
