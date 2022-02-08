export default class User {
    constructor(useremail, nickname, password, passwordConfirm) {
        this.nickname = nickname
        this.email = useremail
        this.password = password
        this.passwordConfirm = passwordConfirm
    }
  }