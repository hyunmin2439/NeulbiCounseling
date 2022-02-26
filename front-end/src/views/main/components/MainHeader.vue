<template>
  <div id="nav">
    <span v-if="state.isSignIn" class="row">
      <div class="col-1" style="text-align: left; display: flex; flex-direction: column; justify-content: center;">
        <img src="@/assets/icon.png" style="position: absolute; width: 4%; margin-left: 2%; margin-bottom: 1%; cursor: pointer;" @click="goToHome">
      </div>
      <span class="col-3 d-flex justify-content-around">
        <router-link :to="{ name: 'Confession' }" style="font-size: 30px;">고해성사</router-link>
        <router-link :to="{ name: 'Advice' }" style="font-size: 30px;">고민상담</router-link>
        <router-link :to="{ name: 'History', params: {user_id: state.userInfo.id} }" style="font-size: 30px;">발자취</router-link>
      </span>
      <span class="col-3">
      </span>
      <span class="col-2 d-flex justify-content-evenly ms-5">
        <router-link :to="{ name: 'Community' }" style="font-size: 30px;">게시판</router-link>
        <router-link :to="{ name: 'Notice' }" style="font-size: 25px;">공지사항</router-link>
        <router-link :to="{ name: 'Qna' }" style="font-size: 25px;">QnA</router-link>
      </span>
      <span class="col-2 d-flex justify-content-evenly ms-5">
        <p @click="clickLogout" style="font-size: 30px;">로그아웃</p>
        <p @click="goToProfile" style="font-size: 30px;">프로필</p>
      </span>
    </span>
    
    <span v-else class="row">
      <span class="col-9"></span>
      <span class="col-2 d-flex justify-content-evenly">
        <router-link :to="{ name: 'SignUp' }" style="font-size: 25px;">SignUp</router-link>
        <router-link :to="{ name: 'SignIn' }" style="font-size: 25px;">SignIn</router-link>
      </span>
    </span>


    

  </div>
</template>

<script>
import { reactive, computed } from "vue"
import { useStore } from "vuex"
import { useRouter } from 'vue-router'

export default {
  name: "MainHeader",
  setup() {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      isSignIn: computed(() => store.getters["root/mainIsSignIn"]),
      userInfo: computed(() => store.getters["root/userInfo"])
    })

    const clickLogout = function () {
      store.commit("root/MAIN_IS_SIGNIN", false)
      store.commit("root/GET_USER_INFO", null)
      store.commit("root/MAIN_EMAIL_BOOL", false)
      store.commit("root/MAIN_NICKNAME_BOOL", false)
      store.commit("root/PROFILE_GET_NICKNAME", false)
      store.commit("root/PROFILE_GET_CONSULTANT_PROFILE", null)
      localStorage.removeItem('vuex')
      localStorage.removeItem("jwt")
      router.push({ name: 'Home' })
    }

    const goToHome = async function () {
      await router.push({
        name: 'Home'
      })
    }

    const goToChatRoom = async function () {
      await store.dispatch("root/chatRoomGetList", { userId: state.userInfo.id })   
      await router.push({
        name: 'ChatRoom',
        params: {
          user_id: state.userInfo.id
        }
      })
    }

    const goToProfile = function () {
      router.push({
        name: 'Profile',
        params: {
          user_id: state.userInfo.id
        }
      })
    }

    return { state, clickLogout, goToHome, goToChatRoom, goToProfile }
  }
}
</script>

<style scoped>
#nav {
  position: fixed-top;
  background-color: #c2d6f8;
  width: 101%;
}

a, p {
  text-decoration: none;
  color: white;
  font-weight: bold;
  line-height: 100px;
  margin-bottom: 0px;
}

a:hover, p:hover {
  text-decoration: underline;
  cursor: pointer;
}
</style>
