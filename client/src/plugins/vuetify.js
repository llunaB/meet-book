// src/plugins/vuetify.js

import Vue from 'vue'
import Vuetify from 'vuetify/lib'
import {library} from '@fortawesome/fontawesome-svg-core'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
import {fas} from '@fortawesome/free-solid-svg-icons'

Vue.component('font-awesome-icon', FontAwesomeIcon) // Register component globally
library.add(fas) // Include needed icons

Vue.use(Vuetify)

export default new Vuetify({
  theme: {
    themes:{
      light: {
        primary: '#798F88',
        secondary: '#FAF6EA',
        accent: '#2A261B',
        error: '#F7DBCE',
      }
    }
  },
  customVariables: ['~/assets/scss/variables.scss'],
}
)