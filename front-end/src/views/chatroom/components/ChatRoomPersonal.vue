<template>
  <div class="top">
    <span>
      <span class="name" v-if="state.chatRoomNickname != ''"
        >{{ state.chatRoomNickname }}과의 대화</span
      ></span
    >
  </div>
  <div class="chat" data-chat="person1">
    <!-- <div class="conversation-start">
      <span>Today, 6:48 AM</span>
    </div> -->
    <div v-for="(message, idx) in chatRoom.chatRoomMessage" :key="idx">
      <div v-if="message.userId == state.userInfo.id" class="bubble me pb-2">
        {{ message.message }}
      </div>
      <div v-else class="bubble you pb-2">
        {{ message.message }}
      </div>
    </div>
  </div>
  <div class="write d-flex justify-content-around">
    <div class="message_input_wrapper">
      <input
        type="text"
        id="searchInChatRoom"
        class="message_input"
        v-model="data.message"
        @keyup="sendMessage"
      />
    </div>

    <div class="d-flex">
      <button @click="clickSendMessage" class="px-3">전송</button>
      <div v-if="state.userInfo.id === state.chatRoomUserList[1]">
        <button @click="clickCreateMeeting" class="px-3">화상채팅 생성</button>
      </div>
      <div v-else>
        <div v-if="state.meetingIsActive">
          <button @click="clickEnterMeeting" class="px-3">화상채팅 참가</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { computed, onMounted, reactive } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  name: "ChatRoomPersonal",
  setup() {
    const store = useStore();
    const router = useRouter();
    onMounted(async () => {
      connect();
      window();
    });
    const window = function () {
      var form = document.getElementById("searchInChatRoom");
      form.focus();
    };

    const state = reactive({
      stompClient: null,
      socketConnected: false,
      recvList: [],
      userInfo: computed(() => store.getters["root/userInfo"]),
      // chatRoomUserList 순서 => user_id / consultant_id / topic_id of consultant
      chatRoomUserList: computed(() => store.getters["root/chatRoomUserList"]),
      adviceMeetingInfo: computed(
        () => store.getters["root/adviceMeetingInfo"]
      ),
      meetingIsActive: computed(() => store.getters["root/meetingIsActive"]),
      chatRoomNickname: computed(() => store.getters["root/chatRoomNickname"]),
    });

    const chatRoom = reactive({
      chatRoomId: computed(() => store.getters["root/chatRoomId"]),
      chatRoomMessage: computed(() => store.getters["root/chatRoomMessage"]),
    });

    const data = reactive({
      userId: state.userInfo.id,
      message: "",
    });

    const connect = async function () {
      let socket = new SockJS("https://localhost:8080/chat");
      state.stompClient = Stomp.over(socket);
      await state.stompClient.connect(
        {},
        (frame) => {
          state.socketConnected = TextTrackCue;
          state.stompClient.subscribe("/send", (res) => {
            store.dispatch("root/chatRoomGetList", {
              userId: state.userInfo.id,
            });
            // store.dispatch('root/chatRoomGetDetail', { user_id: data.userId, chatRoom_id: chatRoom.chatRoomId })
            state.recvList.push(JSON.parse(res.body));
          });
        },
        (error) => {
          state.socketConnected = false;
        }
      );
    };
    const sendMessage = function (e) {
      if (e.keyCode === 13 && data.userId !== "" && data.message !== "") {
        send();
        data.message = "";
      }
    };
    const clickSendMessage = function () {
      if (data.userId !== "" && data.message != "") {
        send();
        data.message = "";
      }
    };
    const send = async function () {
      if (state.stompClient && state.socketConnected) {
        const body = {
          chatRoomId: chatRoom.chatRoomId,
          userId: data.userId,
          message: data.message,
        };
        await state.stompClient.send("/receive", JSON.stringify(body), {});
        await store.dispatch("root/chatRoomGetList", {
          userId: state.userInfo.id,
        });
        // await store.dispatch('root/chatRoomGetDetail', { user_id: body.userId, chatRoom_id: body.chatRoomId })
      }
    };

    const clickCreateMeeting = async function () {
      const body = {
        title: "Advice",
        description: "Advice Start",
        participants: 2,
        meetingCategoryId: 2,
        topicCategoryId: state.chatRoomUserList[2],
        ownerId: state.chatRoomUserList[1],
        chatRoomId: chatRoom.chatRoomId,
      };
      await store.dispatch("root/chatRoomCreateMeeting", body);
      await router.push({
        name: "MeetingAdvice",
        params: {
          meeting_id: state.adviceMeetingInfo.meetingId,
        },
      });
    };

    const clickEnterMeeting = async function () {
      await store.dispatch("root/chatRoomEnterMeeting", chatRoom.chatRoomId);
      await router.push({
        name: "MeetingAdvice",
        params: {
          meeting_id: state.adviceMeetingInfo.meetingId,
        },
      });
    };

    return {
      data,
      window,
      state,
      chatRoom,
      onMounted,
      connect,
      sendMessage,
      clickSendMessage,
      send,
      clickCreateMeeting,
      clickEnterMeeting,
    };
  },
};
</script>

<style scoped lang="scss">
@charset "UTF-8";
*,
*:before,
*:after {
  box-sizing: border-box;
}

.container .right .top {
  width: 100%;
  height: 47px;
  padding: 15px 29px;
  background-color: #fff;
  border-top-right-radius: 30px;
}
.container .right .top span {
  font-size: 15px;
  color: #1a1a1a;
}
.container .right .top span .name {
  color: #1a1a1a;
  font-family: "Source Sans Pro", sans-serif;
  font-weight: 600;
}
.container .right .chat {
  position: relative;
  display: flex;
  overflow: hidden;
  padding: 20px 35px 20px;
  // height: calc(100% - 48px);
  // justify-content: flex-end;
  border-left: 1px solid #999999;
  flex-direction: column-reverse;
  overflow-y: auto;
  height: 82%;
  background-color: rgb(248 248 248);
}
.container .right .chat.active-chat .bubble {
  transition-timing-function: cubic-bezier(0.4, -0.04, 1, 1);
}
.container .right .chat.active-chat .bubble:nth-of-type(1) {
  -webkit-animation-duration: 0.15s;
  animation-duration: 0.15s;
}
.container .right .chat.active-chat .bubble:nth-of-type(2) {
  -webkit-animation-duration: 0.3s;
  animation-duration: 0.3s;
}
.container .right .chat.active-chat .bubble:nth-of-type(3) {
  -webkit-animation-duration: 0.45s;
  animation-duration: 0.45s;
}
.container .right .chat.active-chat .bubble:nth-of-type(4) {
  -webkit-animation-duration: 0.6s;
  animation-duration: 0.6s;
}
.container .right .chat.active-chat .bubble:nth-of-type(5) {
  -webkit-animation-duration: 0.75s;
  animation-duration: 0.75s;
}
.container .right .chat.active-chat .bubble:nth-of-type(6) {
  -webkit-animation-duration: 0.9s;
  animation-duration: 0.9s;
}
.container .right .chat.active-chat .bubble:nth-of-type(7) {
  -webkit-animation-duration: 1.05s;
  animation-duration: 1.05s;
}
.container .right .chat.active-chat .bubble:nth-of-type(8) {
  -webkit-animation-duration: 1.2s;
  animation-duration: 1.2s;
}
.container .right .chat.active-chat .bubble:nth-of-type(9) {
  -webkit-animation-duration: 1.35s;
  animation-duration: 1.35s;
}
.container .right .chat.active-chat .bubble:nth-of-type(10) {
  -webkit-animation-duration: 1.5s;
  animation-duration: 1.5s;
}
.container .right .write {
  position: absolute;
  bottom: 29px;
  left: 30px;
  height: 42px;
  padding-left: 8px;
  // border: 1px solid #e6e6e6;
  background-color: white;
  width: calc(100% - 58px);
  border-radius: 5px;
}

.container .right .write button {
  float: right;
  line-height: 220%;
  margin-left: 10px;
  margin-right: 10px;
  background-color: #809fd6;
  border: 1px solid #809fd6;
  border-radius: 10px;
}

.container .right .write .write-link.attach:before {
  display: inline-block;
  float: left;
  width: 20px;
  height: 42px;
  content: "";
  background-image: url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/382994/attachment.png");
  background-repeat: no-repeat;
  background-position: center;
}
.container .right .write .write-link.smiley:before {
  display: inline-block;
  float: left;
  width: 20px;
  height: 42px;
  content: "";
  background-image: url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/382994/smiley.png");
  background-repeat: no-repeat;
  background-position: center;
}
.container .right .write .write-link.send:before {
  display: inline-block;
  float: left;
  width: 20px;
  height: 42px;
  margin-left: 11px;
  content: "";
  background-image: url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/382994/send.png");
  background-repeat: no-repeat;
  background-position: center;
}
.container .right .bubble {
  font-size: 25px;
  position: relative;
  display: inline-block;
  clear: both;
  margin-bottom: 8px;
  padding: 13px 14px;
  vertical-align: top;
  border-radius: 5px;
}
.container .right .bubble:before {
  position: absolute;
  top: 19px;
  display: block;
  width: 8px;
  height: 6px;
  content: " ";
  transform: rotate(29deg) skew(-35deg);
}
.container .right .bubble.you {
  float: left;
  color: black;
  background-color: #dedfdf;
  align-self: flex-start;
  -webkit-animation-name: slideFromLeft;
  animation-name: slideFromLeft;
}
.container .right .bubble.you:before {
  left: -3px;
  background-color: #dedfdf;
}
.container .right .bubble.me {
  float: right;
  color: #fff;
  background-color: #809fd6;
  align-self: flex-end;
  -webkit-animation-name: slideFromRight;
  animation-name: slideFromRight;
}
.container .right .bubble.me:before {
  right: -3px;
  background-color: #809fd6;
}
.container .right .conversation-start {
  position: relative;
  width: 100%;
  margin-bottom: 27px;
  text-align: center;
}
.container .right .conversation-start span {
  font-size: 14px;
  display: inline-block;
  color: #999;
}
.container .right .conversation-start span:before,
.container .right .conversation-start span:after {
  position: absolute;
  top: 10px;
  display: inline-block;
  width: 30%;
  height: 1px;
  content: "";
  background-color: #e6e6e6;
}
.container .right .conversation-start span:before {
  left: 0;
}
.container .right .conversation-start span:after {
  right: 0;
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

::-webkit-scrollbar {
  width: 10px;
}

::-webkit-scrollbar-track {
  background-color: #fff;
  border-radius: 5px;
}

::-webkit-scrollbar-thumb {
  background: #cfcccc;
  border-radius: 5px;
}
.message_input_wrapper {
  display: inline-block;
  height: 100%;
  border-radius: 15px;
  border: 1px solid #bcbdc0;
  width: 60%;
  position: relative;
}

@keyframes blink-effect {
  50% {
    opacity: 0;
  }
}
.message_input_wrapper .message_input {
  border: none;
  height: 100%;
  box-sizing: border-box;
  border-radius: 30px;
  background: #fff;
  width: 95%;
  padding-left: 5%;
  float: left;
  outline-width: 0;
  color: rgb(168, 168, 168);
  font-family: Century Gothic, sans-serif;
  animation: blink-effect 1s step-end infinite;

  &:focus {
    animation: none;
  }
}
</style>
