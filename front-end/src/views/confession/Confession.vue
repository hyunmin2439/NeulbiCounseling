<template>
  <main-header></main-header>
  <confession-view :userInfo="state.userInfo"></confession-view>
  <!-- 페이지네이션 -->
  <!-- <advice-pagination></advice-pagination> -->
    
</template>

<script>
import ConfessionView from './components/ConfessionView.vue'
import MainHeader from "../main/components/MainHeader.vue";
import { onMounted, reactive, computed } from 'vue'
import { useStore } from 'vuex'

export default {
  name: "Confession",
  components: {
    MainHeader,
    ConfessionView,
  },
  setup() {
    const store = useStore()
    const state = reactive({
      userInfo: computed(() => store.getters['root/userInfo'])
    })

    onMounted(async() => {
      window.scrollTo(0, 0);
      await store.dispatch('root/confessionGetMeetingList')
    })
    return { state, onMounted }
  }
}
</script>

<style>

</style>
