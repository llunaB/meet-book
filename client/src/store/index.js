import Vue from 'vue'
import Vuex from 'vuex'
import {auth} from './auth.module'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loggedinUser: {
      username: '',
      email: '',
      nickname: '',
      profile_description: '',
      profile_image: ''
    }
  },
  modules: {
    auth
  },
  plugins: [createPersistedState()],
})
