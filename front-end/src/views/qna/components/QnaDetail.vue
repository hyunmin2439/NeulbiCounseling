<template>
  <main-header></main-header>
  <div class="container">
    <link
      href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"
      rel="stylesheet"
    />
    <!--디테일 헤더-->
    <div class="detailheader row-hover pos-relative py-3 px-3 mt-5 row-lg-4">
      <div class="row align-items-center">
        <div class="col-md-12" id="leftalign">
          <input
            type="text"
            v-model="data.title"
            :placeholder="state.qnaDetail.title"
            :readonly="state.userInfo.nickname != state.qnaDetail.userNickname"
            style="border: none; font-size: 28px; width: 900px"
          />
        </div>
      </div>
      <div class="row align-items-center">
        <div class="col-md-8 op-7" id="leftalign">
          <h5>
            {{ state.qnaDetail.userNickname }} |
            {{ state.qnaDetail.date.substr(0, 10) }}
          </h5>
        </div>
        <!-- <div class="col-md-4 op-7" id="leftalign">
          <h5>1999-99-99</h5>
        </div> -->
        <div class="col-md-4 op-7">
          <div class="row">
            <div class="col px-1">
              <i class="fas fa-check"></i>
              <span class="d-block text-sm"></span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- community_detail -->
    <!--디테일 본문-->
    <div
      class="card row-hover pos-relative mt-2 py-3 px-3 row-lg-4"
      id="maintext"
      style="border-bottom: 2px solid rgba(0, 0, 0, 0.125); text-align: left"
    >
      <div>
        <i class="fas fa-question" style="color: #3a6bff; font-size: 25px"></i>
        <br /><br />
        <textarea
          cols="100"
          rows="5"
          v-model="data.description"
          :placeholder="state.qnaDetail.description"
          :readonly="state.userInfo.nickname != state.qnaDetail.userNickname"
          style="border: none; font-size: 20px"
        ></textarea>
      </div>
      <div class="d-flex" style="justify-content: flex-end">
        <button
          @click="goToQna"
          class="form-control form-control-md col-lg-2"
          id="input"
        >
          목록
        </button>
        <button
          @click="clickModifyQna"
          class="form-control form-control-md col-lg-2"
          id="input"
          v-if="state.userInfo.nickname == state.qnaDetail.userNickname"
        >
          수정
        </button>
        <button
          @click="clickDeleteQna"
          class="form-control form-control-md col-lg-2"
          id="input"
          v-if="state.userInfo.nickname == state.qnaDetail.userNickname"
        >
          삭제
        </button>
      </div>
    </div>

    <!-- 답변 -->
    <div
      class="detailbutton row-hover pos-relative py-3 px-3 mt-2 row-lg-4"
      style="text-align: left"
    >
      <qna-answer :userInfo="state.userInfo" :qnaDetail="state.qnaDetail">
      </qna-answer>
    </div>

    <hr />
  </div>
</template>

<script>
import QnaAnswer from "./QnaAnswer.vue";
import MainHeader from "@/views/main/components/MainHeader.vue";

import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  name: "QnaDetail",
  components: {
    QnaAnswer,
    MainHeader,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      qnaDetail: computed(() => store.getters["root/qnaDetail"]),
      userInfo: computed(() => store.getters["root/userInfo"]),
    });

    const data = reactive({
      title: state.qnaDetail.title,
      description: state.qnaDetail.description,
      rocked: state.qnaDetail.isRocked,
    });

    onMounted(() => {
      window.scrollTo(0, 0);
    })

    const clickModifyQna = async function () {
      console.log(data.description, data.title);
      await store.dispatch("root/qnaModifyQuestion", {
        qnaId: state.qnaDetail.qnaId,
        userId: state.userInfo.id,
        title: data.title,
        description: data.description,
        isRocked: data.rocked,
      });
      await store.dispatch("root/qnaGetQuestionDetail", {
        qna_id: state.qnaDetail.qnaId,
        user_id: state.userInfo.id,
      });
    };

    const clickDeleteQna = async function () {
      await store.dispatch("root/qnaDeleteQuestion", {
        qnaId: state.qnaDetail.qnaId,
        userId: state.userInfo.id,
      });
      await store.dispatch("root/qnaGetQuestionList", state.userInfo.id);
      await router.push({ name: "Qna" });
    };

    const goToQna = function () {
      router.push({ name: "Qna" });
    };

    return { state, data, onMounted, clickModifyQna, clickDeleteQna, goToQna };
  },
};
</script>

<style scoped lang="scss">
#input {
  margin-left: 10px;
  width: auto;
  display: inline;
}

.detailheader {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: white;
  background-clip: border-box;
  border-top: 2px solid black;
  border-bottom: 2px solid rgba(0, 0, 0, 0.125);
  /* border-radius: 0.25rem; */
}

#maintext {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: white;
  background-clip: border-box;
  height: 300px;
  border-top: 0;
  border-left: 0;
  border-right: 0;
  border-bottom: 0;
}

.detailbutton {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: white;
  background-clip: border-box;
  border-top: 0;
  border-bottom: 0;
  border-left: 0;
  border-right: 0;
}

#leftalign {
  text-align: left;
}

textarea {
  width: 100%;
  height: 150px;
  border: none;
  resize: none;
}
</style>
