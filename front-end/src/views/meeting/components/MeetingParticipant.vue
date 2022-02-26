<template>
  <div class="participant_window">
    <div class="top_menu">
      <div class="title">
        참가자 (<span>{{ state.subscribers.length + 1 }}</span
        >)
      </div>
    </div>
    <div id="text_wrapper">
      <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-7">
          {{
            JSON.parse(state.publisher.stream.connection.data.split("%/%")[0])
              .clientData
          }}
        </div>
        <div class="col-md-3">
          <i
            v-if="state.publisher.stream.videoActive"
            class="fas fa-video"
            style="color: green; font-size: 15px"
          ></i>
          <i
            v-else
            class="fas fa-video-slash"
            style="color: red; font-size: 15px"
          ></i>
          &nbsp;
          <i
            v-if="state.publisher.stream.audioActive"
            class="fas fa-microphone"
            style="color: green; font-size: 15px"
          ></i>
          <i
            v-else
            class="fas fa-microphone-slash"
            style="color: red; font-size: 15px"
          ></i>
        </div>
        <div class="col-md-1"></div>
      </div>
    </div>

    <div
      id="text_wrapper"
      v-for="(sub, index) in state.subscribers"
      :key="index"
    >
      <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-7">
          {{
            JSON.parse(sub.stream.connection.data.split("%/%")[0]).clientData
          }}
        </div>
        <div class="col-md-3">
          <i
            v-if="sub.stream.videoActive"
            class="fas fa-video"
            style="color: green; font-size: 15px"
          ></i>
          <i
            v-else
            class="fas fa-video-slash"
            style="color: red; font-size: 15px"
          ></i>
          &nbsp;
          <i
            v-if="sub.stream.audioActive"
            class="fas fa-microphone"
            style="color: green; font-size: 15px"
          ></i>
          <i
            v-else
            class="fas fa-microphone-slash"
            style="color: red; font-size: 15px"
          ></i>
        </div>

        <div class="col-md-1"></div>
      </div>
    </div>

    <div class="bottom_wrapper clearfix"></div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
export default {
  name: "MeetingParticipant",
  props: {
    publisher: Object,
    subscribers: Object,
  },
  setup(props) {
    const state = reactive({
      publisher: props.publisher,
      subscribers: props.subscribers,
    });

    return { state };
  },
};
</script>

<style scoped lang="scss">
.participant_window {
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

.participants {
  position: relative;
  list-style: none;
  padding: 20px 10px 0 10px;
  padding-top: 10px;
  margin: 0;
  height: 503px;
  overflow-y: scroll;
}
.participants .participant {
  clear: both;
  overflow: hidden;
  margin-bottom: 5px;
  transition: all 0.5s linear;
  opacity: 1;
  font-family: Century Gothic, sans-serif;
}

#help {
  /* position: absolute; */
  width: calc(100% - 20px);
  max-width: 800px;
  height: 500px;
  border-radius: 10px;
  left: 50%;
  top: 50%;
  transform: translateX(-1%) translateY(-1%);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
  background-color: #eeeeee;
  overflow: hidden;
}

#text_wrapper {
  display: inline-block;
  padding: 20px;
  border-radius: 15px;
  width: calc(100% - 85px);
  min-width: 100px;
  position: relative;

  background-color: #c2d6f8;
  margin-left: 10px;
  margin-top: 20px;
  text-align: left;
}

.bottom_wrapper {
  position: relative;
  width: 100%;
  height: 40px;
  background-color: #fff;
  padding: 10px 10px;
  position: absolute;
  bottom: 0;
}
</style>
