export default class User {
    constructor(username, useremail, nickname, password, passwordConfirm) {
        this.username = username
        this.nickname = nickname
        this.email = useremail
        this.password = password
        this.passwordConfirm = passwordConfirm
    }
  }