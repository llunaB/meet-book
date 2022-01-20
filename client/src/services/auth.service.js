import axios from "axios"

const API_URL = 'localhost:8080/accounts/'

class AuthService {
    login(user) {
        return axios.post(API_URL + 'api-token-auth/', {
            useremail: user.useremail,
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
        return axios.post(API_URL + "signup/", {
          username: user.username,
          password: user.password,
          passwordConfirmation: user.passwordConfirm,
          email: user.email,
        })
      }
}

export default new AuthService()