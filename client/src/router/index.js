import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home'
import Login from '@/views/accounts/Login'
import Signup from '@/views/accounts/Signup'
import ConferenceIndex from '@/views/conference/ConferenceIndex'
import Profile from '@/views/accounts/Profile'
import ConferenceMeeting from '@/views/conference/ConferenceMeeting'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '@/views/About.vue')
  },
  {
    path: '/login', name: 'Login', component: Login
  },
  {
    path: '/signup', name: 'Signup', component: Signup
  },
  {
    path: '/conference',
    name: 'ConferenceIndex',
    component: ConferenceIndex
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile
  },
  {
    path: '/conference/:conferenceId',
    name: 'ConferenceMeeting',
    component: ConferenceMeeting
  },
  {
    path: '/accounts/settings',
    name: 'Settings',
    component: Profile
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
