<template>
<div></div>
</template>

<script>
import { onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'

export default {
  name: 'OAuthSuccess',
  setup() {
    const store = useStore()
    const route = useRoute()
    const router = useRouter()

    onMounted(async() => {
      const token = route.query.accessToken
      localStorage.setItem("jwt", token)
      if (localStorage.getItem("jwt")) {
        store.commit("root/MAIN_IS_SIGNIN", true)
        await store.dispatch('root/userGetInfo', token)
        await router.push({ name: "Home"} )
      } else {
        alert('로그인 실패')
      }
    })

    return { onMounted }
  }
}
</script>
