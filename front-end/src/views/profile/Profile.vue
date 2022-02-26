<template>
  <div>
    <main-header></main-header>
    <div class="row container" style="margin-top: 3%; margin-left: 10%;">
      <!-- Content -->
      <profile-user :userInfo="state.userInfo" class="container-fluid"></profile-user>
      <profile-consultant
          :userInfo="state.userInfo"
          class="container-fluid"
        >
      </profile-consultant>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'
import MainHeader from "../main/components/MainHeader.vue";
import ProfileUser from './components/ProfileUser.vue'
import ProfileConsultant from './components/ProfileConsultant.vue'

export default {
  name: 'Profile',
  components: {
    MainHeader,
    ProfileUser,
    ProfileConsultant,
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const state = reactive({
      userInfo: computed(() => store.getters['root/userInfo']),
      select: 'User',
    })

    const selectProfile = async function (event) {
      state.select = event.target.innerText
      const body = { user_id: state.userInfo.id }
      if (state.userInfo.consultant) {
        await store.dispatch("root/profileGetConsultantProfile", state.userInfo.id)
      } else {
        store.commit("root/PROFILE_GET_CONSULTANT_PROFILE", "No")
      }
      await store.dispatch('root/profileGetConsultantLike', state.userInfo.id)
    }

    onMounted(async() => {
      window.scrollTo(0, 0);
      if(route.query.value==='AdviceUser') {
        state.select = 'Consultant'
      } 

      const body = { user_id: state.userInfo.id }
      if (state.userInfo.consultant) {
        await store.dispatch("root/profileGetConsultantProfile", state.userInfo.id)
      } else {
        store.commit("root/PROFILE_GET_CONSULTANT_PROFILE", "No")
      }
      await store.dispatch('root/profileGetConsultantLike', state.userInfo.id)
    })

    const goToConfession = function () {
      router.push({
        name: 'Confession'
      })
    }

    return { state, selectProfile, onMounted, goToConfession }
  }
}
</script>

<style>
</style>