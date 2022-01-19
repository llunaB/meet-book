import AuthService from "../services/auth.service"

const user = JSON.parse(localStorage.getItem('jwt'))
const initialState = user ? {status: { logedIn: true }, user }
: {status: { logedIn: false}, user: null }

export const auth = {
    namespace: true,
    state: initialState,
    actions: {
        login({ commit }, user) {
            return AuthService.login(user).then(
                user => {
                    commit('loginSuccess', user)
                    return Promise.resolve(user)
                },
                e => {
                    commit('loginFailure')
                    return Promise.reject(e.response.data)
                }
            )
        },
        logout({ commit }) {
            AuthService.logout()
            commit('logout')
        },
        register({ commit }, user) {
            return AuthService.register(user).then(
                res => {
                    commit('registerSuccess')
                    console.log(res)
                    return Promise.resolve(res.data)
                },
                e => {
                    commit('registerFailure')
                    return Promise.reject(e.response.data)
                }
            )
        }
    },
    mutations: {
        loginSuccess(state, user) {
            state.status = { logedIn: true },
            state.user = user
        },
        loginFailure(state) {
            state.status = {},
            state.user = null
        },
        logout(state) {
            state.status = {},
            state.user = null
        },
        registerSuccess(state) {
            state.status = {}
        },
        registerFailure(state) {
            state.status = {}
        }
    }
}