<template>
  <div style="background-color: rgb(225 236 255); height: 100vh">
    <div id="main-container" class="container">
      <!-- meeting 입장 초기 화면 -->
      <div id="review">
        <div
          id="review-dialog"
          class="jumbotron vertical-center"
          style="padding-top: 50px"
        >
          <div
            class="d-flex justify-content-center"
            style="
              border-bottom: 3px solid #a6c0fe;
              padding-bottom: 20px;
              font-family: Binggrae;
            "
          >
            <h1 style="color: #333333; font-family: Binggrae">
              미팅은 어떠셨나요?
            </h1>
          </div>
          <br />
          <div
            class="form-group"
            style="color: #333333; margin-top: 20%; font-family: Binggrae"
          >
            <div style="font-family: Binggrae">
              <h1
                data-bs-toggle="modal"
                data-bs-target="#exampleModal2"
                id="input"
                style="font-family: Binggrae"
              >
                신고하기
              </h1>
              <h1
                id="input"
                @click="goToHome"
                style="
                  margin-left: 20%;
                  margin-right: 5%;
                  font-family: Binggrae;
                "
              >
                홈으로
              </h1>
            </div>
            <!-- 신고 모달 -->
            <div
              class="modal fade"
              id="exampleModal2"
              tabindex="-1"
              aria-labelledby="exampleModalLabel2"
              aria-hidden="true"
            >
              <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                  <div class="modal-header" style="font-family: Binggrae">
                    <h5
                      class="modal-title"
                      id="exampleModalLabel2"
                      style="font-family: Binggrae"
                    >
                      신고하기
                    </h5>
                    <button
                      type="button"
                      class="btn-close"
                      data-bs-dismiss="modal"
                      aria-label="Close"
                    ></button>
                  </div>
                  <div class="modal-body">
                    <form>
                      <div class="row">
                        <div class="col-md-3">
                          <span>
                            <ul style="list-style: none; padding-left: 0px">
                              <li
                                v-for="(
                                  person, idx
                                ) in state.confessionReviewList"
                                :key="idx"
                                @click="clickSelectNickname(person, idx)"
                                id="nickname"
                                name="lis"
                                style="font-family: Binggrae"
                              >
                                {{ person.nickName }}
                              </li>
                            </ul>
                          </span>
                        </div>
                        <div class="col-md-1"></div>
                        <div class="col-md-8">
                          <textarea
                            cols="30"
                            rows="5"
                            v-model="state.reportMsg"
                            placeholder="신고 내용을 작성해주세요."
                            style="font-family: Binggrae"
                          ></textarea>
                        </div>
                      </div>
                    </form>
                  </div>
                  <div class="modal-footer">
                    <button
                      class="custom-form-control-red"
                      type="button"
                      @click="clickReviewReportDetail"
                      style="font-family: Binggrae"
                    >
                      보내기
                    </button>
                    <!-- <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Select</button> -->
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router";

export default {
  name: "ReviewConfession",
  setup() {
    const store = useStore();
    const route = useRoute();
    const router = useRouter();
    const state = reactive({
      confessionReviewList: computed(
        () => store.getters["root/confessionReviewList"]
      ),
      reviewIndex: -1,
      user_id: route.params.user_id,
      meeting_id: route.params.meeting_id,
      reportBool: false,
      reportMsg: "",
      reportNickname: "닉네임",
      reportUserId: "",
    });

    onMounted(() => {
      window.scrollTo(0, 0);
    })

    const clickIsReport = async function (idx) {
      if (state.reviewIndex === -1) {
        state.reviewIndex = idx;
      } else {
        state.reviewIndex = -1;
      }
    };

    const clickReviewReportDetail = function () {
      store.dispatch("root/reportDetail", {
        category: "confession",
        reportUserId: state.reportUserId,
        userId: state.user_id,
        reportMsg: state.reportMsg,
      });
      state.reportMsg = "";
      state.reviewIndex = -1;
      if (document.getElementById("unnickname"))
        document.getElementById("unnickname").id = "nickname";
    };

    const goToHome = function () {
      store.dispatch("root/userGetInfo", localStorage.getItem('jwt'))
      router.push({
        name: "Home",
      });
    };

    const clickSelectNickname = function (person, idx) {
      if (document.getElementById("unnickname"))
        document.getElementById("unnickname").id = "nickname";
      state.reportNickname = person.nickName;
      state.reportUserId = person.userId;
      console.log(idx, state.reportNickname);
      document.getElementsByName("lis")[idx].id = "unnickname";
    };

    return {
      state, onMounted,
      clickIsReport,
      clickReviewReportDetail,
      goToHome,
      clickSelectNickname,
    };
  },
};
</script>

<style scoped lang="scss">
.form-group h1:hover {
  cursor: pointer;
}

textarea {
  width: 100%;
  height: 150px;
  border: none;
  resize: none;
}

#input {
  margin-left: 10px;
  width: auto;
  display: inline;
}

.custom-form-control {
  color: black;
  font-weight: bold;
  display: block;
  width: 100%;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  color: #212529;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.custom-form-control-red {
  // color: red;
  font-weight: bold;
  display: block;
  width: 100%;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  color: red;
  background-color: white;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

#review-dialog {
  margin-left: auto;
  margin-right: auto;
  max-width: 70%;
}

#review-dialog h1 {
  color: #4d4d4d;
  font-weight: bold;
  text-align: center;
}

#main-container {
  padding-bottom: 80px;
  padding-top: 5%;
}

#nickname {
  min-width: 7rem;
  border: 2px solid #bbd2f9;
  border-radius: 30px;
  padding: 0px;
  text-align: center;
  margin-bottom: 5px;
}

#unnickname {
  min-width: 7rem;
  border: 2px solid #bbd2f9;
  border-radius: 30px;
  padding: 0px;
  text-align: center;
  margin-bottom: 5px;
  background-color: #bbd2f9;
  color: white;
}

ul li {
  cursor: pointer;
}
</style>
