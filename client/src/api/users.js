export default class User {
    constructor(username, email, nickname, password, passwordConfirm) {
      this.username = username
      this.nickname = nickname
      this.email = email
      this.password = password
      this.passwordConfirm = passwordConfirm
    }
  }