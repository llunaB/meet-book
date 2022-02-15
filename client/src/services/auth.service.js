import axios from "axios"

const SERVER_URL = process.env.VUE_APP_SERVER_URL
const API_URL = SERVER_URL + '/users/'

class AuthService {
    login(user) {
        return axios.post(API_URL + 'login', {
            email: user.email,
            password: user.password
        })
        .then(res => {
            if (res.data.accessToken) localStorage.setItem('user', JSON.stringify(res.data))
            return res.data
        })
    }
    logout (){
        localStorage.removeItem('user')
    }

    register(user) {
        return axios.post(API_URL + "signup", {
            email: user.email,
            nickname: user.nickname,
            password: user.password,
        })
      }
}

export default new AuthService()