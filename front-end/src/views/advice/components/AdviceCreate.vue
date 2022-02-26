<template>
  <div v-if="state.bool == true">
    <!-- 토픽정하기 -->
    <div class="dropdown">
      <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
        {{ state.category }}
      </button>
      <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
        <li v-for="(category,idx) in state.categories" :key="idx" @click="clickSearchList(category)">{{ category.value }}</li>
      </ul>
    </div>
    <br>
    <!-- 설명적기 -->
    <div class="input-group mb-3">
      <span class="input-group-text">설명글</span>
      <textarea
        class="form-control"
        id="description"
        cols="30"
        rows="3"
        v-model="state.description"
      ></textarea>
    </div>
    <br>
    <!-- 마지막 버튼 -->
    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
      <button
        type="button"
        class="btn btn-light me-md-2"
        @click="clickCreateConsultant"
      >
        상담가 신청
      </button>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import { useStore } from "vuex";
export default {
  name: "AdviceCreate",
  props: {
    userInfo: Object,
  },
  setup(props) {
    const store = useStore();
    const state = reactive({
      bool: false,
      userInfo: props.userInfo,
      description: "",
      category: "select",
      topic: "",
      categories: [
        { value: "학업", number: "1" },
        { value: "취업", number: "2" },
        { value: "이직", number: "3" },
      ],
    })

    const clickSearchList = function (category) {
      state.category = category.value
      state.topic = category.number
    }

    const clickCreateConsultant = async function () {
      await store.dispatch("root/adviceCreateConsultant", {
        description: state.description,
        topicCategoryId: state.topic,
        userId: state.userInfo.id,
      })
      await store.dispatch("root/adviceGetConsultantList", state.userInfo.id)
      await store.dispatch("root/adviceGetRankList")
    }

    return { state, clickSearchList, clickCreateConsultant };
  },
};
</script>

<style></style>
