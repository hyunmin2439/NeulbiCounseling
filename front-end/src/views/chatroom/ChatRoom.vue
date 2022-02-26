<template style="background: #c2d6f8;">
  <main-header style="transform: translate(-1%, 0);"></main-header>
  <h1 style="background: rgb(225 236 255); margin-bottom: 0px; padding-top: 2%;">{{ state.userInfo.nickname }}의 채팅방</h1>
  <div class="wrapper">
    <div class="container">
      <div class="left">
        <chat-room-view :chatRoomList="state.chatRoomList"></chat-room-view>
      </div>
      <div class="right">
        <chat-room-personal></chat-room-personal>
      </div>
    </div>
  </div>
</template>

<script>
import MainHeader from "../main/components/MainHeader.vue";
import ChatRoomView from "./components/ChatRoomView.vue"
import ChatRoomPersonal from "./components/ChatRoomPersonal.vue"
import { useStore } from "vuex";
import { reactive, computed, onMounted } from 'vue';

export default {
  name: "ChatRoom",
  components: {
    MainHeader,
    ChatRoomView,
    ChatRoomPersonal,
  },
  setup() {
    const store = useStore()

    const state = reactive({
      userInfo: computed(() => store.getters['root/userInfo']),
      chatRoomList: computed(() => store.getters["root/chatRoomList"]),
    })

    onMounted(async() => {
      window.scrollTo(0, 0);
      await store.dispatch('root/chatRoomGetList', { userId: state.userInfo.id })
      if (state.userInfo.nickname == state.chatRoomList[0].userNickName) {
        store.commit('root/CHATROOM_GET_DETAIL_NICKNAME', state.chatRoomList[0].consultantNickName)
      } else {
        store.commit('root/CHATROOM_GET_DETAIL_NICKNAME', state.chatRoomList[0].userNickName)
      }
    })

    return { state, onMounted }
  }
};
</script>

<style scoped lang="scss">
@charset "UTF-8";
*, *:before, *:after {
  box-sizing: border-box;
}

.wrapper {
  position: absolute;
  // left: 50%;
  width: 100%;
  height: 90vh;
  // transform: translate(-50%, 0);
  background: rgb(225 236 255);
}

.container {
  position: relative;
  margin-top: 300pt;
  margin-left: 50%;
  width: 65%;
  height: 85%;
  background-color: #fff;
  border-radius: 30px;
  transform: translate(-50%, -50%);
  padding-left: 0px;
  padding-right: 0px;
}
.container .left {
  float: left;
  width: 37.6%;
  background: #fff;
  border-top-left-radius: 30px;
  border-bottom-left-radius: 30px;
  height: 100%;
}

.container .right {
  position: relative;
  background: #fff;
  border-left: 1px solid #eceff1;
  border-top-right-radius: 30px;
  border-bottom-right-radius: 30px;
  float: left;
  width: 62.4%;
  height: 100%;
}

</style>
