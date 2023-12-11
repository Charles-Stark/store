<script setup>
import {ref, watch} from "vue";
import {outlinedVisibility, outlinedVisibilityOff} from "@quasar/extras/material-icons-outlined";
import {useMainStore} from "@/store";
import router from "@/router/routes";

const store = useMainStore()

const tab = ref('register')

const isPwd = ref(true)
const username = ref('')
const useEmail = ref(true)
const emailOrPhone = ref('')
const password = ref('')
const code = ref('')

const wait = ref(false)
const secondsLeft = ref(121)
let timer

function waitForCode() {
  wait.value = true
  secondsLeft.value--
  if (secondsLeft.value === 0) {
    secondsLeft.value = 121
    clearTimeout(timer)
    wait.value = false
    return
  }
  timer = setTimeout(waitForCode, 1000)
}

if (store.user !== null) {
  router.push('/user')
}

watch(useEmail, (newValue, oldValue) => {
  emailOrPhone.value = ''
})

</script>

<template>
  <q-page class="column">

    <q-card
        style="width: 450px"
        class="q-mt-xl self-center">
      <q-tabs
          v-model="tab"
          align="justify"
          active-color="primary"
          indicator-color="primary"
      >
        <q-tab label="注册" :ripple="false" name="register"/>
        <q-tab label="登录" :ripple="false" name="login"/>
      </q-tabs>

      <q-tab-panels
          v-model="tab"
          animated>
        <q-tab-panel
            class="q-pa-none"
            name="register">
          <q-form
              greedy
              class="q-pa-lg q-gutter-y-sm column"
              style="max-width: 450px;"
              @submit="store.register(useEmail, emailOrPhone, username, password, code)"
          >
            <q-input
                outlined
                type="text"
                lazy-rules
                :rules="[val => val.length <= 20 || '最多20个字符', val => !!val || '用户名不能为空']"
                label="用户名"
                name="username"
                v-model="username"></q-input>
            <q-input
                outlined
                type="email"
                lazy-rules
                :rules="[val => val.length <= 250 || '最多250个字符', (val, rules) => rules.email(val) || '不是有效的邮箱地址']"
                label="邮箱"
                name="email"
                v-model="emailOrPhone"
                v-if="useEmail"></q-input>
            <q-input
                outlined
                type="tel"
                lazy-rules
                :rules="[val => val.length === 11 || '不是有效的手机号']"
                mask="###########"
                label="手机号"
                name="phone"
                v-model="emailOrPhone"
                v-if="!useEmail"></q-input>
            <q-input
                outlined
                :type="isPwd ? 'password' : 'text'"
                lazy-rules
                :rules="[val => val.length <= 30 || '最多30个字符', val => val.length >= 6 || '密码不能少于6位']"
                label="密码"
                name="password"
                v-model="password">
              <template v-slot:append>
                <q-icon
                    :name="isPwd ? outlinedVisibilityOff : outlinedVisibility"
                    class="cursor-pointer"
                    @click="isPwd = !isPwd"
                />
              </template>
            </q-input>
            <q-input
                outlined
                type="text"
                lazy-rules
                mask="######"
                :rules="[val => val.length === 6 || '验证码为6位数字']"
                label="验证码"
                name="code"
                v-model="code">
              <template v-slot:append>
                <q-btn label="获取" color="primary" :loading="wait" :disable="wait" @click="waitForCode">
                  <template v-slot:loading>
                    {{ secondsLeft }}
                  </template>
                </q-btn>
              </template>
            </q-input>
            <q-btn-toggle
                unelevated
                size="sm"
                class="q-mb-md self-end"
                :ripple="false"
                name="useEmail"
                v-model="useEmail"
                :options="[
              {label: '邮箱', value: true},
              {label: '手机号', value: false},
          ]"/>
            <q-btn label="注册" type="submit" color="primary" size="lg"/>
          </q-form>
        </q-tab-panel>
        <q-tab-panel
            class="q-pa-none"
            name="login">
          <q-form
              greedy
              class="q-pa-lg q-gutter-y-sm column"
              style="max-width: 450px;"
              @submit="store.login(emailOrPhone, password)"
          >
            <q-input
                outlined
                type="text"
                lazy-rules
                :rules="[val => !!val || '邮箱或手机号不能为空']"
                label="邮箱或手机号"
                name="emailOrPhone"
                v-model="emailOrPhone"></q-input>
            <q-input
                outlined
                :type="isPwd ? 'password' : 'text'"
                lazy-rules
                :rules="[val => val.length <= 30 || '最多30个字符', val => val.length >= 6 || '密码不能少于6位']"
                label="密码"
                name="password"
                v-model="password">
              <template v-slot:append>
                <q-icon
                    :name="isPwd ? outlinedVisibilityOff : outlinedVisibility"
                    class="cursor-pointer"
                    @click="isPwd = !isPwd"
                />
              </template>
            </q-input>
            <q-btn label="登录" type="submit" color="primary" size="lg"/>
          </q-form>
        </q-tab-panel>
      </q-tab-panels>
    </q-card>

  </q-page>
</template>

<style scoped>

</style>
