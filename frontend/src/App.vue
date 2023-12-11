<template>
  <q-layout view="hHh lpr fff">
    <q-header reveal elevated>
      <q-toolbar>
        <q-avatar square text-color="red" color="primary" size="60px" style="font-weight: bold">
          NT
        </q-avatar>
        <q-toolbar-title>
          Netflix
        </q-toolbar-title>

        <q-btn flat no-caps no-wrap :ripple="false" label="首页" to="/"/>
        <q-btn flat no-caps no-wrap :ripple="false" label="车票" to="/ticket"/>
        <q-btn flat no-caps no-wrap :ripple="false" label="邀请" to="/invitation"/>
        <q-btn flat no-caps no-wrap :ripple="false" label="工单" to="/service"/>
        <q-btn flat no-caps no-wrap :ripple="false" label="帮助" to="/help"/>
        <q-btn flat no-caps no-wrap :ripple="false" label="登录" to="/login" v-if="!store.user"/>
        <q-btn flat no-caps no-wrap round v-if="store.user">
          <q-avatar v-if="store.user['avatarUrl'] !== null">
            <img :src="store.user['avatarUrl']" alt="profile"/>
          </q-avatar>
          <q-avatar v-if="store.user['avatarUrl'] === null" color="red">
            {{ store.user.username.slice(0, 2).toUpperCase() }}
          </q-avatar>
          <q-menu auto-close>
            <q-list style="max-width: 180px" class="text-center">
              <q-item>
                <q-item-section class="text-subtitle1" style="overflow: hidden">{{
                    store.user.username
                  }}
                </q-item-section>
              </q-item>
              <q-separator/>
              <q-item clickable to="/user">
                <q-item-section avatar>
                  <q-icon :name="outlinedPerson" right/>
                </q-item-section>
                <q-item-section class="q-mr-md text-weight-medium">个人资料</q-item-section>
              </q-item>
              <q-item clickable to="">
                <q-item-section avatar>
                  <q-icon :name="outlinedReceiptLong" right/>
                </q-item-section>
                <q-item-section class="q-mr-md text-weight-medium">订单详情</q-item-section>
              </q-item>
              <q-item clickable to="">
                <q-item-section avatar>
                  <q-icon :name="outlinedWallet" right/>
                </q-item-section>
                <q-item-section class="q-mr-md text-weight-medium">账户充值</q-item-section>
              </q-item>
              <q-separator/>
              <q-item clickable @click="store.logout()">
                <q-item-section class="text-weight-medium">退出登录</q-item-section>
              </q-item>
            </q-list>
          </q-menu>
        </q-btn>
      </q-toolbar>
    </q-header>

    <q-footer>
      <q-toolbar class="flex-center">
        Copyright © 2023
      </q-toolbar>
    </q-footer>

    <q-page-container>
      <router-view></router-view>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import {useMainStore} from "@/store";
import {outlinedPerson, outlinedReceiptLong, outlinedWallet} from "@quasar/extras/material-icons-outlined";

const store = useMainStore()

if (store.user !== null) {
  store.getProfile()
}

</script>

<style scoped>
</style>
