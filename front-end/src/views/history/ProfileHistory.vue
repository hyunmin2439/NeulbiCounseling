<template>
  <div id="history">
    <main-header></main-header>
    <div class="container">
      <profile-history-confession></profile-history-confession>
      <profile-history-advice></profile-history-advice>
      <profile-history-community></profile-history-community>
      <profile-history-comment></profile-history-comment>
      <profile-history-review :userInfo="state.userInfo"></profile-history-review>
    </div>
  </div>
</template>

<script>
import MainHeader from "../main/components/MainHeader.vue";
import ProfileHistoryConfession from './ProfileHistoryConfession.vue'
import ProfileHistoryAdvice from './ProfileHistoryAdvice.vue'
import ProfileHistoryReview from './ProfileHistoryReview.vue'
import ProfileHistoryCommunity from './ProfileHistoryCommunity.vue'
import ProfileHistoryComment from './ProfileHistoryComment.vue'

import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'ProfileHistory',
  components: {
    MainHeader,
    ProfileHistoryConfession,
    ProfileHistoryAdvice,
    ProfileHistoryReview,
    ProfileHistoryCommunity,
    ProfileHistoryComment
  },
  setup () {
    const store = useStore()
    const state = reactive({
      userInfo: computed(() => store.getters['root/userInfo'])
    })

    onMounted(async() => {
      window.scrollTo(0, 0);
      const body = { user_id: state.userInfo.id }
      await store.dispatch('root/profileGetHistoryConfessionMeeting', body)
      await store.dispatch('root/profileGetHistoryAdviceMeeting', body)
      await store.dispatch('root/profileGetHistoryReceivedReview', body)
      await store.dispatch('root/profileGetHistoryWrittenReview', body)
      await store.dispatch('root/profileGetHistoryCommunity', body)
      await store.dispatch('root/profileGetHistoryComment', body)
    })

    return { state, onMounted }
  }
}
</script>

<style>
#history {
  font-family: "Binggrae"!important;
}
</style>