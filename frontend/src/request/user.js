import request from "@/request/request";

export const loginWithPassword = (emailOrPhone, password) => {
    return request({
        method: "POST",
        url: 'api/user/login',
        data: {
            emailOrPhone,
            password,
        }
    })
}

export const userRegister = (useEmail, emailOrPhone, username, password, code) => {
    return request({
        method: "POST",
        url: 'api/user/register',
        data: {
            useEmail,
            emailOrPhone,
            username,
            password,
            code,
        }
    })
}

export const userLogout = () => {
    return request({
        method: "POST",
        url: 'api/user/logout',
    })
}

export const getUserProfile = () => {
    return request({
        method: "GET",
        url: 'api/user/profile',
    })
}

export const updateUsername = (username) => {
    return request({
        method: "POST",
        url: 'api/user/username',
        data: {
            username,
        }
    })
}
