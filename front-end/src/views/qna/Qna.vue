<template>
  <main-header></main-header>
  <qna-view :userInfo="state.userInfo"></qna-view>
</template>

<script>
import QnaView from "./components/QnaView.vue";
import MainHeader from "../main/components/MainHeader.vue";
import { onMounted, reactive, computed } from "vue";
import { useStore } from "vuex";

export default {
  name: "Qna",
  components: {
    QnaView,
    MainHeader
  },
  setup() {
    const store = useStore();
    const state = reactive({
      userInfo: computed(() => store.getters["root/userInfo"]),
    });

    onMounted(async () => {
      window.scrollTo(0, 0);
      await store.dispatch("root/qnaGetQuestionList", state.userInfo.id);
    });

    return { state, onMounted };
  },
};
</script>

<style></style>
