import {createApp} from 'vue'
import App from './App.vue'
import router from './router/routes'
import {createPinia} from "pinia"
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import {Quasar, Notify} from 'quasar'
import 'quasar/dist/quasar.css'
import iconSet from 'quasar/icon-set/material-symbols-outlined'
import '@quasar/extras/material-symbols-outlined/material-symbols-outlined.css'

createApp(App)
    .use(Quasar, {
        iconSet: iconSet,
        plugins: {
            Notify
        },
    })
    .use(router)
    .use(createPinia().use(piniaPluginPersistedstate))
    .mount('#app')
