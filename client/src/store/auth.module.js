import AuthService from "../services/auth.service"
import axios from 'axios'

const SERVER_URL = process.env.VUE_APP_SERVER_URL
const user = JSON.parse(localStorage.getItem('user'))
const initialState = user ? {status: { loggedIn: true }, user }
                        : {status: { loggedIn: false}, user: null }

export const auth = {
    namespaced: true,
    state: initialState,
    actions: {
        snslogin({ commit }, user) {
            let token = user.token
                    let id = parseInt(JSON.parse(Buffer.from(user.token.split('.')[1], 'base64'))["sub"])
                    axios({
                        baseURL: SERVER_URL,
                        url: `users/${id}/detail`,
                        method: 'GET',
                        headers: {
                            'X-AUTH-TOKEN': user.token
                        },
                    })
                    .then(res => {
                        res.data['token'] = token
                        commit('loginSuccess', res.data)
                        return Promise.resolve(res.data)
                    })
        },
        login({ commit }, user) {
            return AuthService.login(user).then(
                user => {
                    let token = user.token
                    let id = parseInt(JSON.parse(Buffer.from(user.token.split('.')[1], 'base64'))["sub"])
                    axios({
                        baseURL: SERVER_URL,
                        url: `users/${id}`,
                        method: 'GET',
                        headers: {
                            'X-AUTH-TOKEN': user.token
                        },
                    })
                    .then(res => {
                        res.data['token'] = token
                        commit('loginSuccess', res.data)
                        return Promise.resolve(res.data)
                    })
                }).catch(e => {
                    commit('loginFailure')
                    return Promise.reject(e.response.data)})
        },
        logout({ commit }) {
            AuthService.logout()
            commit('logout')
        },
        register({ commit }, user) {
            console.log(user)
            return AuthService.register(user).then(
                res => {
                    commit('registerSuccess')
                    return Promise.resolve(res.data)
                }).catch(e => {
                    commit('registerFailure')
                    return Promise.reject(e)})
        }
    },
    mutations: {
        loginSuccess(state, user) {
            state.status.loggedIn = true
            state.user = user
        },
        loginFailure(state) {
            state.status.loggedIn = false
            state.user = null
        },
        logout(state) {
            state.status.loggedIn = false
            state.user = null
        },
        registerSuccess(state) {
            state.status.loggedIn = false
        },
        registerFailure(state) {
            state.status.loggedIn = false
        }
    }
}