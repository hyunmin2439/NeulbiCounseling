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
            style="border: none; font-size: 25px; width: 900px"
            :readonly="state.userInfo.role != 'ADMIN'"
          />
        </div>
      </div>
      <div class="row align-items-center">
        <div class="col-md-8 op-7" id="leftalign">
          <h5>
            운영자 |
            {{ state.noticeDetail.date.substr(0, 10) }}
          </h5>
        </div>
        <!-- <div class="col-md-4 op-7" id="leftalign">
          <h5>1999-99-99</h5>
        </div> -->
        <div class="col-md-4 op-7">
          <div class="row">
            <div class="col px-1">
              <span class="d-block text-sm"></span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!--디테일 본문-->
    <div
      class="d-flex card row-hover pos-relative mt-2 py-3 px-3 row-lg-4"
      id="maintext"
      style="
        justify-content: space-between;
        text-align: left;
      "
    >
      <div>
        <i
          class="fas fa-sticky-note"
          style="color: #3a6bff; font-size: 25px"
        ></i>
        <br /><br />
        <textarea
          cols="100"
          v-model="data.description"
          style="border: none; font-size: 20px; height: 470px;"
          :readonly="state.userInfo.role != 'ADMIN'"
        ></textarea>
      </div>
      <div class="d-flex" style="justify-content: flex-end">
        <button
          @click="goToNotice"
          class="form-control form-control-md col-lg-2"
          id="input"
        >
          목록
        </button>
        <button
          @click="clickModifyNotice"
          class="form-control form-control-md col-lg-2"
          id="input"
          v-if="state.userInfo.role == 'ADMIN'"
        >
          수정
        </button>
        <button
          @click="clickDeleteNotice"
          class="form-control form-control-md col-lg-2"
          id="input"
          v-if="state.userInfo.role == 'ADMIN'"
        >
          삭제
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import MainHeader from "@/views/main/components/MainHeader.vue";

import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  name: "NoticeDetail",
  components: {
    MainHeader,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      noticeDetail: computed(() => store.getters["root/noticeDetail"]),
      userInfo: computed(() => store.getters["root/userInfo"]),
    });
    const data = reactive({
      title: state.noticeDetail.title,
      description: state.noticeDetail.description,
    });

    onMounted(() => {
      window.scrollTo(0, 0);
    })

    const clickModifyNotice = async function () {
      await store.dispatch("root/noticeModifyNotice", {
        noticeId: state.noticeDetail.noticeId,
        userId: state.userInfo.id,
        title: data.title,
        description: data.description,
      });
      await store.dispatch(
        "root/noticeGetNoticeDetail",
        state.noticeDetail.noticeId
      );
    };

    const clickDeleteNotice = async function () {
      await store.dispatch("root/noticeDeleteNotice", {
        noticeId: state.noticeDetail.noticeId,
        userId: state.userInfo.id,
      });
      await store.dispatch("root/noticeGetNoticeList");
      await router.push({ name: "Notice" });
    };

    const goToNotice = function () {
      router.push({ name: "Notice" });
    };

    return { state, data, onMounted, clickModifyNotice, clickDeleteNotice, goToNotice };
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
