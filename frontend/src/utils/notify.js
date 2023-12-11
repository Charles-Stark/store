import {useQuasar} from "quasar";

export function useNotify() {
    const $q = useQuasar()

    function success(message, position = 'top') {
        $q.notify({
            type: 'positive',
            message: message,
            position: position,
            timeout: 700,
        })
    }

    function fail(message, position = 'top') {
        $q.notify({
            type: 'negative',
            message: message,
            position: position,
            timeout: 1500,
        })
    }

    function warn(message, position = 'top') {
        $q.notify({
            type: 'warning',
            message: message,
            position: position,
            timeout: 1500,
        })
    }

    function info(message, position = 'top') {
        $q.notify({
            type: 'info',
            message: message,
            position: position,
            timeout: 1500,
        })
    }

    return {success, fail, warn, info}

}
