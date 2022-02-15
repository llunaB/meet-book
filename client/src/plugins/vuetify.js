// src/plugins/vuetify.js

import Vue from 'vue'
import Vuetify from 'vuetify/lib'
import {library} from '@fortawesome/fontawesome-svg-core'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
import {fas} from '@fortawesome/free-solid-svg-icons'
import 'material-design-icons-iconfont/dist/material-design-icons.css'

Vue.component('font-awesome-icon', FontAwesomeIcon) // Register component globally
library.add(fas) // Include needed icons

Vue.use(Vuetify)

export default new Vuetify({
  theme: {
    themes:{
      light: {
        primary: '#568D6C', // 녹색
        secondary: '#1E1E1E', // 검정
        accent: '#FFB61D', // 골드
        error: '#F7DBCE',
        
      }
    }
  },
  icons: {
    iconfont: 'mdi', // default - only for display purposes
  },
  customVariables: ['~/assets/scss/variables.scss'],
}
)