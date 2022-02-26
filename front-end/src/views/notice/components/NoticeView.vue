<template bgcolor="white">
  <div class="container">
    <div class="row"></div>
    <link
      href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"
      rel="stylesheet"
    />

    <div style="margin-top: 3%">
      <h2 style="font-size: 350%">공지사항</h2>
    </div>

    <div
      class="column intro row-hover pos-relative py-4 px-4 mt-4 mb-4 row text-center"
      style="background-color: #eaf1ff"
    >
      <h5 style="color: #6c8093">
        '공지사항'는 운영자가 사용자들에게 알리고자 하는 내용이 작성된
        공간입니다.
      </h5>
    </div>

    <!-- <div class="col-lg-9 mb-3">  -->
    <div class="inner-main-header" style="font-size: 120%">
      <button
        v-if="state.userInfo.role === 'ADMIN'"
        @click="goToCreateNotice"
        class="form-control form-control-md col-lg-2"
        id="input"
      >
        등록
      </button>
    </div>

    <div
      class="cardheader row-hover pos-relative py-3 px-3"
      style="background-color: #eaf1ff"
    >
      <div class="row align-items-center">
        <div class="col-md-2 mb-3 mb-sm-0">
          <h4>번호</h4>
        </div>
        <div class="col-md-6 mb-3 mb-sm-0">
          <h4>제목</h4>
        </div>
        <div class="col-md-2 mb-3 mb-sm-0">
          <h4>작성자</h4>
        </div>
        <div class="col-md-2 mb-3 mb-sm-0">
          <h4>작성일</h4>
        </div>
      </div>
    </div>
    <!-- 게시글 리스트 -->
    <div
      v-for="(notice, index) in state.noticeList"
      :key="index"
      class="card row-hover pos-relative py-3 px-3"
      id="board-style1"
      style="font-size: 110%"
    >
      <div class="row align-items-center">
        <div class="col-md-2 mb-3 mb-sm-0">
          <h5>{{ notice.noticeId }}</h5>
        </div>
        <div
          class="col-md-6 mb-3 mb-sm-0"
          @click="goToNoticeDetail(notice.noticeId)"
          style="text-align: left; cursor: pointer"
        >
          <h5>
            {{ notice.title }}
          </h5>
        </div>
        <div class="col-md-2 mb-3 mb-sm-0">
          <h5>
            <p class="text-black">운영자</p>
          </h5>
        </div>

        <div class="col-md-2 mb-3 mb-sm-0">
          <h5>
            {{ notice.date.substr(0, 10) }}
          </h5>
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
          v-for="(num, idx) in state.noticeLastPageNum"
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
  name: "NoticeView",
  props: {
    userInfo: Object,
  },
  setup(props) {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      userInfo: props.userInfo,
      noticeList: computed(() => store.getters["root/noticeList"]),
      noticeLastPageNum: computed(
        () => store.getters["root/noticeLastPageNum"]
      ),
      page: 1,
    });

    onMounted(async () => {
      if (state.noticeLastPageNum != 0) {
        let first = document.getElementById("page0");
        first.classList.add("active");
      }
    });

    const checkPage = async function (event) {
      for (var i = 0; i < state.noticeLastPageNum; i++) {
        const sub = document.getElementById("page" + i);
        sub.classList.remove("active");
      }
      event.target.classList.add("active");
      state.page = Number(event.target.id.substr(4, 6)) + 1;
      await store.dispatch("root/noticePageSearch", {
        size: 10,
        page: state.page,
      });
    };

    const goToNoticeDetail = async function (noticeId) {
      await store.dispatch("root/noticeGetNoticeDetail", noticeId);
      await router.push({
        name: "NoticeDetail",
        params: {
          notice_id: noticeId,
        },
      });
    };

    const goToCreateNotice = async function () {
      router.push({ name: "NoticeCreate" });
    };

    return { state, onMounted, checkPage, goToNoticeDetail, goToCreateNotice };
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

textarea {
  width: 100%;
  height: 150px;
  border: none;
  resize: none;
}
</style>
