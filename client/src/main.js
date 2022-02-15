import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import axios from 'axios'
import '@/assets/css/style.css'

Vue.config.productionTip = false

Vue.prototype.$axios = axios
window.Kakao.init("657db4e35a0714b2b62135c9ebc39f36")


new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
