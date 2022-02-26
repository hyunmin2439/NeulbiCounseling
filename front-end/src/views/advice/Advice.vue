<template>
  <main-header></main-header>
  <advice-view :userInfo="state.userInfo"></advice-view>
  
</template>

<script>
import AdviceCreate from "./components/AdviceCreate.vue";
// import AdvicePagination from "./components/AdvicePagination.vue";
import AdviceView from "./components/AdviceView.vue";
import MainHeader from "../main/components/MainHeader.vue";

import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: "Advice",
  components: {
    MainHeader,
    // AdvicePagination,
    AdviceView,
  },
  setup () {
    const store = useStore()
    const state = reactive({
      userInfo: computed(() => store.getters['root/userInfo']),
    })

    onMounted(async() => {
      window.scrollTo(0, 0);
      await store.dispatch("root/adviceGetConsultantList", state.userInfo.id)
      await store.dispatch("root/adviceGetRankList")
    })
    return { state, onMounted }
  }
};
</script>

<style scoped></style>
