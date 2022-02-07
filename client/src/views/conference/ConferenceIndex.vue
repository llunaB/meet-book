<template>
<div>

  <v-card>
    <v-tabs v-model="tab">      
      <v-tab v-for="genre in genres" :key="genre" @click="loging">
        {{genre}}
      </v-tab>
    </v-tabs>
    <v-tabs-items v-model="tab">
      <v-tab-item v-for="genre in genres" :key="genre">
        <v-container>
          <v-row>
            <conference-card class="mx-2 my-2 col-2" v-for="conference in conferences[tab].slice((page-1)*24,(page-1)*24+24)" :key="conference.id" :conference="conference">
            </conference-card>
          </v-row>
        </v-container>
      </v-tab-item>
    </v-tabs-items>
  </v-card>
  <div class="text-center">
    <v-pagination v-model="page" :length="arrLength">  
    </v-pagination>
  </div>
</div>
</template>
<script>
import ConferenceCard from '@/components/home/ConferenceCard'

export default {
  name: "ConferenceIndex",
  data() {
    return {
      genres: ['총류', '철학','종교','사회과학','순수과학','기술과학',
      '예술','언어','문학','역사'],
      tab: 0,
      page: 1,
      conferences:[[{"id":1, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome","isActive":true},],
        [{"id":2, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":true},
        {"id":3, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome","isActive":true},
        {"id":4, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":true},
        {"id":5, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome","isActive":true},
        {"id":6, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":true},
        {"id":7, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome","isActive":true},
        {"id":8, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":true},
        {"id":9, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome","isActive":true},
        {"id":10, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
        {"id":11, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":12, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
        {"id":13, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":14, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
        {"id":15, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":16, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
        {"id":17, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":18, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
        {"id":19, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":20, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
        {"id":21, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":22, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
        {"id":23, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":24, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
        {"id":25, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":26, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
        {"id":27, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},],
        [{"id":28, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},
        {"id":29, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},],
        [{"id":30, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2", "isActive":false},
        {"id":31, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},],
        [{"id":32, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2", "isActive":false},
        {"id":33, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome","isActive":true},],
        [{"id":34, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":true},
        {"id":35, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome","isActive":true},],
        [{"id":36, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2","isActive":false},],
        [{"id":37, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},],
        [{"id":38, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2", "isActive":false},
        {"id":39, "title":"오이디푸스", "thumbnail":"https://image.yes24.com/momo/TopCate393/MidCate005/6417738.jpg","description":"Welcome", "isActive":false},
        {"id":40, "title":"정의란 무엇인가", "thumbnail":"https://image.yes24.com/goods/15156691/XL", "description":"welcome2", "isActive":false},]
      ] 
    }
  },
  methods: {
    loging: function() {
      console.log(this.tab)
      this.page = 1
    }
  },
  components: {
    ConferenceCard,
  },
  computed: {
    arrLength: function() {
      return parseInt(this.conferences[this.tab].length / 25) + 1
    }
  }
}
</script>
<style scoped>
.row {
  justify-content: center;
}
</style>