<template>
  <div>
    <div class="container" v-if="state.profileConsultantProfile.nickname==null">
      <h3 class="py-3" style="text-align: left;">상담가 프로필 등록</h3>
        <!-- 선택하여 정수를 반환하도록 수정해야함 -->
      <div>
        <div class="pagination d-flex justify-content-start my-3 mx-2">
          <div id="topic_div_1" @click="clickSelect(1, $event)" style="cursor: pointer;" class="me-2">
            <p>
              학업
            </p>
          </div>
          <div id="topic_div_2" @click="clickSelect(2, $event)" style="cursor: pointer;" class="mx-2">
            <p>
              가정
            </p>
          </div>
          <div id="topic_div_3" @click="clickSelect(3, $event)" style="cursor: pointer;" class="mx-2">
            <p>
              취업
            </p>
          </div>
          <div id="topic_div_4" @click="clickSelect(4, $event)" style="cursor: pointer;" class="mx-2">
            <p>
              진로
            </p>
          </div>
          <div id="topic_div_5" @click="clickSelect(5, $event)" style="cursor: pointer;" class="mx-2">
            <p>
              연애
            </p>
          </div>
          <div id="topic_div_6" @click="clickSelect(6, $event)" style="cursor: pointer;" class="mx-2">
            <p>
              결혼
            </p>
          </div>
        </div>
      </div>
      <div class="d-flex justify-content-start py-3">
        <div class="searchBox">
          <input class="searchInput" type="text" placeholder="Description" v-model="consultant.description">
        </div>
        <button class="front__text-hover d-flex justify-content-start mx-3" @click="clickCreateConsultant">상담가 정보 등록</button>
      </div>
      <br><br><br><br>
    </div>
    <div v-else>
      <h3 class="py-3" style="text-align: left;">상담가 프로필 변경</h3>
        <!-- 선택하여 정수를 반환하도록 수정해야함 -->
      <div>
        <div class="pagination d-flex justify-content-start my-3 mx-2">
          <div id="topic_div_1" @click="clickSearchList(1, $event)" style="cursor: pointer;" class="me-2">
            <p>
              학업
            </p>
          </div>
          <div id="topic_div_2" @click="clickSearchList(2, $event)" style="cursor: pointer;" class="mx-2">
            <p>
              가정
            </p>
          </div>
          <div id="topic_div_3" @click="clickSearchList(3, $event)" style="cursor: pointer;" class="mx-2">
            <p>
              취업
            </p>
          </div>
          <div id="topic_div_4" @click="clickSearchList(4, $event)" style="cursor: pointer;" class="mx-2">
            <p>
              진로
            </p>
          </div>
          <div id="topic_div_5" @click="clickSearchList(5, $event)" style="cursor: pointer;" class="mx-2">
            <p>
              연애
            </p>
          </div>
          <div id="topic_div_6" @click="clickSearchList(6, $event)" style="cursor: pointer;" class="mx-2">
            <p>
              결혼
            </p>
          </div>
        </div>
      </div>
      <div class="d-flex justify-content-start py-3">
        <div class="searchBox">
          <input class="searchInput" type="text" placeholder="Description" v-model="consultant.description">
        </div>
        <button class="front__text-hover d-flex justify-content-start mx-3" @click="clickProfileModifyDescription">상담가 정보 변경</button>
      </div>
      <br><br><br><br>
    </div>
    <profile-consultant-list :userInfo="state.userInfo">
    </profile-consultant-list>
  </div>
</template>

<script>
import ProfileConsultantList from './ProfileConsultantList.vue'

import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";
export default {
  name: "ProfileConsultant",
  components: {
    ProfileConsultantList,
  },
  props: {
    userInfo: Object,
  },
  setup(props) {
    const store = useStore();
    const state = reactive({
      userInfo: props.userInfo,
      profileConsultantProfile: computed(() => store.getters["root/profileConsultantProfile"]),
      description: "",
      topic: "",
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

    const consultant = reactive({
      topicCategoryId: null,  
      description: state.profileConsultantProfile.description,
    })

    onMounted(() => {
      if(state.profileConsultantProfile.topicCategoryName == '학업'){
        const targetId = 'topic_div_1'
        const topic_tag = document.getElementById(targetId)
        topic_tag.setAttribute('data-state', 'active')
      } else if (state.profileConsultantProfile.topicCategoryName == '가정') {
        const targetId = 'topic_div_2'
        const topic_tag = document.getElementById(targetId)
        topic_tag.setAttribute('data-state', 'active')
      } else if (state.profileConsultantProfile.topicCategoryName == '취업') {
        const targetId = 'topic_div_3'
        const topic_tag = document.getElementById(targetId)
        topic_tag.setAttribute('data-state', 'active')
      } else if (state.profileConsultantProfile.topicCategoryName == '진로') {
        const targetId = 'topic_div_4'
        const topic_tag = document.getElementById(targetId)
        topic_tag.setAttribute('data-state', 'active')
      } else if (state.profileConsultantProfile.topicCategoryName == '연애') {
        const targetId = 'topic_div_5'
        const topic_tag = document.getElementById(targetId)
        topic_tag.setAttribute('data-state', 'active')
      } else if (state.profileConsultantProfile.topicCategoryName == '결혼') {
        const targetId = 'topic_div_6'
        const topic_tag = document.getElementById(targetId)
        topic_tag.setAttribute('data-state', 'active')
      }
    })

    const clickSearchList = function (num, event) {
      consultant.topicCategoryId = num
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
      clickProfileModifyTopicCategory(consultant.topicCategoryId)
    }

    const clickSelect = function (num, event) {
      consultant.topicCategoryId = num
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

    const clickCreateConsultant = async function () {
      await store.dispatch("root/adviceCreateConsultant", {
        description: consultant.description,
        topicCategoryId: consultant.topicCategoryId,
        userId: state.userInfo.id,
      })
      await store.dispatch("root/userGetInfo", localStorage.getItem('jwt'))
      await store.dispatch('root/profileGetConsultantProfile', state.userInfo.id)

      if(state.profileConsultantProfile.topicCategoryName == '학업'){
        const targetId = 'topic_div_1'
        const topic_tag = document.getElementById(targetId)
        topic_tag.setAttribute('data-state', 'active')
      } else if (state.profileConsultantProfile.topicCategoryName == '가정') {
        const targetId = 'topic_div_2'
        const topic_tag = document.getElementById(targetId)
        topic_tag.setAttribute('data-state', 'active')
      } else if (state.profileConsultantProfile.topicCategoryName == '취업') {
        const targetId = 'topic_div_3'
        const topic_tag = document.getElementById(targetId)
        topic_tag.setAttribute('data-state', 'active')
      } else if (state.profileConsultantProfile.topicCategoryName == '진로') {
        const targetId = 'topic_div_4'
        const topic_tag = document.getElementById(targetId)
        topic_tag.setAttribute('data-state', 'active')
      } else if (state.profileConsultantProfile.topicCategoryName == '연애') {
        const targetId = 'topic_div_5'
        const topic_tag = document.getElementById(targetId)
        topic_tag.setAttribute('data-state', 'active')
      } else if (state.profileConsultantProfile.topicCategoryName == '결혼') {
        const targetId = 'topic_div_6'
        const topic_tag = document.getElementById(targetId)
        topic_tag.setAttribute('data-state', 'active')
      }
    }
    
    const clickProfileModifyTopicCategory = async function (topicCategoryId) {
      await store.dispatch("root/profileModifyTopicCategory", {
        id: props.userInfo.id,
        topicCategoryId: topicCategoryId,
      })
      await store.dispatch("root/userGetInfo", localStorage.getItem('jwt'))
      await store.dispatch('root/profileGetConsultantProfile', state.userInfo.id)
    }

    const clickProfileModifyDescription = async function () {
      await store.dispatch("root/profileModifyDescription", {
        user_id: props.userInfo.id,
        description: consultant.description
      })
      await store.dispatch("root/userGetInfo", localStorage.getItem('jwt'))
      await store.dispatch('root/profileGetConsultantProfile', state.userInfo.id)
    }

    return {
      state,
      consultant,
      onMounted,
      clickSearchList,
      clickSelect,
      clickCreateConsultant,
      clickProfileModifyTopicCategory,
      clickProfileModifyDescription
    };
  },
}
</script>

<style scoped lang="scss">
// 전체 틀
@import url("https://fonts.googleapis.com/css?family=Poppins:400,500,600,700&display=swap");

.front__text-hover {
  position: relative;
  font-size: 15px;
  color: #bbd2f9;
  backface-visibility: hidden;

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
  width: 7%;
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
  position: relative;
  // transform:  translate(-50%,50%);
  background: white;
  height: 10%;
  width: 30%;
  // right: 10%;
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
</style>
