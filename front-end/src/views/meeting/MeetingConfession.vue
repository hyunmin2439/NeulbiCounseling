<template>
  <div style="background-color: rgb(225 236 255); height: 100vh">
    <div id="meetingheader" v-if="state.session">
      <ul style="text-align: left">
        <li style="float: left">
          <img src="@/assets/icon.png" alt="" style="width: 50px" />
          <span style="font-size: 25px; font-family: Binggrae">
            &nbsp;&nbsp;고해성사</span
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
            @click="participantShow"
            class="fas fa-user-friends"
            style="color: red"
          ></i>
        </li>
        <li>
          <button
            class="front__text-hover"
            type="button"
            @click="clickEmojiView"
            style="position: relative; margin-left: 50px; font-family: Binggrae"
          >
            반응
            <div
              v-if="state.emojiState"
              id="emojilist"
              style="
                position: absolute;
                display: flex;
                top: 120%;
                left: 0px;
                transform: translateX(calc(-50% + 26px));
                z-index: 99;
                background: #a39d9e;
                padding: 10px;
                column-gap: 5px;
                border-radius: var(--radius-md);
                box-shadow: var(--shadow-depth-2);
              "
            >
              <div
                v-for="(emoji, index) in state.EMOJI_MAP"
                :key="index"
                style="float: left; font-size: 25px; background-color: #a39d9e"
                @click="sendEmoji(index)"
              >
                {{ emoji }}
              </div>
            </div>
          </button>
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
              {{ data.confessionMeetingInfo.title }} 미팅룸
            </h1>
          </div>

          <!-- <hr style="color: #a6c0fe; height: 3px" /> -->
          <br />
          <div class="form-group" style="color: #333333; font-family: Binggrae">
            <h3 v-if="data.userInfo.id === data.confessionMeetingInfo.ownerId">
              방장
            </h3>
            <h3 v-else>게스트</h3>
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
        <div id="session-header"></div>

        <!-- 스트림 화면 -->
        <div id="session-body">
          <div class="row">
            <div id="stream" class="col-md-12">
              <div class="row">
                <div id="main-video" class="col-md-6">
                  <user-video :stream-manager="state.mainStreamManager" />
                </div>
                <div id="video-container" class="col-md-6">
                  <user-video
                    :stream-manager="state.publisher"
                    @click="updateMainVideoStreamManager(state.publisher)"
                  />
                  <user-video
                    v-for="sub in state.subscribers"
                    :key="sub.stream.connection.connectionId"
                    :stream-manager="sub"
                    @click.native="updateMainVideoStreamManager(sub)"
                  />
                </div>
              </div>
            </div>
            <div id="participantRoom" class="col-md-4" style="display: none">
              <meeting-participant
                v-if="state.publisher"
                :publisher="state.publisher"
                :subscribers="state.subscribers"
              ></meeting-participant>
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
import { useStore } from "vuex";
import { useRouter } from "vue-router";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

import { computed, reactive, onMounted } from "vue";
import MeetingParticipant from "./components/MeetingParticipant.vue";

export default {
  name: "MeetingConfession",

  components: {
    UserVideo,
    MeetingParticipant,
  },

  setup() {
    const store = useStore();
    const router = useRouter();
    const data = reactive({
      userInfo: computed(() => store.getters["root/userInfo"]),
      confessionMeetingInfo: computed(
        () => store.getters["root/confessionMeetingInfo"]
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
      emojiState: false,
      emoji: [],
      EMOJI_MAP: ["🔥", "🦑", "🤣", "😛", "😜", "🤪", "😝", "😔", "😪", "😷"],

      participantState: false,

      mySessionId: data.confessionMeetingInfo.meetingId,
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
      if (data.userInfo.id != data.confessionMeetingInfo.ownerId) {
        await store.dispatch("root/meetingJoinRoom", {
          meeting_id: data.confessionMeetingInfo.meetingId,
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

        state.session.on("signal:emoji", (event) => {
          console.log(event.data); // Message
          // + 받는 부분에서 함수 실행.
          handleDisplayFlyingEmoji(event.data);
          // state.emoji.push(event.data);
          console.log(event.from); // Connection object of the sender
          console.log(event.type); // The type of message
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
              console.log(
                state.publisher.stream.connection.data.split("%/%")[1]
              );
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
        window.addEventListener("reaction_added", handleSendFlyingEmoji);
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
      state.participantState = false;
      await store.dispatch("root/meetingExit", {
        meeting_id: data.confessionMeetingInfo.meetingId,
        user_id: data.userInfo.id,
        owner_id: data.confessionMeetingInfo.ownerId,
      });
      await store.dispatch(
        "root/confessionReviewList",
        data.confessionMeetingInfo.meetingId
      );
      await router.push({
        name: "ReviewConfession",
        params: {
          user_id: data.userInfo.id,
          meeting_id: data.confessionMeetingInfo.meetingId,
        },
      });

      window.removeEventListener("beforeunload", leaveSession);
    };

    const hideMainVideo = function () {
      state.mainStreamManager = null;
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

    const updateMainVideoStreamManager = async function (stream) {
      console.log(stream);
      if (state.mainStreamManager === stream) return;
      await hideMainVideo();
      state.mainStreamManager = stream;
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

    const clickEmojiView = function () {
      state.emojiState ^= 1;
    };

    const handleDisplayFlyingEmoji = async function (emoji) {
      console.log(`⭐ Displaying flying emoji: ${emoji}`);
      const node = document.createElement("mydiv");
      console.log(node);
      node.appendChild(document.createTextNode(emoji));
      console.log(node);
      node.className =
        Math.random() * 1 > 0.5 ? "emoji wiggle-1" : "emoji wiggle-2";
      // node.style.transform = `rotate(${-30 + Math.random() * 60}deg)`;
      // const deg = 10;
      const leftper = 6;
      const topper = -40;
      node.style.position = "absolute";
      // node.style.transform = `translateX(${deg})`;
      // node.style.left = `${Math.random() * 100}%`;
      node.style.right = `${leftper}%`;
      node.style.top = `${topper}%`;
      node.src = "";
      // + 이게 없으면 화면에 표시가 되지 않는다.
      document.body.appendChild(node);
      // overlayRef.current.appendChild(node);
      node.addEventListener("animationend", () => {
        console.log("지우기 함수 실행 중");
        // if (!document.body.contains) return;
        document.body.removeChild(node);
      });
    };

    const handleSendFlyingEmoji = function (e) {
      const { emoji } = e.detail;
      console.log(`⭐ Sending flying emoji: ${state.EMOJI_MAP[emoji]}`);
      const emojiNow = state.EMOJI_MAP[emoji];

      if (emojiNow) {
        state.session
          .signal({
            data: emojiNow, // Any string (optional)
            to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
            type: "emoji", // The type of message (optional)
          })
          .then(() => {
            console.log("Message successfully sent");
            // handleDisplayFlyingEmoji(emoji);
          })
          .catch((error) => {
            console.error(error);
          });
      }
    };

    const sendEmoji = function (emoji) {
      // console.log(state.EMOJI_MAP[emoji]);
      window.dispatchEvent(
        new CustomEvent("reaction_added", { detail: { emoji } })
      );
    };

    const participantShow = function () {
      if (state.participantState) {
        document.getElementById("stream").className = "col-md-12";
        document.getElementById("participantRoom").style.display = "none";
        state.participantState = false;
      } else {
        document.getElementById("stream").className = "col-md-8";
        document.getElementById("participantRoom").style.display = "block";
        state.participantState = true;
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
      state, onMounted,
      joinSession,
      leaveSession,
      updateMainVideoStreamManager,
      getToken,
      createSession,
      createToken,
      hideMyVideo,
      hideMainVideo,
      hideMyAudio,
      clickFilter,
      sendEmoji,
      handleSendFlyingEmoji,
      handleDisplayFlyingEmoji,
      clickEmojiView,
      participantShow,
      getVideo,
      offVideo,
    };
  },
};
</script>

<style scoped lang="scss">
#participantRoom {
  position: relative;
  left: 10%;
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

#emojilist:hover {
  cursor: pointer;
}

html {
  position: relative;
  min-height: 100%;
}

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

#main-container {
  padding-bottom: 80px;
  padding-top: 5%;
}
video {
  width: 100%;
  height: auto;
}
#video-container video {
  display: flex;
  justify-content: center;
  align-items: center;

  position: relative;
  float: left;
  // width: 45%;
  cursor: pointer;
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
#main-video video {
  position: relative;
  float: left;
  width: 100%;
  cursor: pointer;
  border-radius: 15px;
}
#main-video video + div {
  float: left;
  width: 100%;
  position: relative;
  margin-top: -50%;
}
#main-video p {
  display: inline-block;
  padding-left: 5px;
  padding-right: 5px;
  font-size: 22px;
  color: #777777;
  font-weight: bold;
  border-bottom-right-radius: 4px;
  position: relative;
  top: -30%;
}

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
  /* display: inline; */
  position: absolute;
  float: left;
  bottom: 0;
  width: 100%;
  height: 60px;
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

// mydiv {
//   position: fixed;
//   top: 20%;
//   bottom: 0px;
//   left: 72%;
//   right: 0px;
//   overflow: hidden;
//   pointer-events: none;
//   user-select: none;
//   z-index: 99;
// }

// .emoji.wiggle-1 {
//   animation: emerge 2s forwards, wiggle-1 2s ease-in-out infinite alternate;
//   font-size: 60px;
// }
// .emoji.wiggle-2 {
//   animation: emerge 2s forwards, wiggle-2 2s ease-in-out infinite alternate;
//   font-size: 60px;
// }
.flying-emojis :global(.emoji.wiggle-1) {
  animation: emerge 3s forwards, wiggle-1 3s ease-in-out infinite alternate;
  font-size: 60px;
}
.flying-emojis :global(.emoji.wiggle-2) {
  animation: emerge 3s forwards, wiggle-2 3s ease-in-out infinite alternate;
  font-size: 60px;
}

@keyframes emerge {
  to {
    right: 6%;
    top: -30%;
    opacity: 0;
  }
}
@keyframes wiggle-1 {
  from {
    margin-top: 450px;
  }
  to {
    margin-top: 600px;
  }
}
@keyframes wiggle-2 {
  from {
    margin-top: 450px;
  }
  to {
    margin-top: 600px;
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

  background: black;
  color: #fff;
}
</style>
