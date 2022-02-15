<template>
<div>
  <v-card>
    <v-tabs v-model="tab">      
      <v-tab v-for="genre in genres" :key="genre" @click="loging()">
        {{genre}}
      </v-tab>
    </v-tabs>
    <v-tabs-items v-model="tab">
      <v-tab-item v-for="genre in genres" eager :key="genre">
        <v-container>
          <v-row>            
              <!-- <conference-card  class="mx-2 my-2 col-2" v-for="conference in conferences[tab].slice((page-1)*24,(page-1)*24+24)" :key="conference.id" :conference="conference">
              </conference-card>             -->
              <conference-card  class="mx-2 my-2 col-2" v-for="conference in conferences[tab][page-1]" :key="conference.id" :conference="conference">
              </conference-card>            
          </v-row>
        </v-container>
      </v-tab-item>
    </v-tabs-items>
  </v-card>
  <div class="text-center">
    <v-pagination v-model="page" :length="genrePages">  
    </v-pagination>
  </div>
</div>
</template>
<script>
import ConferenceCard from '@/components/home/ConferenceCard'
import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "ConferenceIndex",
  data() {
    return {
      genres: ['총류', '철학','종교','사회과학','순수과학','기술과학',
      '예술','언어','문학','역사'],
      tab: 0,
      page: 1,      
      showing: null,
      conferences: [[],[],[],[],[],[],[],[],[],[]],
      genrePages: 0,
    }
  },
  methods: {
    loging: function() {
      console.log(this.tab)      
      this.page = 1
    },
    // loading: function() {
    //   console.log(this.genres[this.tab])
    //   axios({
    //     method: 'get',
    //     url: `/conference/list/${this.page}`,        
    //   })
    //   .then(res=>{
    //     this.conferences[10] = res.data
    //     console.log("a",this.tab)})
    //   .catch(err => console.error(err))
    // }
  },
  components: {
    ConferenceCard,
  },  
  watch: {
    tab: function(){
      if(this.conferences[this.tab].length < 1){
        axios({
          baseURL: SERVER_URL,
          method: 'get',
          url: `search/conference/genre?genre=${this.genres[this.tab]}&page=${this.page-1}&size=20`,          
        })
        .then(res=> {
          console.log(res)
          this.conferences[this.tab].push(res.data.content)     
        })
        .catch(err => console.error(err))
      }
      axios({
        method: 'get',
        baseURL: SERVER_URL,
        url: `search/conference/genre/count?genre=${this.genres[this.tab]}`
      })
      .then(res=> this.genrePages=parseInt((parseInt(res.data.data)-1)/20) + 1)
      .catch(err => console.error(err))
    },
    page: function(){
      if(this.conferences[this.tab].length < this.page){
        axios({
          baseURL: SERVER_URL,
          method: 'get',
          url: `search/conference/genre?genre=${this.genres[this.tab]}&page=${this.page-1}&size=20`,          
        })
        .then(res=> {
          console.log(res)
          this.conferences[this.tab].push(res.data.content)     
        })
        .catch(err => console.error(err))      
      }
    }
  },
  mounted(){
    axios({
        baseURL: SERVER_URL,
        method: 'get',
        url: `search/conference/genre?genre=${this.genres[this.tab]}&page=${this.page-1}&size=20`,
        // headers: {
        //   'X-AUTH-TOKEN': this.$store.state.auth.user.token
        // }
      })
      .then(res=> {        
        console.log(res)
        this.conferences[this.tab].push(res.data.content)
      })
      .catch(err => console.error(err))

    axios({
        method: 'get',
        baseURL: SERVER_URL,
        url: `search/conference/genre/count?genre=${this.genres[this.tab]}`
      })
      .then(res=> this.genrePages=parseInt(parseInt(res.data.data)/20) + 1)
      .catch(err => console.error(err))
  }
}
</script>
<style scoped>
.row {
  justify-content: center;
}
</style>