<template>
  <div class="top">
    <span>
      <span class="name"></span>
    </span>
  </div>
  <ul class="people">
    <li class="person" data-chat="person1" v-for="(chatRoom, idx) in state.chatRoomList" :key=idx @click="chatRoomGetDetail(chatRoom.id, chatRoom.userNickName, chatRoom.consultantNickName)">
      <img :src="require('@/assets/mask/mask'+chatRoom.maskId+'.png')" alt="" />
      <div class="d-flex row">
        <span class="name row-5" v-if="chatRoom.consultantId == state.userInfo.id">{{ chatRoom.userNickName }}</span>
        <span class="name row-5" v-else>{{ chatRoom.consultantNickName }}</span>
        <span class="iscon row-2" v-if="chatRoom.consultantId == state.userInfo.id">상담가</span>
        <span class="iscon row-2" v-else>상담자</span>
        <span class="time row-2 ms-4" v-if="chatRoom.createdDate!=null" @click="chatRoomGetDetail(chatRoom.id, chatRoom.consultantNickName)">
          {{ chatRoom.createdDate.substr(5, 5) }}
        </span>
        <span class="time row-2 ms-4" v-else @click="chatRoomGetDetail(chatRoom.id, consultantNickName)">xx-xx</span>
      </div>
      <span class="preview" v-if="chatRoom.lastMessage!=null">{{ chatRoom.lastMessage }}</span>
      <span class="preview" v-else>메시지가 없습니다.</span>
    </li>
  </ul>
    <!-- <div v-for="(chatRoom, idx) in state.chatRoomList" :key=idx>
      <p @click="chatRoomGetDetail(chatRoom.id)">{{ chatRoom.id }}</p>
    </div> -->
  
</template>

<script>
import { useStore } from "vuex"
import { reactive, computed } from 'vue'
export default {
  name: "ChatRoomView",
  props: {
    chatRoomList: Array
  },
  setup(props) {
    const store = useStore()
    const state = reactive ({
      chatRoomList: computed(() => props.chatRoomList),
      userInfo: store.getters['root/userInfo'],
    })

    const chatRoomGetDetail = async function (chatRoom_id, userNickName, consultantNickName) {
      if (state.userInfo.nickname == userNickName) {
        store.commit('root/CHATROOM_GET_DETAIL_NICKNAME', consultantNickName)
      } else {
        store.commit('root/CHATROOM_GET_DETAIL_NICKNAME', userNickName)
      }
      await store.dispatch("root/chatRoomGetDetail",
        { user_id: state.userInfo.id, chatRoom_id: chatRoom_id }
      )
    }

    return { state, chatRoomGetDetail }
  }
}
</script>

<style scoped lang="scss">
@charset "UTF-8";
*, *:before, *:after {
  box-sizing: border-box;
}

.container .left .top {
  width: 100%;
  height: 47px;
  padding: 15px 29px;
  background-color: #fff;
  border-top-left-radius: 30px;
  border-bottom: 1px solid white;
}
.container .left .people {
  overflow-y: auto;
  background: #f8f8f8;
  margin-left: -1px;
  padding-left: 0px;
  width: 100%;
  height: 82%;
}
.container .left .people .person {
  position: relative;
  width: 100%;
  padding: 16px 5% 16px 5%;
  cursor: pointer;
  background-color: #dedfdf;
  border-bottom: 1px solid #9c9d9f;
}
// .container .left .people .person:after {
//   position: absolute;
//   bottom: 0;
//   left: 50%;
//   display: block;
//   width: 80%;
//   height: 1px;
//   content: "";
//   background-color: #e6e6e6;
//   transform: translate(-50%, 0);
// }
.container .left .people .person img {
  float: left;
  width: 40px;
  height: 40px;
  margin-left: 15px;
  margin-right: 12px;
  margin-top: 5px;
  border-radius: 50%;
  background: #fff;
  -o-object-fit: cover;
     object-fit: cover;
}
.container .left .people .person .name {
  position: relative;
  font-size: 14px;
  line-height: 22px;
  display: inline-block;
  color: #1a1a1a;
  font-family: "Source Sans Pro", sans-serif;
  font-weight: 600;
  width: 30%;
  padding-left: 5px;
}
.container .left .people .person .iscon {
  position: relative;
  font-size: 14px;
  line-height: 22px;
  display: inline-block;
  color: #6A515E;
  font-family: "Source Sans Pro", sans-serif;
  font-weight: 600;
  width: 30%;
  padding-left: 5px;
}
.container .left .people .person .time {
  font-size: 14px;
  position: relative;
  // top: 16px;
  width: 30%;
  padding: 0 0 5px 5px;
  color: #999;
  // background-color: #fff;
}
.container .left .people .person .preview {
  font-size: 14px;
  display: inline-block;
  overflow: hidden !important;
  width: 70%;
  padding-top: 1%;
  white-space: nowrap;
  text-overflow: ellipsis;
  text-align: left;
  color: #999;
}

// .container .left .people .person.active, .container .left .people .person:hover {
//   margin-top: -1px;
//   margin-left: -1px;
//   padding-top: 13px;
//   border: 0;
//   background-color: var(--blue);
//   width: calc(100% + 2px);
//   padding-left: calc(10% + 1px);
// }
.container .left .people .person.active span, .container .left .people .person:hover span {
  color: var(--white);
  background: transparent;
}
.container .left .people .person.active:after, .container .left .people .person:hover:after {
  display: none;
}

@keyframes slideFromLeft {
  0% {
    margin-left: -200px;
    opacity: 0;
  }
  100% {
    margin-left: 0;
    opacity: 1;
  }
}
@-webkit-keyframes slideFromLeft {
  0% {
    margin-left: -200px;
    opacity: 0;
  }
  100% {
    margin-left: 0;
    opacity: 1;
  }
}
@keyframes slideFromRight {
  0% {
    margin-right: -200px;
    opacity: 0;
  }
  100% {
    margin-right: 0;
    opacity: 1;
  }
}
@-webkit-keyframes slideFromRight {
  0% {
    margin-right: -200px;
    opacity: 0;
  }
  100% {
    margin-right: 0;
    opacity: 1;
  }
}

::-webkit-scrollbar { width: 10px; }

::-webkit-scrollbar-track { 
  background-color: white; 
  border-radius: 5px; 
}

::-webkit-scrollbar-thumb { 
    background: #e6e6e6;
    border-radius: 5px;
}
</style>
