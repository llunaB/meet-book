import axios from "axios"

const API_URL = 'https://localhost:8080/users/'

class AuthService {
    login(user) {
        return axios.post(API_URL + 'api-token-auth/', {
            email: user.email,
            password: user.password
        })
        .then(res => {
            if (res.data.accessToken) {
                localStorage.setItem('user', JSON.stringify(res.data))
            }
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