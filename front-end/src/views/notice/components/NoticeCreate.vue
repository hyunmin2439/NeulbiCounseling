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
            v-model="state.title"
            placeholder="제목을 작성해주세요."
            style="border: none; font-size: 28px; width: 900px"
          />
        </div>
      </div>
      <div class="row align-items-center">
        <div class="col-md-8 op-7" id="leftalign">
          <h5>{{ state.userInfo.nickname }} | {{ state.today }}</h5>
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
        border-bottom: 2px solid rgba(0, 0, 0, 0.125);
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
          rows="5"
          v-model="state.description"
          placeholder="글을 작성해주세요."
          style="border: none; font-size: 20px"
        ></textarea>
      </div>
      <div class="d-flex" style="justify-content: flex-end">
        <button
          @click="goToNotice"
          class="form-control form-control-md col-lg-2"
          id="input"
        >
          취소
        </button>
        <button
          @click="clickCreateNotice"
          class="form-control form-control-md col-lg-2"
          id="input"
        >
          등록
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
  name: "NoticeCreate",
  components: {
    MainHeader,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      userInfo: computed(() => store.getters["root/userInfo"]),
      title: "",
      description: "",
      today: "",
    });

    onMounted(() => {
      window.scrollTo(0, 0);
      const now = new Date();
      const year = now.getFullYear();
      const month =
        now.getMonth() + 1 < 10
          ? "0" + (now.getMonth() + 1)
          : now.getMonth() + 1;
      const date = now.getDate();
      state.today = year + "-" + month + "-" + date;
    });

    const clickCreateNotice = async function () {
      if (state.userInfo.role === "ADMIN") {
        await store.dispatch("root/noticeCreateNotice", {
          userId: state.userInfo.id,
          title: state.title,
          description: state.description,
        });
        await store.dispatch("root/noticeGetNoticeList");
        await router.push({ name: "Notice" });
      } else {
        alert("관리자가 아닙니다!");
      }
    };

    const goToNotice = function () {
      router.push({ name: "Notice" });
    };

    return { state, clickCreateNotice, goToNotice };
  },
};
</script>

<style scoped lang="scss">
@supports (-webkit-appearance: none) or (-moz-appearance: none) {
  input[type="checkbox"] {
    --active: #275efe;
    --active-inner: #fff;
    --focus: 2px rgba(39, 94, 254, 0.3);
    --border: #bbc1e1;
    --border-hover: #275efe;
    --background: #fff;
    --disabled: #f6f8ff;
    --disabled-inner: #e1e6f9;
    -webkit-appearance: none;
    -moz-appearance: none;
    height: 21px;
    outline: none;
    display: inline-block;
    vertical-align: top;
    position: relative;
    margin: 0;
    cursor: pointer;
    border: 1px solid var(--bc, var(--border));
    background: var(--b, var(--background));
    transition: background 0.3s, border-color 0.3s, box-shadow 0.2s;
    &:after {
      content: "";
      display: block;
      left: 0;
      top: 0;
      position: absolute;
      transition: transform var(--d-t, 0.3s) var(--d-t-e, ease),
        opacity var(--d-o, 0.2s);
    }
    &:checked {
      --b: var(--active);
      --bc: var(--active);
      --d-o: 0.3s;
      --d-t: 0.6s;
      --d-t-e: cubic-bezier(0.2, 0.85, 0.32, 1.2);
    }
    &:hover {
      &:not(:checked) {
        &:not(:disabled) {
          --bc: var(--border-hover);
        }
      }
    }
    &:focus {
      box-shadow: 0 0 0 var(--focus);
    }
    &:not(.switch) {
      width: 21px;
      &:after {
        opacity: var(--o, 0);
      }
      &:checked {
        --o: 1;
      }
    }
    & + label {
      font-size: 14px;
      line-height: 21px;
      display: inline-block;
      vertical-align: top;
      cursor: pointer;
      margin-left: 4px;
    }
  }
  input[type="checkbox"] {
    &:not(.switch) {
      border-radius: 7px;
      &:after {
        width: 5px;
        height: 9px;
        border: 2px solid var(--active-inner);
        border-top: 0;
        border-left: 0;
        left: 7px;
        top: 4px;
        transform: rotate(var(--r, 20deg));
      }
      &:checked {
        --r: 43deg;
      }
    }
    &.switch {
      width: 38px;
      border-radius: 11px;
      &:after {
        left: 2px;
        top: 2px;
        border-radius: 50%;
        width: 15px;
        height: 15px;
        background: var(--ab, var(--border));
        transform: translateX(var(--x, 0));
      }
      &:checked {
        --ab: var(--active-inner);
        --x: 17px;
      }
    }
  }
}

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
