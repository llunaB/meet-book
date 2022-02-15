import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home'

import Login from '@/views/accounts/Login'
import Signup from '@/views/accounts/Signup'
import Profile from '@/views/accounts/Profile'
import Settings from "@/views/accounts/Settings";

import ConferenceIndex from '@/views/conference/ConferenceIndex'
import ConferenceMeeting from '@/views/conference/ConferenceMeeting'
import CreateConference from '@/views/conference/CreateConference'
import ConferenceSchedule from '@/views/conference/ConferenceSchedule'

import Search from '@/views/Search'

import Mainscreen from '@/views/conference/Mainscreen'

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
    component: Profile,
    props: true,
  },
  // 주의: Router에서 CreateConference가 ConferenceMeeting보다 밑에 있으면 작동하지 않습니다.
  {
    path: '/conference/new',
    name: 'CreateConference',
    component: CreateConference
  },
  {
    path: '/conference/schedule',
    name: 'ConferenceSchedule',
    component: ConferenceSchedule,
  },
  {
    path: '/conference/:conferenceId/main',
    name: 'Mainscreen',
    component: Mainscreen
  },
  {
    path: '/conference/:conferenceId',
    name: 'ConferenceMeeting',
    component: ConferenceMeeting
  },
  {
    path: '/accounts/settings',
    name: 'Settings',
    component: Settings
  },
  {
    path: '/books/:id',
    name: 'Bookinfo',
    component: () => import('@/views/Bookinfo')
  },
  {
    path: '/search/:type',
    name: 'Search',
    component: Search
  },

  

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
