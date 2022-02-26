<template id="wrapper">
  <div style="background-color: rgb(225 236 255); height: 100vh">
    <!-- session 연결됐을 때 상담 도구 모음 -->
    <div id="meetingheader" v-if="state.session">
      <ul style="text-align: left">
        <li style="float: left">
          <img src="@/assets/icon.png" alt="" style="width: 50px" />
          <span style="font-size: 25px; font-family: Binggrae">
            &nbsp;&nbsp;고민상담</span
          >
        </li>
        <li>
          <i
            @click="leaveSession"
            class="far fa-times-circle"
            style="color: red; margin-left: 50px"
            >&nbsp;<span style="font-family: Binggrae">종료</span></i
          >
        </li>
        <li>
          <i
            @click="chatroomShow"
            class="far fa-comments"
            style="color: red; margin-left: 50px"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            title="채팅방"
          ></i>
        </li>
        <li>
          <i
            v-if="!state.audioEcho"
            type="button"
            @click="clickFilter"
            class="fab fa-creative-commons-sampling"
            style="color: red"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            title="노래방모드"
          ></i
          ><i
            v-else
            type="button"
            @click="clickFilter"
            class="fab fa-creative-commons-sampling"
            style="color: green"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            title="노래방모드"
          ></i>
        </li>
        <li>
          <i
            v-if="state.audioState"
            @click="hideMyAudio(state.audioState)"
            class="fas fa-microphone"
            style="color: green"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            title="오디오 ON/OFF"
          ></i>
          <i
            v-else
            @click="hideMyAudio(state.audioState)"
            class="fas fa-microphone-slash"
            style="color: red"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            title="오디오 ON/OFF"
          ></i>
        </li>
        <li>
          <i
            v-if="state.videoState"
            @click="hideMyVideo(state.videoState)"
            class="fas fa-video"
            style="color: green"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            title="비디오 ON/OFF"
          ></i>

          <i
            v-else
            @click="hideMyVideo(state.videoState)"
            class="fas fa-video-slash"
            style="color: red"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            title="비디오 ON/OFF"
          ></i>
        </li>
      </ul>
    </div>
    <div id="main-container" class="container" style="padding-bottom: 0px;">
      <!-- meeting 입장 초기 화면 -->
      <div id="join" v-if="!state.session">
        <div
          id="join-dialog"
          class="jumbotron vertical-center"
        >
          <div
            class="d-flex justify-content-center"
            style="border-bottom: 3px solid #a6c0fe; padding-bottom: 20px"
          >
            <h1 style="color: #333333; font-family: Binggrae">
              {{ data.adviceMeetingInfo.ownerNickname }}님의 미팅룸
            </h1>
          </div>

          <!-- <hr style="color: #a6c0fe; height: 3px" /> -->
          <br />
          <div class="form-group" style="color: #333333; font-family: Binggrae">
            <h3
              v-if="data.userInfo.id === data.adviceMeetingInfo.ownerId"
              style="font-family: Binggrae"
            >
              상담가
            </h3>
            <h3 v-else style="font-family: Binggrae">상담자</h3>
            <br />
            <div>
              <video id="myVideo" style="border: 1px solid #ddd"></video>
              <br /><br />
              <div class="d-flex" style="justify-content: center">
                <button
                  v-if="!state.videoState"
                  class="front__text-hover"
                  type="button"
                  @click="getVideo"
                  style="font-family: Binggrae; font-weight: lighter"
                >
                  <i
                    class="fas fa-video"
                    style="color: green; font-size: 15px"
                  ></i>
                  비디오 시작
                </button>
                <button
                  v-else
                  class="front__text-hover"
                  type="button"
                  @click="offVideo"
                  style="font-family: Binggrae; font-weight: lighter"
                >
                  <i
                    class="fas fa-video-slash"
                    style="color: red; font-size: 15px"
                  ></i>
                  비디오 중지
                </button>
                <button
                  v-if="!state.audioState"
                  class="front__text-hover"
                  type="button"
                  @click="state.audioState = !state.audioState"
                  style="
                    margin-left: 20px;
                    font-family: Binggrae;
                    font-weight: lighter;
                  "
                >
                  <i
                    class="fas fa-microphone"
                    style="color: green; font-size: 15px"
                  ></i>
                  말하기
                </button>
                <button
                  v-else
                  class="front__text-hover"
                  type="button"
                  @click="state.audioState = !state.audioState"
                  style="
                    margin-left: 20px;
                    font-family: Binggrae;
                    font-weight: lighter;
                  "
                >
                  <i
                    class="fas fa-microphone-slash"
                    style="color: red; font-size: 15px"
                  ></i>
                  음소거
                </button>
                <button
                  class="front__text-hover"
                  @click="joinSession()"
                  style="
                    margin-left: 20px;
                    font-family: Binggrae;
                    font-weight: lighter;
                  "
                >
                  참가하기
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 화상 채팅 화면 -->
      <div id="session" v-if="state.session">
        <!-- 미팅 떠나기 버튼 -->

        <div id="session-body">
          <div class="row">
            <div id="stream" class="col-md-12">
              <!-- 스트림 화면 -->
              <div class="row">
                <div id="video-container" class="col-md-12">
                  <div style="height: 120px"></div>
                  <user-video :stream-manager="state.publisher" />
                  <user-video
                    v-for="sub in state.subscribers"
                    :key="sub.stream.connection.connectionId"
                    :stream-manager="sub"
                  />
                </div>
              </div>
            </div>
            <div id="chatroom" class="col-md-4" style="display: none">
              <meeting-chat-room :session="state.session"></meeting-chat-room>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./components/UserVideo.vue";
import MeetingChatRoom from "./components/MeetingChatRoom.vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

import { computed, reactive, onMounted } from "vue";

export default {
  name: "MeetingAdvice",

  components: {
    UserVideo,
    MeetingChatRoom,
  },

  setup() {
    const store = useStore();
    const router = useRouter();
    const data = reactive({
      userInfo: computed(() => store.getters["root/userInfo"]),
      adviceMeetingInfo: computed(
        () => store.getters["root/adviceMeetingInfo"]
      ),
      meetingParticipantLimit: computed(
        () => store.getters["root/meetingParticipantLimit"]
      ),
    });

    const state = reactive({
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      videoState: false,
      audioState: false,

      maskState: false,
      audioEcho: false,

      chatState: false,

      mySessionId: data.adviceMeetingInfo.meetingId,
      myUserName: data.userInfo.nickname,

      localstream: undefined,
    });

    onMounted(() => {
      window.scrollTo(0, 0);
    })

    const joinSession = async function () {
      if (state.videoState) {
        const myVideoStream = document.getElementById("myVideo");
        myVideoStream.pause();
        myVideoStream.src = "";
        state.localstream.getTracks()[0].stop();
      }
      if (data.userInfo.id != data.adviceMeetingInfo.ownerId) {
        await store.dispatch("root/meetingJoinRoom", {
          meeting_id: data.adviceMeetingInfo.meetingId,
          user_id: data.userInfo.id,
        });
      }
      if (data.meetingParticipantLimit == false) {
        alert("참가할 수 없습니다.");
        await router.push({ name: "Confession" });
      } else {
        // --- Get an OpenVidu object ---
        state.OV = new OpenVidu();
        // --- Init a session ---
        state.session = state.OV.initSession();
        // --- Specify the actions when events take place in the session ---

        // On every new Stream received...
        state.session.on("streamCreated", ({ stream }) => {
          const subscriber = state.session.subscribe(stream);
          state.subscribers.push(subscriber);
        });

        // On every Stream destroyed...
        state.session.on("streamDestroyed", ({ stream }) => {
          const index = state.subscribers.indexOf(stream.streamManager, 0);
          if (index >= 0) {
            state.subscribers.splice(index, 1);
          }
        });

        // On every asynchronous exception...
        state.session.on("exception", ({ exception }) => {
          console.warn(exception);
        });

        // --- Connect to the session with a valid user token ---

        // 'getToken' method is simulating what your server-side should do.
        // 'token' parameter should be retrieved and returned by your own backend
        getToken(String(state.mySessionId)).then((token) => {
          state.session
            .connect(token, { clientData: state.myUserName })
            .then(() => {
              // --- Get your own camera stream with the desired properties ---
              let publisher = state.OV.initPublisher(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: state.videoState, // Whether you want to start publishing with your audio unmuted or not
                publishVideo: state.audioState, // Whether you want to start publishing with your video enabled or not
                resolution: "640x480", // The resolution of your video
                frameRate: 30, // The frame rate of your video
                insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
                mirror: false, // Whether to mirror your local video or not
              });

              state.mainStreamManager = publisher;
              state.publisher = publisher;

              // --- Publish your stream ---
              state.session.publish(state.publisher);
              console.log(state.publisher);
            })
            .catch((error) => {
              console.log(
                "There was an error connecting to the session:",
                error.code,
                error.message
              );
            });
        });

        window.addEventListener("beforeunload", leaveSession);
      }
    };

    const leaveSession = async function () {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (state.session) {
        state.session.disconnect();
      }

      state.session = undefined;
      state.mainStreamManager = undefined;
      state.publisher = undefined;
      state.subscribers = [];
      state.OV = undefined;
      state.videoState = false;
      state.audioState = false;
      state.maskState = false;
      state.audioEcho = false;
      state.chatState = false;
      await store.dispatch("root/meetingExit", {
        meeting_id: data.adviceMeetingInfo.meetingId,
        user_id: data.userInfo.id,
        owner_id: data.adviceMeetingInfo.ownerId,
      });
      if (data.userInfo.id != data.adviceMeetingInfo.ownerId) {
        await router.push({
          name: "ReviewAdvice",
          params: {
            user_id: data.userInfo.id,
            consultant_id: data.adviceMeetingInfo.ownerId,
          },
        });
      } else {
        await router.push({ name: "Home" });
      }

      window.removeEventListener("beforeunload", leaveSession);
    };

    const hideMyVideo = function (isActive) {
      if (isActive) {
        state.publisher.publishVideo(false);
        state.videoState = false;
      } else {
        state.publisher.publishVideo(true);
        state.videoState = true;
      }
    };

    const hideMyAudio = function (isActive) {
      if (isActive) {
        state.publisher.publishAudio(false);
        state.audioState = false;
      } else {
        state.publisher.publishAudio(true);
        state.audioState = true;
      }
    };

    const getToken = function (mySessionId) {
      return createSession(mySessionId).then((sessionId) =>
        createToken(sessionId)
      );
    };

    const createSession = function (sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    };
    const createToken = function (sessionId) {
      const maskback = data.userInfo.maskId + "%/%" + data.userInfo.backId;
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            JSON.stringify({
              type: "WEBRTC",
              data: maskback,
              role: "PUBLISHER",
              kurentoOptions: {
                allowedFilters: ["GStreamerFilter", "FaceOverlayFilter"],
              },
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    };

    // const addMask = function () {
    //   if (state.maskState) {
    //     state.publisher.stream
    //       .removeFilter()
    //       .then(() => {
    //         console.log("Filter removed");
    //         state.maskState = false;
    //       })
    //       .catch((error) => {
    //         console.error(error);
    //       });
    //   } else {
    //     state.publisher.stream
    //       .applyFilter("FaceOverlayFilter")
    //       .then((filter) => {
    //         filter.execMethod("setOverlayedImage", {
    //           uri: "https://cdn.pixabay.com/photo/2013/07/12/14/14/derby-148046_960_720.png",
    //           offsetXPercent: "-0.2F",
    //           offsetYPercent: "-0.8F",
    //           widthPercent: "1.3F",
    //           heightPercent: "1.0F",
    //         });
    //         state.maskState = true;
    //       });
    //   }
    // };

    const clickFilter = function () {
      if (state.audioEcho) {
        state.publisher.stream
          .removeFilter()
          .then(() => {
            console.log("Filter removed");
            state.audioEcho = false;
          })
          .catch((error) => {
            console.error(error);
          });
      } else {
        state.publisher.stream
          .applyFilter("GStreamerFilter", {
            command: "audioecho delay=50000000 intensity=0.6 feedback=0.4",
          })
          .then(() => {
            console.log("Video rotated!");
            state.audioEcho = true;
          })
          .catch((error) => {
            console.error(error);
          });
      }
    };

    const chatroomShow = function () {
      if (state.chatState) {
        document.getElementById("stream").className = "col-md-12";
        document.getElementById("chatroom").style.display = "none";
        state.chatState = false;
      } else {
        document.getElementById("stream").className = "col-md-8";
        document.getElementById("chatroom").style.display = "block";
        state.chatState = true;
      }
    };

    const getVideo = function () {
      const myVideoStream = document.getElementById("myVideo");
      navigator.getMedia =
        navigator.getUserMedia ||
        navigator.webkitGetUserMedia ||
        navigator.mozGetUserMedia ||
        navigator.msGetUserMedia;
      navigator.getMedia(
        { video: true, audio: false },

        function (stream) {
          state.localstream = stream;
          myVideoStream.srcObject = stream;
          myVideoStream.play();
          state.videoState = true;
        },

        function (error) {
          alert("webcam not working");
        }
      );
    };

    const offVideo = function () {
      const myVideoStream = document.getElementById("myVideo");
      myVideoStream.pause();
      myVideoStream.src = "";
      state.localstream.getTracks()[0].stop();
      state.videoState = false;
    };

    return {
      data,
      state,
      onMounted,
      joinSession,
      leaveSession,
      getToken,
      createSession,
      createToken,
      hideMyVideo,
      hideMyAudio,
      clickFilter,
      // handleRemoveFlyingEmoji,
      chatroomShow,
      getVideo,
      offVideo,
    };
  },
};
</script>

<style scoped lang="scss">
// .icon {
//   width: 75px;
//   height: 75px;
// }
.tooltip-inner {
  background-color: #2f4fff;
  box-shadow: 0px 0px 4px black;
  opacity: 1 !important;
}
.tooltip.bs-tooltip-right .tooltip-arrow::before {
  border-right-color: #2f4fff !important;
}
.tooltip.bs-tooltip-left .tooltip-arrow::before {
  border-left-color: #2f4fff !important;
}
.tooltip.bs-tooltip-bottom .tooltip-arrow::before {
  border-bottom-color: #2f4fff !important;
}
.tooltip.bs-tooltip-top .tooltip-arrow::before {
  border-top-color: #2f4fff !important;
}

#chatroom {
  position: relative;
  left: 10%;
  margin-top: 5%;
}

#myVideo {
  background-color: #1d1e22;
  width: 600px;
  height: 500px;
  object-fit: cover;
}
#meetingheader {
  padding-top: 5px;
  border-bottom: 3px solid #a6c0fe;
  padding-right: 50px;
  height: 60px;
}

ul {
  list-style: none;
}
ul li {
  display: inline;
  margin-left: 30px;
  float: right;
}
ul li i {
  padding-top: 15px;
  font-size: 22px;
}
ul li i:hover {
  cursor: pointer;
}

button {
  font-family: inherit;
  font-family: "Roboto Mono", monospace;
}

#emojilist:hover {
  cursor: pointer;
}

html {
  position: relative;
  min-height: 100%;
}

nav {
  height: 50px;
  width: 100%;
  z-index: 1;
  background-color: #4d4d4d !important;
  border-color: #4d4d4d !important;
  border-top-right-radius: 0 !important;
  border-top-left-radius: 0 !important;
}

.navbar-header {
  width: 100%;
}

.nav-icon {
  padding: 5px 15px 5px 15px;
  float: right;
}

nav a {
  color: #ccc !important;
}

nav i.fa {
  font-size: 40px;
  color: #ccc;
}

nav a:hover {
  color: #a9a9a9 !important;
}

nav i.fa:hover {
  color: #a9a9a9;
}

#main-container {
  padding-bottom: 80px;
  padding-top: 5%;
}

/*vertical-center {
	position: relative;
	top: 30%;
	left: 50%;
	transform: translate(-50%, -50%);
}*/

.horizontal-center {
  margin: 0 auto;
}

.form-control {
  color: #0088aa;
  font-weight: bold;
}

.form-control:focus {
  border-color: #0088aa;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075),
    0 0 8px rgba(0, 136, 170, 0.6);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075),
    0 0 8px rgba(0, 136, 170, 0.6);
}

input.btn {
  font-weight: bold;
}

.btn {
  font-weight: bold !important;
}

.openvidu-logo {
  height: 35px;
  float: right;
  margin: 12px 0;
  -webkit-transition: all 0.1s ease-in-out;
  -moz-transition: all 0.1s ease-in-out;
  -o-transition: all 0.1s ease-in-out;
  transition: all 0.1s ease-in-out;
}

.openvidu-logo:hover {
  -webkit-filter: grayscale(0.5);
  filter: grayscale(0.5);
}

.demo-logo {
  margin: 0;
  height: 22px;
  float: left;
  padding-right: 8px;
}

a:hover .demo-logo {
  -webkit-filter: brightness(0.7);
  filter: brightness(0.7);
}

#join-dialog {
  margin-left: auto;
  margin-right: auto;
  max-width: 70%;
}

#join-dialog h1 {
  color: #4d4d4d;
  font-weight: bold;
  text-align: center;
}

#img-div {
  text-align: center;
  margin-top: 3em;
  margin-bottom: 3em;
  /*position: relative;
	top: 20%;
	left: 50%;
	transform: translate(-50%, -50%);*/
}

#img-div img {
  height: 15%;
}

#join-dialog label {
  color: #0088aa;
}

#join-dialog input.btn {
  margin-top: 15px;
}

#session-header {
  margin-bottom: 20px;
}

#session-title {
  display: inline-block;
}

#video-container video {
  display: flex;
  justify-content: center;
  align-items: center;

  position: relative;
  float: left;
  // width: 45%;
  cursor: pointer;
  // margin-left: 25px;
  // padding-top: 5%;
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
  color: #ffe54c;
  font-weight: bold;
  border-bottom-right-radius: 4px;
}

video {
  width: 100%;
  height: auto;
}

/* 수정 중 */

#session img {
  width: 100%;
  display: inline-block;
  object-fit: contain;
  vertical-align: baseline;
}

/*스타일이 공유되고 있음.*/
/* #session #video-container img {
  position: relative;
  width: 50%;
  cursor: pointer;
  object-fit: cover;
  height: 180px;
} */

@media screen and (max-width: 991px) and (orientation: portrait) {
  #join-dialog {
    max-width: inherit;
  }
  #img-div img {
    height: 10%;
  }
  #img-div {
    margin-top: 2em;
    margin-bottom: 2em;
  }
  .container-fluid > .navbar-collapse,
  .container-fluid > .navbar-header,
  .container > .navbar-collapse,
  .container > .navbar-header {
    margin-right: 0;
    margin-left: 0;
  }
  .navbar-header i.fa {
    font-size: 30px;
  }
  .navbar-header a.nav-icon {
    padding: 7px 3px 7px 3px;
  }
}

#session-footer {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 2%;
}

@media only screen and (max-height: 767px) and (orientation: landscape) {
  #img-div {
    margin-top: 1em;
    margin-bottom: 1em;
  }
  #join-dialog {
    max-width: inherit;
  }
}

.front__text-hover {
  position: relative;
  top: 3px;
  font-size: 15px;
  backface-visibility: hidden;

  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.4px;

  border: 2px solid;
  padding: 8px 15px;
  margin-top: 0px;
  border-radius: 30px;

  background: #454242;
  color: #fff;
}
</style>
