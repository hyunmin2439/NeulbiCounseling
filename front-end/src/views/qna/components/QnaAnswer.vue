<template>
  <div>
    <i class="fas fa-arrow-right" style="color: #3a6bff; font-size: 25px"></i>
    <br /><br />
    <textarea
      cols="100"
      rows="5"
      v-model="state.description"
      :placeholder="
        state.description == null
          ? state.description
          : '답글이 아직 작성되지 않았습니다.😢'
      "
      :readonly="state.userInfo.role != 'ADMIN'"
      style="border: none; font-size: 20px"
    ></textarea
    ><br />
  </div>
  <div
    class="d-flex"
    style="justify-content: flex-end"
    v-if="state.userInfo.role == 'ADMIN'"
  >
    <button
      class="form-control form-control-md col-lg-2"
      @click="clickCreateAnswer"
      id="input"
      v-if="!state.qnaAnswerList[0]"
    >
      등록
    </button>
    <button
      class="form-control form-control-md col-lg-2"
      @click="clickModifyAnswer"
      id="input"
      v-if="state.qnaAnswerList[0]"
    >
      수정
    </button>
    <button
      class="form-control form-control-md col-lg-2"
      @click="clickDeleteAnswer"
      id="input"
      v-if="state.qnaAnswerList[0]"
    >
      삭제
    </button>
  </div>
</template>

<script>
import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";

export default {
  name: "QnaAnswer",
  props: {
    userInfo: Object,
    qnaDetail: Object,
  },
  setup(props) {
    const store = useStore();
    const state = reactive({
      userInfo: props.userInfo,
      qnaDetail: props.qnaDetail,
      qnaAnswerList: computed(() => store.getters["root/qnaAnswerList"]),
      description: "",
      answerId: "",
    });

    onMounted(() => {
      if (state.qnaAnswerList[0]) {
        state.description = state.qnaAnswerList[0].description;
        state.answerId = state.qnaAnswerList[0].answerId;
      }
    });

    const clickCreateAnswer = async function () {
      await store.dispatch("root/qnaCreateAnswer", {
        userId: state.userInfo.id,
        qnaId: state.qnaDetail.qnaId,
        description: state.description,
      });
      await store.dispatch("root/qnaGetQuestionDetail", {
        qna_id: state.qnaDetail.qnaId,
        user_id: state.userInfo.id,
      });
      if (state.qnaAnswerList[0]) {
        state.description = state.qnaAnswerList[0].description;
        state.answerId = state.qnaAnswerList[0].answerId;
      }
    };

    const clickModifyAnswer = async function () {
      await store.dispatch("root/qnaModifyAnswer", {
        userId: state.userInfo.id,
        answerId: state.answerId,
        description: state.description,
      });
      await store.dispatch("root/qnaGetQuestionDetail", {
        qna_id: state.qnaDetail.qnaId,
        user_id: state.userInfo.id,
      });
    };

    const clickDeleteAnswer = async function () {
      await store.dispatch("root/qnaDeleteAnswer", {
        userId: state.userInfo.id,
        answerId: state.answerId,
      });
      await store.dispatch("root/qnaGetQuestionDetail", {
        qna_id: state.qnaDetail.qnaId,
        user_id: state.userInfo.id,
      });
      if (state.qnaAnswerList[0]) {
        state.description = state.qnaAnswerList[0].description;
        state.answerId = state.qnaAnswerList[0].answerId;
      } else {
        state.description = "";
        state.answerId = "";
      }
    };

    return { state, clickCreateAnswer, clickModifyAnswer, clickDeleteAnswer };
  },
};
</script>

<style scoped lang="scss">
#input {
  margin-left: 10px;
  width: auto;
  display: inline;
}

textarea {
  width: 100%;
  height: 150px;
  border: none;
  resize: none;
}
</style>
