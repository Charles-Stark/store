<script setup>
import {useMainStore} from "@/store";
import {outlinedPerson} from "@quasar/extras/material-icons-outlined";
import {ref} from "vue";
import {updateUsername} from "@/request/user";
import {useNotify} from "@/utils/notify";

const store = useMainStore()
const notify = useNotify()

const inputUsername = ref(false)
const username = ref('')
const usernameBlur = () => {
  updateUsername(username.value)
      .then((res) => {
        if (res.data.code === 0) {
          notify.success(res.data['msg'])
          store.getProfile()
        } else {
          notify.fail(res.data['msg'])
        }
      })
  inputUsername.value = !inputUsername.value;
  username.value = ''
}

store.getProfile()

</script>

<template>
  <q-page class="column">
    <q-card style="width: 600px"
            class="q-mt-xl q-pa-xl self-center">
      <q-list>

        <q-item class="q-mb-md">
          <q-item-section>
            <q-avatar v-if="store.user['avatarUrl'] !== null" size="100px" class="self-center cursor-pointer" v-ripple
                      @click="this.$refs.uploader.pickFiles()"
            >
              <img :src="store.user['avatarUrl']" alt="profile"/>
            </q-avatar>
            <q-avatar v-if="store.user['avatarUrl'] === null" size="100px" class="self-center cursor-pointer" v-ripple
                      :icon="outlinedPerson" color="grey" text-color="grey-9" font-size="60px"
                      @click="this.$refs.uploader.pickFiles()"
            />
            <q-uploader v-show="false"
                        ref="uploader"
                        accept="image/*"
                        max-file-size="1048576"
                        max-files="1"
                        @rejected="notify.fail('文件格式错误或超过1MB')"
                        with-credentials
                        auto-upload
                        url="http://localhost:8080/api/user/avatar"
                        field-name="avatar"
                        method="POST"
                        @finish="this.$refs.uploader.reset(); store.getProfile()"
                        @failed="notify.fail('上传失败')"
            />
          </q-item-section>
        </q-item>

        <q-item class="text-center text-subtitle1">
          <q-item-section>用户名</q-item-section>
          <q-item-section>
            <q-btn flat fab no-caps :ripple="false" v-if="!inputUsername" @click="inputUsername = !inputUsername"
                   :label="store.user.username"/>
            <q-input
                type="text"
                autofocus
                v-if="inputUsername"
                v-model="username"
                @blur="usernameBlur"
            ></q-input>
          </q-item-section>
        </q-item>
        <q-separator/>

        <q-item class="text-center text-subtitle1">
          <q-item-section>邮箱</q-item-section>
          <q-item-section>
            <q-btn flat fab no-caps :ripple="false" @click=""
                   :label="store.user['email'] === null ? '点击添加' : store.user['email']"/>
          </q-item-section>
        </q-item>
        <q-separator/>

        <q-item class="text-center text-subtitle1">
          <q-item-section>手机号</q-item-section>
          <q-item-section>
            <q-btn flat fab no-caps :ripple="false" @click=""
                   :label="store.user['phoneNumber'] === null ? '点击添加' : store.user['phoneNumber']"/>
          </q-item-section>
        </q-item>
        <q-separator/>

        <q-item class="text-center text-subtitle1">
          <q-item-section>加入时间</q-item-section>
          <q-item-section>
            <q-btn flat fab no-caps :ripple="false"
                   :label="store.user['createdAt']"/>
          </q-item-section>
        </q-item>

      </q-list>
    </q-card>

  </q-page>
</template>

<style scoped>

</style>
