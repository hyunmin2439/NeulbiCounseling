<template>
  <div class="d-flex justify-content-center">
    <button class="front__text-hover"  data-bs-toggle="modal" data-bs-target="#exampleModal">고해성사 생성하기</button>
    <!-- 마스크 모달 -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">고해성사 방을 생성하세요.</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form>
              <h3 class="py-3">방생성</h3>
              <div class="d-flex justify-content-center my-3">
                <div class="searchBox">
                    <input class="searchInput" type="text" placeholder="방 제목" v-model="confessionChatRoom.title">
                </div>
              </div>
              <br>
              <!-- 선택하여 정수를 반환하도록 수정해야함 -->
              <div>
                <div class="pagination my-3 mx-2">
                  <div id="topic_div_1" @click="clickSearchList(1, $event)" style="cursor: pointer;">
                    <p>
                      학업
                    </p>
                  </div>
                  <div id="topic_div_2" @click="clickSearchList(2, $event)" style="cursor: pointer;">
                    <p>
                      가정
                    </p>
                  </div>
                  <div id="topic_div_3" @click="clickSearchList(3, $event)" style="cursor: pointer;">
                    <p>
                      취업
                    </p>
                  </div>
                </div>
                <div class="pagination my-3 mx-2">
                  <div id="topic_div_4" @click="clickSearchList(4, $event)" style="cursor: pointer;">
                    <p>
                      진로
                    </p>
                  </div>
                  <div id="topic_div_5" @click="clickSearchList(5, $event)" style="cursor: pointer;">
                    <p>
                      연애
                    </p>
                  </div>
                  <div id="topic_div_6" @click="clickSearchList(6, $event)" style="cursor: pointer;">
                    <p>
                      결혼
                    </p>
                  </div>
                </div>
              </div>
              <!-- 제한 인원 수 : 정수형 -->
              <div class="d-flex justify-content-center my-3">
                <div class="searchBox">
                    <input class="searchInput" type="number" placeholder="참가자 수" v-model="confessionChatRoom.participants" @keyup="checkParticipants">
                </div>
              </div>
              <br>
              <div class="d-flex justify-content-center my-3">
                <div class="searchBox">
                  <input class="searchInput" type="text" placeholder="방 설명" v-model="confessionChatRoom.description">
                </div>
              </div>
              <br><br>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">종료</button>
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="clickCreateMeeting">생성</button>
          </div>
        </div>
      </div>
    </div>
  </div>
      
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'ConfessionCreate',
  setup() {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      userInfo: computed(() => store.getters['root/userInfo']),
      confessionMeetingInfo: computed(() => store.getters['root/confessionMeetingInfo']),
      categories: [
        { value: "학업", number: "1" },
        { value: "가정", number: "2" },
        { value: "취업", number: "3" },
        { value: "진로", number: "4" },
        { value: "연애", number: "5" },
        { value: "결혼", number: "6" },
      ],
      categoryName: 'select'
    })

    const confessionChatRoom = reactive({
      title: null, 
      topicCategoryId: null,  
      participants: null, 
      description: null,
    })

    const clickSearchList = function (num, event) {
      confessionChatRoom.topicCategoryId = num
      state.categoryName = state.categories[num-1].value
      let targetId = event.currentTarget.id
      const topic_tag = document.getElementById(targetId)
      document.getElementById('topic_div_1').setAttribute('data-state', '')
      document.getElementById('topic_div_2').setAttribute('data-state', '')
      document.getElementById('topic_div_3').setAttribute('data-state', '')
      document.getElementById('topic_div_4').setAttribute('data-state', '')
      document.getElementById('topic_div_5').setAttribute('data-state', '')
      document.getElementById('topic_div_6').setAttribute('data-state', '')
      topic_tag.setAttribute('data-state', 'active')
    }

    const clickCreateMeeting = async function() {
      const body = {
        title: confessionChatRoom.title,
        description: confessionChatRoom.description,
        participants: confessionChatRoom.participants,
        meetingCategoryId: 1,
        topicCategoryId: confessionChatRoom.topicCategoryId,
        ownerId: state.userInfo.id,
        chatRoomId: 0,
      }
      await store.dispatch('root/confessionCreateMeeting', body)
      await router.push({
        name: 'MeetingConfession',
        params: {
          meeting_id: state.confessionMeetingInfo.meetingId
        }
      })
    }

    const checkParticipants = function () {
      if (confessionChatRoom.participants > 6) {
        alert('6명을 초과한 인원으로 방을 생성할 수 없습니다.')
        confessionChatRoom.participants = 0
      }
    }

    return { state, confessionChatRoom, clickSearchList, clickCreateMeeting, checkParticipants }
  }
}
</script>

<style scoped lang="scss">
// 전체 틀
@import url("https://fonts.googleapis.com/css?family=Poppins:400,500,600,700&display=swap");

.front__text-header {
  font-weight: 700;
  font-family: "Oswald";
  text-transform: uppercase;
  font-size: 20px;
}

.front__text-para {
  position: relative;
  top: -5px;

  color: #000;
  font-size: 14px;
  letter-spacing: 0.4px;
  font-weight: 400;
  font-family: "Montserrat", sans-serif;
}

.front-icons {
  position: relative;
  top: 0;
  font-size: 14px;
  margin-right: 6px;
  color: gray;
}

.front__text-hover {
  position: relative;
  top: 10px;
  font-size: 15px;
  color: #bbd2f9;
  backface-visibility: hidden;
  font-family: "Binggrae-Taom";

  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: .4px;

  border: 2px solid #bbd2f9;
  padding: 8px 15px;
  border-radius: 30px;

  background: #bbd2f9;
  color: #fff;
}


// 버튼들

.pagination {
  display: flex;
  justify-content: center;
}
.pagination div {
  // flex: 1;
  margin: 0px 5px;
  background: #dde1e7;
  border-radius: 3px;
  width: 100%;
  box-shadow: -3px -3px 7px #ffffff73, 3px 3px 5px rgba(94, 104, 121, 0.288);
}
.pagination div p {
  font-size: 18px;
  text-decoration: none;
  color: #4d3252;
  height: 80%;
  width: 100%;
  // display: block;
  line-height: 45px;
  margin-bottom: 0px;
}
.pagination div[data-state=active] {
  box-shadow: inset -3px -3px 7px #ffffff73,
    inset 3px 3px 5px rgba(94, 104, 121, 0.288);
}
.pagination div[data-state=active] p {
  font-size: 17px;
  font-weight: bold;
}

// 검색창
.searchBox {
  position: absolute;
  // transform:  translate(-50%,50%);
  background: white;
  height: 10%;
  width: 90%;
  border-radius: 40px;
  padding: 10px;
  border: 2px solid #bbd2f9;
}


.searchInput {
  border:none;
  background: none;
  outline:none;
  float:left;
  padding: 0;
  color: black;
  font-size: 16px;
  transition: 0.4s;
  line-height: 20px;
  width: 90%;
  padding: 0 6px;
}


@media screen and (max-width: 620px) {
.searchBox:hover > .searchInput {
    width: 150px;
    padding: 0 6px;
  }
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}


</style>