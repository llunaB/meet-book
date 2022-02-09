export default class User {
    constructor(nickname, email, password, passwordConfirm, profileDescription, profileImage) {
        this.nickname = nickname
        this.email = email
        this.password = password
        this.passwordConfirm = passwordConfirm
        this.profileDescription = profileDescription
        this.profileImage = profileImage
    }
  }