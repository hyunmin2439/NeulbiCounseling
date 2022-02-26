<template bgcolor="white">
  <div class="container">
    <div class="row"></div>
    <link
      href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"
      rel="stylesheet"
    />

    <div style="margin-top: 3%">
      <h2 style="font-size: 350%">게시판</h2>
    </div>

    <div
      class="column intro row-hover pos-relative py-4 px-4 mt-4 mb-4 row text-center"
      style="background-color: #eaf1ff"
    >
      <h5 style="color: #6c8093">
        '게시판'는 사용자들 간의 유용한 정보를 공유하고 서로 도움을 받을 수 있는
        소통하는 공간입니다. 다양한 의견과 글을 자유롭게 게시하고 공유해주세요.
      </h5>
    </div>

    <!-- <div class="col-lg-9 mb-3">  -->
    <div class="inner-main-header" style="font-size: 120%">
      <!-- 검색부분 -->
      <span class="dropdown col-lg-3 row text-center op-7">
        <!-- 카테고리별 정렬 -->
        <div class="col px-1" style="cursor: pointer">
          <a @click="clickCommunityList">최신순</a>
        </div>
        <div
          class="col px-1"
          v-for="(type, idx) in ['공감순', '조회순']"
          :key="idx"
          style="cursor: pointer"
        >
          <!-- <span v-for="(type, idx) in (['공감순', '조회순'])" :key="idx"> -->
          <a @click="clickSelectList(idx)">{{ type }}</a>
          <!-- </span>   -->
        </div>
        <div
          class="col px-1"
          @click="goToCreateCommunity"
          style="cursor: pointer"
        >
          <a>등록&nbsp; <i class="ion-edit"></i></a>
        </div>
      </span>

      <span class="col-lg-5"></span>

      <span class="px-1">
        <button
          class="form-control form-control-md dropdown-toggle"
          type="button"
          id="dropdownMenuButton1"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          {{ state.categoryselect }}
        </button>

        <ul
          class="dropdown-menu"
          aria-labelledby="dropdownMenuButton1"
          style="
            min-width: 4rem;
            border: 2px solid #bbd2f9;
            border-radius: 10px;
            padding: 0px;
          "
        >
          <li
            v-for="(category, idx) in state.categories"
            :key="idx"
            @click="clickSearchList(category)"
            style="text-align: center;"
          >
            {{ category.value }}
          </li>
        </ul>
      </span>

      <span class="input-icon input-icon-sm ml-auto col-lg-3 display: flex">
        <input
          type="text"
          v-model="state.searchInput"
          class="form-control form-control-md bg-gray-200 border-gray-200 shadow-none"
          placeholder="찾으시는 게시글이 있으신가요?"
        />
      </span>

      <button
        @click="clickSearchCommunity"
        class="form-control form-control-md col-lg-2"
        id="input"
      >
        찾기
      </button>
    </div>

    <!--게시판 헤더-->
    <div
      class="cardheader row-hover pos-relative py-3 px-3"
      style="background-color: #eaf1ff; font-size: 120%"
    >
      <div class="row align-items-center">
        <div class="col-md-2 mb-3 mb-sm-0">
          <h4>번호</h4>
        </div>
        <div class="col-md-4 mb-3 mb-sm-0">
          <h4>제목</h4>
        </div>
        <div class="col-md-2 mb-3 mb-sm-0">
          <h4>작성자</h4>
        </div>
        <div class="col-md-4 op-7">
          <div class="row text-center op-7">
            <div class="col px-1">
              <i class="ion-ios-heart-outline icon-1x"></i>
              <span class="d-block text-sm">공감수</span>
            </div>
            <div class="col px-1">
              <i class="ion-ios-chatboxes-outline icon-1x"></i>
              <span class="d-block text-sm">댓글수</span>
            </div>
            <div class="col px-1">
              <i class="ion-ios-eye-outline icon-1x"></i>
              <span class="d-block text-sm">조회수</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 게시글 리스트 -->
    <div
      v-for="(community, index) in state.communityList"
      :key="index"
      class="card row-hover pos-relative py-3 px-3"
      id="board-style1"
      style="font-size: 110%"
    >
      <div class="row align-items-center">
        <div class="col-md-2 mb-3 mb-sm-0">
          <h5>{{ community.id }}</h5>
        </div>
        <div
          class="col-md-4 mb-3 mb-sm-0"
          @click="goToCommunityDetail(community.id)"
          style="cursor: pointer"
        >
          <h5>
            {{ community.title }}
          </h5>
        </div>
        <div class="col-md-2 mb-3 mb-sm-0">
          <h5>
            <p class="text-black">{{ community.userNickname }}</p>
          </h5>
        </div>

        <div class="col-md-4 op-7">
          <div class="row text-center op-7">
            <div class="col px-1">
              <span class="d-block text-sm">{{ community.likeCnt }} Votes</span>
            </div>
            <div class="col px-1">
              <span class="d-block text-sm">{{ community.commentCnt }}</span>
            </div>
            <div class="col px-1">
              <span class="d-block text-sm">{{ community.viewCnt }} Views</span>
            </div>
            <!-- <div class="col px-1"> <i class="ion-connection-bars icon-1x"></i> <span class="d-block text-sm">141 Votes</span> </div>
            <div class="col px-1"> <i class="ion-ios-chatboxes-outline icon-1x"></i> <span class="d-block text-sm">122 Replys</span> </div>
            <div class="col px-1"> <i class="ion-ios-eye-outline icon-1x"></i> <span class="d-block text-sm">290 Views</span> </div> -->
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
          v-for="(num, idx) in state.communityLastPageNum"
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
import { computed, reactive, onMounted } from "@vue/runtime-core";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  name: "CommunityView",
  props: {
    userInfo: Object,
  },
  setup(props) {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      userInfo: props.userInfo,
      communityList: computed(() => store.getters["root/communityList"]),
      communityLastPageNum: computed(
        () => store.getters["root/communityLastPageNum"]
      ),
      categories: [
        { key: "description", value: "내용" },
        { key: "title", value: "제목" },
        { key: "nickname", value: "닉네임" },
      ],
      categoryselect: "선택",
      categorykey: "",
      searchInput: "",
      page: 1,
      topic: null,
      pageSearchTopic: "main",
    });

    onMounted(() => {
      if (state.communityLastPageNum != 0) {
        let first = document.getElementById("page0");
        first.classList.add("active");
      }
    });

    const clickCommunityList = async function () {
      state.pageSearchTopic = "main";
      state.page = 1;
      await store.dispatch("root/communityGetCommunityList");
    };

    const clickSearchList = function (category) {
      state.categoryselect = category.value;
      state.categorykey = category.key;
    };

    const clickSearchCommunity = async function () {
      state.pageSearchTopic = "search";
      state.page = 1;
      await store.dispatch("root/communityGetSearchList", {
        key: state.categorykey,
        value: state.searchInput,
      });
    };

    const clickSelectList = async function (idx) {
      state.pageSearchTopic = "topic";
      state.page = 1;
      state.topic = idx === 0 ? "like" : "view";
      await store.dispatch("root/communityGetSelectList", {
        key: idx === 0 ? "like" : "view",
      });
    };

    const checkPage = async function (event) {
      for (var i = 0; i < state.communityLastPageNum; i++) {
        const sub = document.getElementById("page" + i);
        sub.classList.remove("active");
      }
      event.target.classList.add("active");
      state.page = Number(event.target.id.substr(4, 6)) + 1;
      if (state.pageSearchTopic === "main") {
        await store.dispatch("root/communityPageSearch", {
          size: 8,
          page: state.page,
        });
      } else if (state.pageSearchTopic === "topic") {
        await store.dispatch("root/communityTopicPageSearch", {
          topic: state.topic,
          size: 8,
          page: state.page,
        });
      } else if (state.pageSearchTopic === "search") {
        await store.dispatch("root/communitySearchPageSearch", {
          key: state.categorykey,
          value: state.searchInput,
          size: 8,
          page: state.page,
        });
      }
    };

    const goToCommunityDetail = async function (community_id) {
      await store.dispatch("root/communityGetDetail", {
        user_id: state.userInfo.id,
        community_id: community_id,
      });
      await router.push({
        name: "CommunityDetail",
        params: {
          community_id: community_id,
        },
      });
    };

    const goToCreateCommunity = function () {
      router.push({ name: "CommunityCreate" });
    };

    return {
      state,
      onMounted,
      clickCommunityList,
      goToCommunityDetail,
      clickSelectList,
      clickSearchList,
      clickSearchCommunity,
      checkPage,
      goToCreateCommunity,
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
  background-color: #eceff3;
  background-clip: border-box;
  border-top: 2px solid black;
  border-bottom: 2px solid rgba(0, 0, 0, 0.125);
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
  background-color: #eceff3;
}

.custom-form-control {
  color: black;
  font-weight: bold;
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

.form-control {
  // color: red;
  font-weight: bold;
  display: block;
  width: 100%;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  color: #23adad;
  background-color: white;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}
</style>
