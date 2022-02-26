<template>
  <div class="chat_window">
    <div class="top_menu">
      <div class="title">Chat</div>
    </div>
    <ul class="messages" id="mymsg">
      <li
        class="message"
        v-for="(msg, index) in state.messages"
        :key="index"
        :id="msg.from == state.userInfo.nickname ? 'right' : 'left'"
      >
        <div class="text_wrapper">
          <div class="text">
            {{ msg.text }}
          </div>
        </div>
      </li>
    </ul>
    <div class="bottom_wrapper clearfix">
      <div class="message_input_wrapper">
        <input
          class="message_input"
          v-model="state.message"
          placeholder="메시지를 적어주세요."
          @keyup="entermessage"
        />
      </div>
      <div class="send_message" @click="sendMessage">전송</div>
    </div>
  </div>
</template>

<script>
import { computed, reactive } from "@vue/reactivity";
import { useStore } from "vuex";

export default {
  name: "MeetingChatRoom",
  props: {
    session: Object,
  },
  setup(props) {
    const store = useStore();
    const state = reactive({
      message: "",
      messages: [],
      userInfo: computed(() => store.getters["root/userInfo"]),
    });

    props.session.on("signal:chat", async (event) => {
      const objMessage = document.getElementById("mymsg");
      console.log(event.data);
      console.log(JSON.parse(event.from.data.split("%/%")[0]).clientData);
      console.log(event.type);
      await state.messages.push({
        text: event.data,
        from: JSON.parse(event.from.data.split("%/%")[0]).clientData,
      });

      objMessage.scrollTop = objMessage.scrollHeight;
    });

    const entermessage = function (e) {
      if (e.keyCode==13 & state.message!= null) {
        sendMessage()
      }
    }

    const sendMessage = function () {
      if (state.message.trim() == "") return;
      props.session
        .signal({
          data: state.message,
          to: [],
          type: "chat",
        })
        .then(() => {
          console.log("Message successfully sent");
          state.message = "";
        })
        .catch((error) => {
          console.error(error);
        });
    };
    return { state, sendMessage, entermessage };
  },
};
</script>

<style scoped lang="scss">
* {
  box-sizing: border-box;
}

body {
  background-color: #edeff2;
  font-family: "Calibri", "Roboto", sans-serif;
}

#right {
  float: right;
}

#right .text_wrapper {
  background-color: #809fd6;
}

#right .text {
  text-align: right;
  color: white;
}

#left {
  float: left;
}

#left .text_wrapper {
  background-color: #cbcfd4;
}

#left .text {
  text-align: left;
  color: black;
}

ul {
  list-style: none;
}
ul li {
  display: inline;
  margin-left: 10px;
}

.chat_window {
  /* position: absolute; */
  width: calc(100% - 10px);
  max-width: 800px;
  height: 600px;
  border-radius: 10px;
  background-color: #fff;
  left: 50%;
  top: 50%;
  transform: translateX(-1%) translateY(-1%);
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.15);
  background-color: #f8f8f8;
  overflow: hidden;
}

.top_menu {
  background-color: #fff;
  width: 100%;
  padding: 10px 0 6px;
  box-shadow: 0 1px 30px rgba(0, 0, 0, 0.1);
}

.top_menu .title {
  text-align: center;
  color: #bcbdc0;
  font-size: 15px;
  font-family: Century Gothic, sans-serif;
}

.messages {
  position: relative;
  list-style: none;
  padding: 20px 10px 0 10px;
  padding-top: 10px;
  margin: 0;
  height: 503px;
  overflow-y: scroll;
}
.messages .message {
  clear: both;
  overflow: hidden;
  margin-bottom: 5px;
  transition: all 0.5s linear;
  opacity: 1;
  font-family: Century Gothic, sans-serif;
}

.messages .message .text_wrapper {
  margin-bottom: 1px;
}
/* .messages .message .text_wrapper::after,
.messages .message .text_wrapper::before {
  right: 100%;
  border-right-color: #ffe6cb;
} */
.messages .message .text {
  /* margin-left: 10px; */

  font-size: 1px;
}
.messages .message .from {
  margin-right: 5px;
  text-align: right;
}

.messages .message .text_wrapper {
  display: inline-block;
  padding: 10px;
  border-radius: 15px;
  width: calc(100% - 0px);
  min-width: 60px;
  position: relative;
}

.messages .message .text_wrapper .text {
  font-size: 15px;
  font-weight: 100;
}

.bottom_wrapper {
  position: relative;
  width: 100%;
  background-color: #fff;
  padding: 10px 10px;
  position: absolute;
  bottom: 0;
}
.bottom_wrapper .message_input_wrapper {
  display: inline-block;
  height: 30px;
  border-radius: 15px;
  border: 1px solid #bcbdc0;
  width: calc(100% - 70px);
  position: relative;
  padding: 0 20px;
}
.bottom_wrapper .message_input_wrapper .message_input {
  border: none;
  height: 100%;
  box-sizing: border-box;
  width: 100%;
  float: left;
  outline-width: 0;
  color: rgb(168, 168, 168);
  font-family: Century Gothic, sans-serif;
}
.bottom_wrapper .send_message {
  width: 50px;
  height: 30px;
  display: inline;
  border-radius: 50px;
  background-color: #809fd6;
  border: 2px solid #809fd6;
  color: #fff;
  cursor: pointer;
  transition: all 0.2s linear;
  text-align: center;
  float: right;
}
.bottom_wrapper .send_message:hover {
  color: #809fd6;
  background-color: #fff;
}
.bottom_wrapper .send_message i {
  font-size: 15px;
  display: inline-block;
}
</style>
