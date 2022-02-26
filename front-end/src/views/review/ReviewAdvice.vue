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
            style="border-bottom: 3px solid #a6c0fe; padding-bottom: 20px"
          >
            <h1 style="color: #333333; font-family: Binggrae">
              미팅은 어떠셨나요?
            </h1>
          </div>
          <br />
          <div
            class="form-group"
            style="color: #333333; font-family: Binggrae; margin-top: 20%"
          >
            <div>
              <h1
                data-bs-toggle="modal"
                data-bs-target="#exampleModal1"
                id="input"
                style="margin-right: 15%; font-family: Binggrae"
              >
                칭찬하기
              </h1>
              <h1
                data-bs-toggle="modal"
                data-bs-target="#exampleModal2"
                id="input"
                style="margin-right: 15%; font-family: Binggrae"
              >
                신고하기
              </h1>
              <h1
                id="input"
                @click="goToHome"
                style="margin-right: 5%; font-family: Binggrae"
              >
                홈으로
              </h1>
            </div>
            <!-- 칭찬 모달 -->
            <div
              class="modal fade"
              id="exampleModal1"
              tabindex="-1"
              aria-labelledby="exampleModalLabel1"
              aria-hidden="true"
            >
              <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5
                      class="modal-title"
                      id="exampleModalLabel1"
                      style="font-family: Binggrae"
                    >
                      칭찬하기
                    </h5>
                    <button
                      type="button"
                      class="btn-close"
                      data-bs-dismiss="modal"
                      aria-label="Close"
                    ></button>
                  </div>
                  <div class="modal-body">
                    <form
                      name="myform"
                      id="myform"
                      method="post"
                      action="./save"
                    >
                      <fieldset>
                        <!-- <legend>이모지 별점</legend> -->
                        <input
                          type="radio"
                          name="rating"
                          value="10"
                          v-model="state.point"
                          id="rate1"
                        /><label for="rate1">⭐</label>
                        <input
                          type="radio"
                          name="rating"
                          value="8"
                          v-model="state.point"
                          id="rate2"
                        /><label for="rate2">⭐</label>
                        <input
                          type="radio"
                          name="rating"
                          value="6"
                          v-model="state.point"
                          id="rate3"
                        /><label for="rate3">⭐</label>
                        <input
                          type="radio"
                          name="rating"
                          value="4"
                          v-model="state.point"
                          id="rate4"
                        /><label for="rate4">⭐</label>
                        <input
                          type="radio"
                          name="rating"
                          value="2"
                          v-model="state.point"
                          id="rate5"
                        /><label for="rate5">⭐</label>
                      </fieldset>
                    </form>
                    <div>
                      <textarea
                        cols="30"
                        rows="5"
                        v-model="state.description"
                        placeholder="해당 점수를 준 이유를 적어주세요"
                        style="font-family: Binggrae"
                      ></textarea>
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button
                      id="input"
                      class="form-control form-control-md col-lg-2"
                      @click="clickSubmitReview"
                      style="font-family: Binggrae"
                    >
                      점수부여
                    </button>
                  </div>
                </div>
              </div>
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
                  <div class="modal-header">
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
                      <textarea
                        cols="30"
                        rows="5"
                        v-model="state.reportMsg"
                        placeholder="신고 내용을 작성해주세요."
                        style="font-family: Binggrae"
                      ></textarea>
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
import { reactive, onMounted } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router";
export default {
  name: "ReviewAdvice",
  setup() {
    const store = useStore();
    const route = useRoute();
    const router = useRouter();
    const state = reactive({
      user_id: route.params.user_id,
      consultant_id: route.params.consultant_id,
      description: "",
      point: "",
      reportBool: false,
      reportMsg: "",
    });

    onMounted(() => {
      window.scrollTo(0, 0);
    })

    const clickSubmitReview = async function () {
      const body = {
        userId: state.user_id,
        consultantId: state.consultant_id,
        description: state.description,
        point: state.point,
      };
      await store.dispatch("root/reviewSubmitReview", body);
      await store.dispatch("root/userGetInfo", localStorage.getItem("jwt"));
      state.description = "";
    };

    const clickReviewReportDetail = async function () {
      await store.dispatch("root/reportDetail", {
        category: "advice",
        reportUserId: state.consultant_id,
        userId: state.user_id,
        reportMsg: state.reportMsg,
      });
      state.reportMsg = "";
    };

    const goToHome = function () {
      store.dispatch("root/userGetInfo", localStorage.getItem('jwt'))
      router.push({
        name: "Home",
      });
    };

    return { state, onMounted, clickSubmitReview, clickReviewReportDetail, goToHome };
  },
};
</script>

<style scoped lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Mulish:wght@400;600;700&display=swap");

*,
*::before,
*::after {
  box-sizing: border-box;
}

/* general layout */

// * {
//   font-family: inherit;
//   line-height: inherit;
//   color: inherit;
// }

.news {
  background-color: #fff;
  padding: 2.125em 1.25em;
  border-radius: 30px;
  box-shadow: 0px 34px 85px -25px #bbc0ea;
}
.news__title {
  font-size: 1.75rem;
  margin-bottom: 1.5em;
}

.custom {
  opacity: 0;
  position: relative;
}

.custom + label {
  display: grid;
  grid-template-columns: 24px auto;
  grid-template-rows: auto auto;
  align-items: center;
  column-gap: 1em;
  position: relative;
  cursor: pointer;
}

.custom[type="checkbox"] + label::before {
  content: "";
  width: 24px;
  height: 24px;
  border-radius: 3px;
  border: 1px solid #8f94a1;
  background: transparent;
  margin-bottom: 10px;
}

.custom[type="checkbox"] + label::after {
  content: "\2713";
  color: white;
  font-size: 1.25rem;
  position: absolute;
  top: 0px;
  left: 4px;
  transform: scale(0);
  transition: all 300ms ease-in-out;
}

.custom[type="checkbox"]:checked + label::before {
  background-color: hsl(248, 66%, 66%);
}

.custom[type="checkbox"]:checked + label::after {
  transform: scale(1);
}

h5 {
  font-size: 1.25rem;
  white-space: nowrap;
}

p {
  grid-column: 2/ -2;
}
.card {
  width: 100%;
  padding: 0 12px;
}
.card.active {
  background-color: hsl(232, 41%, 97%);

  height: 88px;
  width: 100%;
}

.form-group h1:hover {
  cursor: pointer;
}

// ======= form
.news__form {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 30px;
}

@media (min-width: 550px) {
  .news-grid {
    grid-auto-flow: column;
    grid-template-columns: repeat(3, 1fr);
  }
  .news {
    padding: 4.125em 2.25em;
  }
  .news__form {
    flex-direction: row;
    gap: 10px;
  }
  input[type="email"] {
    flex: 0 0 480px;
  }
}

#myform fieldset {
  display: inline-block; /* 하위 별점 이미지들이 있는 영역만 자리를 차지함.*/
  border: 0; /* 필드셋 테두리 제거 */
  direction: rtl; /* 이모지 순서 반전 */
}
#myform input[type="radio"] {
  display: none; /* 라디오박스 감춤 */
}
#myform label {
  font-size: 3em; /* 이모지 크기 */
  color: transparent; /* 기존 이모지 컬러 제거 */
  text-shadow: 0 0 0 #f0f0f0; /* 새 이모지 색상 부여 */
}

#myform label:hover {
  text-shadow: 0 0 0 #a00; /* 마우스 호버 */
}
#myform label:hover ~ label {
  text-shadow: 0 0 0 #a00; /* 마우스 호버 뒤에오는 이모지들 */
}

#myform fieldset legend {
  text-align: left;
}

#myform input[type="radio"]:checked ~ label {
  text-shadow: 0 0 0 #a00; /* 마우스 클릭 체크 */
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
</style>
