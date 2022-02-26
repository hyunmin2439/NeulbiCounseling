<template bgcolor="white">
  <div class="container">
    <div class="row"></div>
    <link
      href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"
      rel="stylesheet"
    />

    <div style="margin-top: 3%">
      <h2 style="font-size: 350%">Q&amp;A</h2>
    </div>

    <div
      class="column intro row-hover pos-relative py-4 px-4 mt-4 mb-4 row text-center"
      style="background-color: #eaf1ff"
    >
      <h5 style="color: #6c8093">
        'Q&amp;A'는 궁금증을 해소하는 공간입니다. 운영자에게 궁금한 점이나
        제시할 사항이 있다면 자유롭게 작성해주세요.
      </h5>
    </div>

    <div class="inner-main-header" style="font-size: 120%">
      <!-- 검색부분 -->
      <span class="dropdown col-lg-3 row text-center op-7">
        <!-- 카테고리별 정렬 -->
        <div class="col px-1" @click="goToCreateQna" style="cursor: pointer">
          <a>등록&nbsp; <i class="ion-edit"></i></a>
        </div>
        <div class="col px-1" style="cursor: pointer"></div>
        <div
          class="col px-1"
          v-for="(type, idx) in ['공감순', '조회순']"
          :key="idx"
          style="cursor: pointer"
        ></div>
      </span>

      <span class="col-lg-5"></span>

      <span class="px-1"></span>

      <span class="input-icon input-icon-sm ml-auto col-lg-3 display: flex">
        <input
          type="text"
          v-model="state.searchWord"
          class="form-control form-control-md bg-gray-200 border-gray-200 shadow-none"
          placeholder="찾으시는 게시글이 있으신가요?"
        />
      </span>

      <button
        @click="clickSearchQna"
        class="form-control form-control-md col-lg-2"
        id="input"
      >
        찾기
      </button>
    </div>

    <!-- 수정 이전 코드 -->
    <!-- <div class="inner-main-header d-flex justify-content-between">
      <button
        @click="goToCreateQna"
        class="form-control form-control-md col-lg-2"
        id="input"
      >
        등록
      </button>
      <div style="display: flex; justify-content: around;">
        <span class="input-icon input-icon-sm ml-auto" style="width: 100%;">
          <input type="text" v-model="state.searchWord" class="form-control form-control-md bg-gray-200 border-gray-200 shadow-none" placeholder="찾으시는 게시글이 있으신가요?" />
        </span>
        <button @click="clickSearchQna" class="form-control form-control-md col-lg-2" id="input">찾기</button>
      </div>
    </div> -->

    <!--게시판 헤더-->
    <div
      class="cardheader row-hover pos-relative py-3 px-3"
      style="background-color: #eaf1ff"
    >
      <div class="row align-items-center">
        <div class="col-md-2 mb-3 mb-sm-0">
          <h4></h4>
        </div>
        <div class="col-md-5 mb-3 mb-sm-0">
          <h4>제목</h4>
        </div>
        <div class="col-md-1 mb-3 mb-sm-0">
          <h4>상태</h4>
        </div>
        <div class="col-md-2 mb-3 mb-sm-0">
          <h4>작성자</h4>
        </div>
        <div class="col-md-2 mb-3 mb-sm-0">
          <h4>작성일</h4>
        </div>
      </div>
    </div>

    <div v-for="(qna, idx) in state.qnaList" :key="idx" style="font-size: 110%">
      <!-- QnA 리스트 -->
      <div class="card row-hover pos-relative py-3 px-3" id="board-style1">
        <div class="row align-items-center">
          <div class="d-flex col-md-1 mb-3 mb-sm-0" style="color: #3a6bff">
            <button
              v-if="
                state.userInfo.role === 'ADMIN' ||
                state.userInfo.nickname == qna.userNickname
              "
              @click="goToQnaDetail(qna)"
              class="form-control form-control-md col-lg-2"
              id="input"
              style="font-size: 12px; margin-right: 50px"
            >
              답글관리
            </button>
          </div>
          <div class="d-flex col-md-1 mb-3 mb-sm-0" style="color: #3a6bff">
            <h5 style="margin-left: 15px">Q</h5>
          </div>
          <div
            class="col-md-5 mb-3 mb-sm-0"
            style="text-align: left; cursor: pointer"
            @click="showQnaAnswer(qna, idx)"
          >
            <h5>
              <i
                v-if="qna.rocked"
                class="fas fa-lock"
                style="color: #f65566"
              ></i>
              <i v-else class="fas fa-lock-open" style="color: gold"></i>
              &nbsp;&nbsp;
              {{ qna.title }}
            </h5>
          </div>

          <div class="d-flex col-md-1 mb-3 mb-sm-0">
            <span
              class="form-control form-control-md col-lg-2"
              id="input"
              style="font-size: 12px"
              v-if="qna.answered"
            >
              답변완료
            </span>
            <span
              class="form-control form-control-md col-lg-2"
              id="input"
              style="font-size: 12px"
              v-else
            >
              답변대기
            </span>
          </div>
          <div class="col-md-2 mb-3 mb-sm-0">
            <h5>
              <p class="text-black">{{ qna.userNickname }}</p>
            </h5>
          </div>

          <div class="col-md-2 mb-3 mb-sm-0">
            <h5>
              {{ qna.date.substr(0, 10) }}
            </h5>
          </div>
        </div>
      </div>

      <!-- QnA 댓글 -->
      <div
        name="qnaAnswers"
        style="
          font-size: 110%;
          background-color: rgb(250, 250, 250);
          display: none;
        "
        class="card row-hover pos-relative py-3 px-3"
        id="board-style1"
      >
        <div class="row align-items-center">
          <div class="col-md-1 mb-3 mb-sm-0" style="color: #3a6bff">
            <h5></h5>
          </div>
          <div class="d-flex col-md-1 mb-3 mb-sm-0" style="color: #3a6bff">
            <h5 style="margin-left: 15px">A</h5>
          </div>
          <div class="col-md-8 mb-3 mb-sm-0" style="text-align: left">
            <h5 v-if="state.qnaAnswerList[0]">
              <div>
                {{ state.qnaDetail.description }}
              </div>
              <br />
              <div>
                <i class="fas fa-arrow-right"></i>&nbsp;&nbsp;&nbsp;
                {{ state.qnaAnswerList[0].description }}
              </div>
            </h5>
          </div>
          <div class="col-md-2 mb-3 mb-sm-0">
            <h5 v-if="state.qnaAnswerList[0]">
              {{ state.qnaAnswerList[0].date.substr(0, 10) }}
            </h5>
          </div>
        </div>
      </div>
    </div>

    <!--페이지-->
    <div id="app" class="pagecontainer">
      <ul class="page">
        <li class="page__btn me-4" @click="checkPage($event)">
          <span class="material-icons">◀</span>
        </li>
        <li
          class="page__numbers"
          :id="'page' + idx"
          v-for="(num, idx) in state.qnaLastPageNum"
          :key="idx"
          @click="checkPage($event)"
        >
          {{ num }}
        </li>
        <li class="page__btn ms-4">
          <span class="material-icons" @click="checkPage($event)">▶</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  name: "QnaView",
  props: {
    userInfo: Object,
  },
  setup(props) {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      userInfo: props.userInfo,
      qnaList: computed(() => store.getters["root/qnaList"]),
      qnaLastPageNum: computed(() => store.getters["root/qnaLastPageNum"]),
      qnaAnswerList: computed(() => store.getters["root/qnaAnswerList"]),
      qnaDetail: computed(() => store.getters["root/qnaDetail"]),
      searchWord: "",
      page: 1,
      pageSearchTopic: "main",
    });

    onMounted(async () => {
      if (state.qnaLastPageNum != 0) {
        let first = document.getElementById("page0");
        first.classList.add("active");
      }
    });

    const clickSearchQna = async function () {
      for (var i = 0; i < state.qnaList.length; i++) {
        document.getElementsByName("qnaAnswers")[i].style.display = "none";
      }
      state.page = 1;
      state.pageSearchTopic = "search";
      await store.dispatch("root/qnaGetSearchQuestionList", {
        search_word: state.searchWord,
        user_id: state.userInfo.id,
      });
    };

    const clickOnMounted = async function () {
      state.page = 1;
      state.pageSearchTopic = "main";
      await store.dispatch("root/qnaGetQuestionList", state.userInfo.id);
    };

    const checkPage = async function (event) {
      for (var j = 0; j < state.qnaList.length; j++) {
        document.getElementsByName("qnaAnswers")[j].style.display = "none";
      }
      for (var i = 0; i < state.qnaLastPageNum; i++) {
        const sub = document.getElementById("page" + i);
        sub.classList.remove("active");
      }
      event.target.classList.add("active");
      state.page = Number(event.target.id.substr(4, 6)) + 1;
      if (state.pageSearchTopic === "main") {
        await store.dispatch("root/qnaPageSearch", {
          user_id: state.userInfo.id,
          size: 10,
          page: state.page,
        });
      } else if (state.pageSearchTopic === "topic") {
        await store.dispatch("root/qnaSearchPageSearch", {
          search_word: state.searchWord,
          user_id: state.userInfo.id,
          size: 10,
          page: state.page,
        });
      }
    };

    const goToQnaDetail = async function (qna) {
      for (var i = 0; i < state.qnaList.length; i++) {
        document.getElementsByName("qnaAnswers")[i].style.display = "none";
      }
      await store.dispatch("root/qnaGetQuestionDetail", {
        qna_id: qna.qnaId,
        user_id: state.userInfo.id,
      });
      await router.push({
        name: "QnaDetail",
        params: {
          qna_id: qna.qnaId,
        },
      });
    };

    const showQnaAnswer = async function (qna, idx) {
      if (
        document.getElementsByName("qnaAnswers")[idx].style.display == "block"
      ) {
        document.getElementsByName("qnaAnswers")[idx].style.display = "none";
        return;
      }
      for (var i = 0; i < state.qnaList.length; i++) {
        document.getElementsByName("qnaAnswers")[i].style.display = "none";
      }
      if (!qna.rocked) {
        await store.dispatch("root/qnaGetQuestionDetail", {
          qna_id: qna.qnaId,
          user_id: state.userInfo.id,
        });
        if (state.qnaAnswerList[0])
          document.getElementsByName("qnaAnswers")[idx].style.display = "block";
      } else {
        alert("비공개된 글입니다.");
      }
    };

    const goToNotice = function () {
      router.push({ name: "Notice" });
    };

    const goToCreateQna = async function () {
      router.push({ name: "QnaCreate" });
    };

    return {
      state,
      onMounted,
      clickSearchQna,
      clickOnMounted,
      checkPage,
      goToQnaDetail,
      goToNotice,
      goToCreateQna,
      showQnaAnswer,
    };
  },
};
</script>

<style scoped lang="scss">
body {
  margin-top: 20px;
  background: #eee;
  color: #708090;
}
.icon-1x {
  font-size: 24px !important;
}
a {
  text-decoration: none;
}
.text-primary,
a.text-primary:focus,
a.text-primary:hover {
  color: #1f4b97 !important;
}
.text-black,
.text-hover-black:hover {
  color: #000 !important;
}
.font-weight-bold {
  font-weight: 700 !important;
}

.inner-main-header {
  height: 6rem;
  border-bottom: 1px solid #cbd5e0;
  display: flex;
  align-items: center;
  padding: 0 1rem;
  flex-shrink: 0;
  justify-content: flex-end;
}

#input {
  margin-left: 10px;
  width: auto;
  display: inline;
}

#dropdownMenuButton1 {
  background-color: white;
}

a:hover {
  font-weight: bold;
  font-size: large;
}

.cardheader {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fafbfc;
  background-clip: border-box;
  border-top: 2px solid black;
  border-bottom: 2px solid rgba(0, 0, 0, 0.125);
  /* border-radius: 0.25rem; */
}

#board-style1 {
  border-top: 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.125);
  border-left: 0;
  border-right: 0;
}

#board-style1:hover {
  background-color: snow;
}

.intro {
  background-color: #fafbfc;
}
/// pagination

ul {
  list-style-type: none;
}

.items-list {
  max-width: 90vw;
  margin: 2rem;
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  grid-gap: 3rem;
  justify-content: center;
  align-content: center;

  @media only screen and (max-width: 600px) {
    grid-template-columns: repeat(2, 1fr);
  }
}

.item {
  width: 10rem;
  height: 10rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #2d4848;
  cursor: pointer;

  span {
    background: #ffffff;
    box-shadow: 0 0.8rem 2rem rgba(#5a6181, 0.05);
    border-radius: 0.6rem;
    padding: 2rem;
    font-size: 3rem;
    transition: all 0.3s ease;
  }

  &:hover {
    span {
      transform: scale(1.2);
      color: #23adad;
    }
  }

  p {
    font-size: 1.2rem;
    margin-top: 1rem;
    color: #23adade1;
  }
}

.page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 5rem;
  margin: 3rem;
  border-radius: 0.6rem;
  background: #ffffff;
  box-shadow: 0 0.8rem 2rem rgba(#5a6181, 0.05);

  &__numbers,
  &__btn,
  &__dots {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0.8rem;
    font-size: 1.4rem;
    cursor: pointer;
  }

  &__dots {
    width: 2.6rem;
    height: 2.6rem;
    color: #23adade1;
    cursor: initial;
  }

  &__numbers {
    width: 2.6rem;
    height: 2.6rem;
    border-radius: 0.4rem;

    &:hover {
      color: #23adad;
    }

    &.active {
      color: #ffffff;
      background: #23adad;
      font-weight: 600;
      border: 1px solid #23adad;
    }
  }

  &__btn {
    color: #23adade1;
    pointer-events: none;

    &.active {
      color: #2d4848;
      pointer-events: initial;

      &:hover {
        color: #23adad;
      }
    }
  }
}

.h1,
.h2,
.h3,
.h4,
.h5,
.h6,
h1,
h2,
h3,
h4,
h5,
h6,
p {
  margin-top: 0.25rem;
  margin-bottom: 0.25rem;
  font-weight: 500;
  line-height: 1.2;
}
</style>
