<template>
  <main-header></main-header>
  <div>
    <!-- <button @click="goToCreateCommunity">게시글 생성</button> -->
    <community-view :userInfo="state.userInfo"></community-view>
  </div>
</template>

<script>
import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import MainHeader from "../main/components/MainHeader.vue";
import CommunityView from './components/CommunityView.vue';

export default {
    name: "Confession",
    components: { 
      CommunityView,
      MainHeader
    },

    setup() {
      const store = useStore();
      const router = useRouter()
      const state = reactive({
        userInfo: computed(()=> store.getters['root/userInfo']),
      })

      onMounted(() => {
        window.scrollTo(0, 0);
        store.dispatch("root/communityGetCommunityList")
      })

      // const goToCreateCommunity = function () {
      //   router.push({ name : "CommunityCreate" })
      // }

      return { state, onMounted }
    }

}
</script>

<style></style>