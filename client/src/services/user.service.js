import axios from "axios"
import authheader from "./auth-header"

const API_URL = 'http://localhost:8080/api/user/'

class UserService {
    getUserContent() {
        return axios.get(API_URL + 'userContent', { headers: authheader() })
    }
}

export default new UserService()