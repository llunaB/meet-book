import axios from "axios"

const API_URL = 'localhost:8080/api/auth/'

class AuthService {
    login(user) {
        return axios.post(API_URL + 'login', {
            useremail: user.useremail,
            password: user.password
        })
        .then(this.handleResponse)
        .then(res => {
            if (res.data.accessToken) {
                localStorage.setItem('user', JSON.stringify(res.data))
            }
            return res.data
        })
    }
    logout (){
        localStorage.removeItem('jwt')
    }
    handleResponse (res){
        if (res.status === 401){
            this.logout()
            location.reload(true)
    
            const error = res.data && res.data.message
            return Promise.reject(error)
        }
        return Promise.resolve(res)
    }
}

export default new AuthService()