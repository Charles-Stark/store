import {defineStore} from "pinia";
import {ref} from "vue";
import {getUserProfile, loginWithPassword, userLogout, userRegister} from "@/request/user";
import router from "@/router/routes";
import {useNotify} from "@/utils/notify";

export const useMainStore = defineStore('main', () => {
    const user = ref(null)

    const notify = useNotify()

    function login(username, password) {
        loginWithPassword(username, password)
            .then((res) => {
                if (res.data.code === 0) {
                    this.user = res.data.data
                    notify.success(res.data["msg"])
                    router.replace('/').then()
                } else {
                    notify.fail(res.data["msg"])
                }
            })
    }

    function logout() {
        userLogout()
            .then((res) => {
                this.user = null
                if (res.data.code === 0) {
                    notify.success(res.data["msg"])
                } else {
                    notify.fail(res.data["msg"])
                }
                router.replace('/').then()
            })
    }

    function register(useEmail, emailOrPhone, username, password, code) {
        userRegister(useEmail, emailOrPhone, username, password, code)
            .then((res) => {
                if (res.data.code === 0) {
                    this.user = res.data.data
                    notify.success(res.data["msg"])
                    router.replace('/').then()
                } else {
                    notify.fail(res.data["msg"])
                }
            })
    }

    function getProfile() {
        getUserProfile()
            .then((res) => {
                if (res.data.code === 0) {
                    this.user = res.data.data
                } else {
                    this.user = null
                }
            })
    }

    return {user, login, logout, register, getProfile}
}, {
    persist: {
        storage: localStorage,
    }
})
