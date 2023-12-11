import {createRouter, createWebHistory} from 'vue-router'
import {useMainStore} from "@/store";

const routes = [
    {
        path: '/',
        name: 'Index',
        component: () => import('@/pages/index.vue')
    },
    {
        path: '/ticket',
        name: 'Ticket',
        component: () => import('@/pages/ticket.vue'),
    },
    {
        path: '/invitation',
        name: 'Invitation',
        component: () => import('@/pages/invitation.vue'),
    },
    {
        path: '/service',
        name: 'Service',
        component: () => import('@/pages/service.vue'),
    },
    {
        path: '/help',
        name: 'Help',
        component: () => import('@/pages/help.vue'),
    },
    {
        path: '/user',
        name: 'User',
        component: () => import('@/pages/user.vue'),
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/pages/login.vue'),
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const store = useMainStore()
    if (store.user === null && !['Index', 'Login', 'Help'].includes(to.name)) {
        next({
            name: 'Login'
        })
    } else if (store.user !== null && to.name === 'Login') {
        next({
            name: 'User'
        })
    } else {
        next()
    }
})

// router.afterEach((to, from, next) => {
//     window.scrollTo(0, 0)
// })

export default router
