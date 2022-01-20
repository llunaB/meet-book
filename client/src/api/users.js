export default class User {
    constructor(username, email, password, passwordConfirm) {
      this.username = username;
      this.email = email;
      this.password = password;
      this.passwordConfirm = passwordConfirm;
    }
  }