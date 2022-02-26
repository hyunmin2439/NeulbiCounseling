<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" />
    <div id="whatis">
      <div v-if="!streamManager.stream.videoActive">
        <div id="bgimg">
          <img
            :src="
              require('@/assets/back/back' +
                streamManager.stream.connection.data.split('%/%')[2] +
                '.png')
            "
            alt=""
          />
          <div id="maskimg-2">
            <img
              :src="
                require('@/assets/mask/mask' +
                  streamManager.stream.connection.data.split('%/%')[1] +
                  '.png')
              "
              alt=""
              style="width: 103%"
            />
          </div>
        </div>
      </div>
      <div id="nameismute">
        <p>
          {{ state.clientData.clientData }}
        </p>
        <p v-if="streamManager.stream.videoActive">
          <i class="fas fa-video" style="color: green; font-size: 15px"></i>
        </p>
        <p v-else>
          <i class="fas fa-video-slash" style="color: red; font-size: 15px"></i>
        </p>
        <p v-if="streamManager.stream.audioActive">
          <i
            class="fas fa-microphone"
            style="color: green; font-size: 15px"
          ></i>
        </p>
        <p v-else>
          <i
            class="fas fa-microphone-slash"
            style="color: red; font-size: 15px"
          ></i>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "@vue/reactivity";
import OvVideo from "./OvVideo";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
  },
  setup(props) {
    const state = reactive({
      clientData: computed(() => getConnectionData()),
    });

    const getConnectionData = function () {
      const { connection } = props.streamManager.stream;
      const conn = connection.data.split("%/%");
      console.log(conn[0]);
      return JSON.parse(conn[0]);
    };

    return { state, getConnectionData };
  },
};
</script>

<style scoped lang="scss">
#main-video video {
  position: relative;
  float: left;
  width: 100%;
  cursor: pointer;
  border-radius: 15px;
}

#main-video p {
  display: inline-block;
  padding-left: 5px;
  padding-right: 5px;
  color: grey;
  font-weight: bold;
  border-bottom-right-radius: 4px;
  // margin-top: 10%;
  position: relative;
  top: -40%;
}

#main-video #nameismute {
  // display: inline-block;
  justify-content: center;
  align-items: center;
  padding-right: 10%;
  height: 10vh;
}

#main-video #bgimg {
  position: absolute;
  left: 50%;
  top: 0%;
  left: 0%;
  right: 0;
  bottom: 0;
  padding-top: 2%;
}

#video-container video {
  display: flex;
  justify-content: center;
  align-items: center;

  position: relative;
  float: left;
  width: 45%;
  cursor: pointer;
  margin-left: 20px;
  margin-bottom: 15px;
}

#video-container video + div {
  float: left;
  width: 50%;
  position: relative;
  margin-left: -50%;
  display: grid;
}

#video-container p {
  display: inline-block;
  padding-left: 5px;
  padding-right: 5px;
  color: grey;
  font-weight: bold;
  border-bottom-right-radius: 4px;
  // margin-top: 10%;
}

video {
  width: 100%;
  height: auto;
}

#session img {
  width: 100%;
  display: inline-block;
  object-fit: contain;
  vertical-align: baseline;
}

#maskimg {
  position: relative;
  width: 100%;
  cursor: pointer;
}

#nameismute {
  margin-top: 48%;
  // display: inline-block;
  justify-content: center;
  align-items: center;
  padding-left: 10%;
}

#maskimg {
  display: inline-block;
  justify-content: center;
  align-items: center;
}

#whatis {
  position: relative;
  padding-top: 5%;
}

#bgimg {
  position: absolute;
  left: 50%;
  top: 0%;
  left: 10%;
  right: 0;
  bottom: 0;
  padding-top: 2%;
}

#main-video #maskimg-2 {
  // width: 103%;
}

#maskimg-2 {
  position: absolute;
  top: 10%;
  left: 22%;
  z-index: 99;
  right: 24%;
  bottom: 0;
}
</style>
