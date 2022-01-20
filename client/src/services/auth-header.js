export default function authheader () {
    const user = JSON.parse(localStorage.getItem('user'))
    if (user && user.accessToken) {
        return { Authorization: 'JWT ' + user.accessToken}
    } else {
        return {}
    }
}