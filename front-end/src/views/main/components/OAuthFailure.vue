<template>
<div></div>
</template>

<script>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'OAuthSuccess',
  setup() {
    const router = useRouter()

    onMounted(async() => {
      alert('소셜 로그인에 실패했습니다!')
      await router.push({ name: "Home"} )
    })

    return { onMounted }
  }
}
</script>
