import axios from "axios";
import router from "@/router/routes";
import {useMainStore} from "@/store";

const instance = axios.create({
    baseURL: 'http://localhost:8080/',
    timeout: 5000,
    headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
    },
    withCredentials: true,
})

instance.interceptors.response.use(function (response) {
    if (response.data.code === 1004) {
        const store = useMainStore()
        store.$patch({
            user: null
        })
        router.replace('/').then()
    }
    return response
}, function (error) {
    return Promise.reject(error)
})

export default instance
