<template>
  <v-container class="profileForm" style="padding-top:5rem;">
    <v-row>
      <v-col class="text-center align-self-center justify-center text-center" cols="3">
        <v-avatar v-if="user.profileImage" size="150">
          <v-img :src="user.profileImage" contain />
        </v-avatar>
        <v-avatar v-else size="150">
          <v-img src="@/assets/host_img/HostImg.png" />
        </v-avatar>

      </v-col>
      <v-col cols="8" style="margin-left: 2rem">
        <div class="d-flex">
          <h1 style="display:inline;">{{ user.nickname }}님의 개인 프로필</h1>
          <v-img :src="`src/assets/bookicon/book${num}page.svg`" style="height:50px;" />
        </div>
        <br>
        <v-card>
          <v-card-title><strong>한마디</strong></v-card-title>
          <v-spacer></v-spacer>
          <v-card-subtitle v-if="user.profileDescription.length > 0">{{ user.profileDescription }}</v-card-subtitle>
          <v-card-subtitle v-else>아직 한마디가 없어요..</v-card-subtitle>
        </v-card>
        <span v-if="conferences.length > 0">{{ conferences.length }}개의 책을 읽었어요!!</span>
      </v-col>
    </v-row>
    <v-col style="padding-top:2rem">
      <v-item-group v-if="!conferences.length" style="text-align-last: center">
        <h2 style="color: #ff3170;">아직 함께 참여한 모임이 없어요 ㅠㅠ </h2>
        <br>
        <v-btn v-if="searchUser === name" rounded class="primary" href="conference">참여하러가기</v-btn>
      </v-item-group>
      <div v-else>
        <h3>최근 참가한 모임</h3>
        <v-item-group>
          <v-container>
            <v-row>
              <v-col cols="6" v-for="(item, idx) in conferences.slice(0, 5)" :key="idx">
                <v-item>
                  <v-card
                    class="bookcard"
                    max-width="0"
                    :to="{name: 'Bookinfo', params: {id: item.book.id}}"
                      >
                      <template>
                        <div class="book">
                          <div class="back"></div>
                          <div class="page6">
                            <v-card-text style="padding:0;">
                              <v-card-title style="font-size:15px; font-weight:bold;">{{ item.title }}</v-card-title>
                              <v-card-subtitle >
                                모임 날짜: {{ item.callStartTime.slice(0, 10) }}
                                <hr>
                                시작 시간: {{ item.callStartTime.slice(11, 19) }}
                                <br>
                                종료 시간: {{ item.callEndTime.slice(11, 19) }}
                              </v-card-subtitle>
                            </v-card-text>
                          </div>
                          <div class="page5" /><div class="page4" /><div class="page3" /><div class="page2" /><div class="page1" />
                          <div class="front">
                            <v-img :src="item.book.bookThumbnailUrl" contain />
                          </div>
                        </div>
                      </template>
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
  </v-container>
</template>

<script>
import axios from "axios";
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  name: 'Profile',
  data() {
    return {
      user: {},
      conference: '',
      conferences: [],
      searchUser : this.$route.params.userId,
      name: this.$store.state.auth.user.id,
      cnt: 0,
      num: '1',

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
    userProfile() {
      if (this.searchUser === undefined) {
        this.searchUser = this.name
      }
      axios({
        baseURL: SERVER_URL,
        url: '/users/' + this.searchUser,
        method: 'GET'
      })
        .then(res => {
          this.user = res.data
          console.log(this.user)
          })
        .catch(e => console.log(e))
    },
    userBookmark() {
      axios({
        baseURL: SERVER_URL,
        url:`/users/${this.searchUser}/bookmark`,
        method: 'GET',
        headers: {
          'X-AUTH-TOKEN': this.$store.state.auth.user.token
        }
      })
      .then(res => {
        for (let index = 0; index < res.data.length; index++) {
          axios({
            baseURL: SERVER_URL,
            url: `/conference/${res.data[index].conferenceId}`,
            method: 'GET',
          })
          .then(res => {
            if (!this.conferences.includes(res.data)) this.conferences.push(res.data)
            this.cnt += 1
          })
        }
        console.log(this.conferences)
        if (this.cnt >= 500) {
          this.cnt = 500
        } else if (this.cnt >= 250) {
          this.num = '250'
        } else if (this.cnt >= 100) {
          this.num = '100'
        } else if (this.cnt >= 50) {
          this.num = '50'
        } else if (this.cnt >= 25) {
          this.num = '25'
        } else if (this.cnt >= 10) {
          this.num = '10'
        } else if (this.cnt >= 5) {
          this.num = '5'
        } else if (this.cnt >= 0) {
          this.num = '1'
        }
      })
      .catch(e => console.log(e))
    },
  },
  beforeMount() {
    this.userProfile()
    this.userBookmark()
  },
}
</script>

<style>

.img-upload > input {
  display: none;
}

.book {
  transform-style: preserve-3d;
  position: relative;
  width: 170px;
  height: 247px;
  margin: 1rem;
  cursor: pointer;
  backface-visibility: visible;
}

.front, .back, .page1, .page2, .page3, .page4, .page5, .page6 {
  transform-style: preserve-3d;
  position: absolute;
  width: 170px;
  height: 247px;
  transform-origin: right center;
  transition: transform .5s ease-in-out, box-shadow .35s ease-in-out;
}

.front, .page1, .page3, .page5 {
  border-bottom-right-radius: .5em;
  border-top-right-radius: .5em;
}

.back, .page2, .page4, .page6 {
  border-bottom-right-radius: .5em;
  border-top-right-radius: .5em;
}

.page1 { 
  background: #efefef;
}

.page2 {
  background: #efefef;
}

.page3 {
  background: #f5f5f5;
}

.page4 {
  background: #f5f5f5;
}

.page5 {
  background: #fafafa;
  display: flex;
  flex-direction: column-reverse;
}

.page6 {
  background: #fdfdfd;
}

.book:hover .front {
  transform: rotateY(160deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .page1 {
  transform: rotateY(150deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .page2 {
  transform: rotateY(30deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .page3 {
  transform: rotateY(140deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .page4 {
  transform: rotateY(40deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .page5 {
  transform: rotateY(130deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .page6 {
  transform: rotateY(45deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .back {
  transform: rotateY(20deg) scale(1.1);
}
</style>
