<script setup>
import {ref} from 'vue'
import {useMainStore} from "@/store";

const store = useMainStore()
const slide = ref(1)

if (store.user !== null) {
  store.getProfile()
}

const products = ['Google', 'Prime', 'Netflix', 'AppleID', 'max', 'Disney', 'ChatGPT']

</script>

<template>
  <q-page>
    <q-carousel
        animated
        infinite
        swipeable
        arrows
        navigation
        autoplay
        height="200px"
        v-model="slide"
    >
      <q-carousel-slide :name="1" img-src="https://cdn.quasar.dev/img/mountains.jpg"/>
      <q-carousel-slide :name="2" img-src="https://cdn.quasar.dev/img/parallax1.jpg"/>
      <q-carousel-slide :name="3" img-src="https://cdn.quasar.dev/img/parallax2.jpg"/>
    </q-carousel>

    <div class="row wrap flex-center" style="padding: 20px 70px">
      <q-card
          style="margin: 20px; width: 300px"
          v-for="product in products"
          v-ripple
          class="cursor-pointer q-hoverable"
      >
        <span class="q-focus-helper"></span>
        <img src="https://cdn.netflixtown.com/264x264_2.png" alt="img">
        <q-card-section>
          <div>{{ product }}</div>
          <div>已售 2345</div>
        </q-card-section>
        <q-card-section>
          Google账号
        </q-card-section>
        <q-card-section>
          ¥ 12.8
        </q-card-section>
      </q-card>
    </div>

  </q-page>
</template>

<style scoped>

</style>
