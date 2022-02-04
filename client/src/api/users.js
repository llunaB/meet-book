export default class User {
    constructor(name, useremail, nickname, password, passwordConfirm) {
        this.name = name
        this.nickname = nickname
        this.email = useremail
        this.password = password
        this.passwordConfirm = passwordConfirm
    }
  }