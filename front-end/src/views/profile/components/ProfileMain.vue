<template>
  <div>
    <h1>ProfileMain</h1>
    <button @click="clickConfession">Confession</button>
    <button @click="clickAdvice">Advice</button>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'

export default {
  name: 'ProfileMain',
  setup() {
    const router = useRouter()

    const clickConfession = function()  {
      router.push({
        name: 'Confession',
      })
		}
    const clickAdvice = function() {
      router.push({
        name: 'Advice'
      })
    }
    return { clickConfession, clickAdvice }
  }
}
</script>

<style>
</style>