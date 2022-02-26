<template>
  <h3>Pagination</h3>
  <button
    type="button"
    class="btn btn-light me-md-2"
    :disabled="state.pageNum == 1"
    @click="clickPrevPage"
  >
    이전
  </button>
  <span
    >{{ state.pageNum }}/{{
      state.totalNum % 6 == 0
        ? state.totalNum / 6
        : parseInt(state.totalNum / 6) + 1
    }}페이지</span
  >
  <button
    type="button"
    class="btn btn-light me-md-2"
    :disabled="state.pageNum * 6 >= state.totalNum"
    @click="clickNextPage"
  >
    다음
  </button>
</template>

<script>
import { reactive, computed } from "vue";
import { useStore } from "vuex";
export default {
  name: "AdvicePagination",
  setup() {
    const store = useStore();
    const state = reactive({
      pageNum: computed(() => store.getters["root/advicePageNum"]),
      totalNum: computed(() => store.getters["root/adviceTotalLength"]),
    });

    store.commit("root/SET_ADVICE_PAGENUM", 1);

    const clickPrevPage = function () {
      store.commit("root/SET_ADVICE_PAGENUM", state.pageNum - 1);
      store.commit("root/CLEAR_ADVICE_VIEW");
      store.commit("root/SET_ADVICE_PAGINATION", {
        pageNum: state.pageNum,
        totalNum: state.totalNum,
      });
    };

    const clickNextPage = function () {
      store.commit("root/SET_ADVICE_PAGENUM", state.pageNum + 1);
      store.commit("root/CLEAR_ADVICE_VIEW");
      store.commit("root/SET_ADVICE_PAGINATION", {
        pageNum: state.pageNum,
        totalNum: state.totalNum,
      });
    };

    return { state, clickPrevPage, clickNextPage };
  },
};
</script>

<style></style>
