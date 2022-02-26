<template>
  <div>
    <h3 style="text-align: left">내가 받은 리뷰</h3>
    <br>
    <div v-if="state.profileHistoryReceivedReview==[]"></div>
    <div v-else class="row d-flex justify-content-start ms-3">
      <div class="col-4" v-for="(receivedReview, idx) in state.profileHistoryReceivedReview" :key="idx">
        <a href="#" class="data-card">
          <h3>작성자 : {{ receivedReview.userNickname }}</h3><br>
          <h4>Point : {{ receivedReview.review.point }} pt</h4><br>
          <p>{{ receivedReview.review.description }}</p>
        </a>
      </div>
    </div>
    <br>
    <div class="d-flex justify-content-center mb-5" v-if="state.profileHistoryReceivedReviewLastPageNum!=0">
      <button id="rec_prev" class="paginaterec left" @click="checkReceivedPage($event)"><i></i><i></i></button>
      <div class="counter">{{state.receivedpage}}페이지 / {{ state.profileHistoryReceivedReviewLastPageNum }}페이지 </div>
      <button id="rec_next" class="paginaterec right" @click="checkReceivedPage($event)"><i></i><i></i></button>
    </div>
    <div v-else>
      <br><br>
      <span style="font-size: 25px;">아직 받은 리뷰가 없습니다.</span>
    </div>
    <br>
    <hr>
    <br>
    <h3 style="text-align: left;">내가 적은 리뷰</h3>
    <br>
    <div v-if="state.profileHistoryWrittenReview==[]"></div>
    <div v-else class="row d-flex justify-content-start ms-3">
      <div class="col-4" v-for="(writtenReview, idx) in state.profileHistoryWrittenReview" :key="idx">
        <a href="#" class="data-card">
          <h3>작성자 : {{ writtenReview.userNickname }}</h3><br>
          <h4>Point : {{ writtenReview.review.point }} pt</h4><br>
          <p>{{ writtenReview.review.description }}</p>
        </a>
      </div>
    </div>
    <br>
    <div class="d-flex justify-content-center mb-5" v-if="state.profileHistoryWrittenReviewLastPageNum!=0">
      <button id="wri_prev" class="paginatewri left" @click="checkWrittenPage($event)"><i></i><i></i></button>
      <div class="counter">{{state.writtenpage}}페이지 / {{ state.profileHistoryWrittenReviewLastPageNum }}페이지 </div>
      <button id="wri_next" class="paginatewri right" @click="checkWrittenPage($event)"><i></i><i></i></button>
    </div>
    <div v-else>
      <br><br>
      <span style="font-size: 25px;">아직 작성한 리뷰가 없습니다.</span>
    </div>
    <br><br>
  </div>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: "ProfileHistoryReview",
  props: {
    userInfo: Object,
  },
  setup (props) {
    const store = useStore()
    const state = reactive ({
      userInfo: props.userInfo,
      profileHistoryReceivedReview: computed(() => store.getters['root/profileHistoryReceivedReview']),
      profileHistoryReceivedReviewLastPageNum: computed(() => store.getters['root/profileHistoryReceivedReviewLastPageNum']),
      profileHistoryWrittenReview: computed(() => store.getters['root/profileHistoryWrittenReview']),
      profileHistoryWrittenReviewLastPageNum: computed(() => store.getters['root/profileHistoryWrittenReviewLastPageNum']),
      receivedpage: 1,
      writtenpage: 1,
    })

    onMounted(() => {
      if (state.profileHistoryReceivedReviewLastPageNum != 0) {
        const pr = document.querySelector('.paginaterec.left')
        const pl = document.querySelector('.paginaterec.right')

        pr.setAttribute('data-state', state.receivedpage===1 ? 'disabled' : '')
        if (state.receivedpage===1) {
          pr.disabled = true
        } else {
          pr.disabled = false
        }
        pl.setAttribute('data-state', state.receivedpage===state.profileHistoryReceivedReviewLastPageNum ? 'disabled' : '')
        if (state.receivedpage === state.profileHistoryReceivedReviewLastPageNum) {
          pl.disabled = true
        } else {
          pl.disabled = false
        }
      }

      if (state.profileHistoryWrittenReviewLastPageNum != 0) {
        const prwr = document.querySelector('.paginatewri.left')
        const plwr = document.querySelector('.paginatewri.right')

        prwr.setAttribute('data-state', state.writtenpage===1 ? 'disabled' : '')
        if (state.writtenpage===1) {
          prwr.disabled = true
        } else {
          prwr.disabled = false
        }
        plwr.setAttribute('data-state', state.writtenpage===state.profileHistoryWrittenReviewLastPageNum ? 'disabled' : '')
        if (state.writtenpage === state.profileHistoryWrittenReviewLastPageNum) {
          plwr.disabled = true
        } else {
          plwr.disabled = false
        }
      }
    })

    const checkReceivedPage = async function(event) {
      let targetId = event.currentTarget.id;
      if(targetId == "rec_prev") {
        state.receivedpage -= 1;
      }
      else if(targetId == "rec_next") {
        state.receivedpage += 1;
      }

      const pr = document.querySelector('.paginaterec.left')
      const pl = document.querySelector('.paginaterec.right')

      pr.setAttribute('data-state', state.receivedpage===1 ? 'disabled' : '')
      if (state.receivedpage===1) {
        pr.disabled = true
      } else {
        pr.disabled = false
      }
      pl.setAttribute('data-state', state.receivedpage===state.profileHistoryReceivedReviewLastPageNum ? 'disabled' : '')
      if (state.receivedpage === state.profileHistoryReceivedReviewLastPageNum) {
        pl.disabled = true
      } else {
        pl.disabled = false
      }

      await store.dispatch("root/profileGetHistoryReceivedReviewPageSearch", {
        user_id: state.userInfo.id,
        page: state.receivedpage,
        size: 3,
      })
    }

    const checkWrittenPage = async function(event) {
      let targetId = event.currentTarget.id;
      if(targetId == "wri_prev") {
        state.writtenpage -= 1;
      }
      else if(targetId == "wri_next") {
        state.writtenpage += 1;
      }

      const prwr = document.querySelector('.paginatewri.left')
      const plwr = document.querySelector('.paginatewri.right')

      prwr.setAttribute('data-state', state.writtenpage===1 ? 'disabled' : '')
      if (state.writtenpage===1) {
        prwr.disabled = true
      } else {
        prwr.disabled = false
      }
      plwr.setAttribute('data-state', state.writtenpage===state.profileHistoryWrittenReviewLastPageNum ? 'disabled' : '')
      if (state.writtenpage === state.profileHistoryWrittenReviewLastPageNum) {
        plwr.disabled = true
      } else {
        plwr.disabled = false
      }

      await store.dispatch("root/profileGetHistoryWrittenReviewPageSearch", {
        user_id: state.userInfo.id,
        page: state.writtenpage,
        size: 3,
      })
    }

    return { state, onMounted, checkReceivedPage, checkWrittenPage }
  }
}
</script>

<style scoped lang="scss">
@import url('https://fonts.googleapis.com/css2?family=Open+Sans:wght@600;700&display=swap');

* {
  box-sizing: border-box;
}

.page-contain {
  display: flex;
  min-height: 100vh;
  align-items: center;
  justify-content: center;
  background: #E7F3F1;
  border: .75em solid white;
  padding: 2em;
  font-family: 'Open Sans', sans-serif;
}

.data-card {
  display: flex;
  flex-direction: column;
  max-width: 20.75em;
  max-height: 15em;
  overflow: hidden;
  border: 1px solid #c2d6f8;
  border-radius: 30px;
  text-decoration: none;
  background: white;
  margin: 1em;
  padding: 2.75em 2.5em;
  box-shadow: 0 1.5em 2.5em -.5em rgba(#000000, .1);
  transition: transform .45s ease, background .45s ease;
  
  h3 {
    color: #2E3C40;
    font-size: 1.3em;
    font-weight: 600;
    line-height: 1;
    padding-bottom: .5em;
    margin: 0 0 0.142857143em;
    border-bottom: 2px solid #c2d6f8;
    transition: color .45s ease, border .45s ease;
  }

  h4 {
    color: #627084;
    text-transform: uppercase;
    font-size: 1.125em;
    font-weight: 700;
    line-height: 1;
    letter-spacing: 0.1em;
    margin: 0 0 1em;
    transition: color .45s ease;
  }

  p {
    // opacity: 0;
    color: #627084;
    font-weight: 600;
    line-height: 1.8;
    margin-bottom: 5px;
    transform: translateY(-1em);
    // transition: opacity .45s ease, transform .5s ease;
  }

  .link-text {
    display: block;
    color: #708bef;
    font-size: 1.125em;
    font-weight: 600;
    line-height: 1.2;
    margin: auto 0 0;
    transition: color .45s ease;
  }
  
  &:hover {
    background: #c2d6f8;
    transform: scale(1.02);
    
    h3 {
      color: #FFFFFF;
      border-bottom-color: white;
    }

    h4 {
      color: #FFFFFF;
    }

    p {
      color: #FFFFFF
    }

    .link-text {
      color: black;
    }
  }
}

@keyframes point {
  0% {
   transform: translateX(0);
  }
  100% {
    transform: translateX(.125em);
  }
}


// pagination

button {
  -webkit-appearance: none;
  background: transparent;
  border: 0;
  outline: 0;
}

.paginaterec {
  position: relative;
  margin: 10px;
  width: 50px;
  height: 50px;
  cursor: pointer;
  transform: translate3d(0, 0, 0);
  position: absolute;
  margin-top: -25px;
  -webkit-filter: drop-shadow(0 2px 0px rgba(0, 0, 0, 0.2));
}
.paginaterec i {
  position: absolute;
  left: 0;
  width: 60%;
  height: 5px;
  border-radius: 2.5px;
  background: #708bef;
  transition: all 0.15s ease;
}
.paginaterec.left {
  position: relative;
}
.paginaterec.left i {
  transform-origin: 0% 50%;
}
.paginaterec.left i:first-child {
  transform: translate(0, -1px) rotate(40deg);
}
.paginaterec.left i:last-child {
  transform: translate(0, 1px) rotate(-40deg);
}
.paginaterec.left:hover i:first-child {
  transform: translate(0, -1px) rotate(30deg);
}
.paginaterec.left:hover i:last-child {
  transform: translate(0, 1px) rotate(-30deg);
}
.paginaterec.left:active i:first-child {
  transform: translate(1px, -1px) rotate(25deg);
}
.paginaterec.left:active i:last-child {
  transform: translate(1px, 1px) rotate(-25deg);
}
.paginaterec.left[data-state=disabled] i:first-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginaterec.left[data-state=disabled] i:last-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginaterec.left[data-state=disabled]:hover i:first-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginaterec.left[data-state=disabled]:hover i:last-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginaterec.right {
  position: relative;
}
.paginaterec.right i {
  transform-origin: 100% 50%;
}
.paginaterec.right i:first-child {
  transform: translate(0, 1px) rotate(40deg);
}
.paginaterec.right i:last-child {
  transform: translate(0, -1px) rotate(-40deg);
}
.paginaterec.right:hover i:first-child {
  transform: translate(0, 1px) rotate(30deg);
}
.paginaterec.right:hover i:last-child {
  transform: translate(0, -1px) rotate(-30deg);
}
.paginaterec.right:active i:first-child {
  transform: translate(1px, 1px) rotate(25deg);
}
.paginaterec.right:active i:last-child {
  transform: translate(1px, -1px) rotate(-25deg);
}
.paginaterec.right[data-state=disabled] i:first-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginaterec.right[data-state=disabled] i:last-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginaterec.right[data-state=disabled]:hover i:first-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginaterec.right[data-state=disabled]:hover i:last-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginaterec[data-state=disabled] {
  opacity: 0.3;
  cursor: default;
}


.paginatewri {
  position: relative;
  margin: 10px;
  width: 50px;
  height: 50px;
  cursor: pointer;
  transform: translate3d(0, 0, 0);
  position: absolute;
  margin-top: -25px;
  -webkit-filter: drop-shadow(0 2px 0px rgba(0, 0, 0, 0.2));
}
.paginatewri i {
  position: absolute;
  left: 0;
  width: 60%;
  height: 5px;
  border-radius: 2.5px;
  background: #708bef;
  transition: all 0.15s ease;
}
.paginatewri.left {
  position: relative;
}
.paginatewri.left i {
  transform-origin: 0% 50%;
}
.paginatewri.left i:first-child {
  transform: translate(0, -1px) rotate(40deg);
}
.paginatewri.left i:last-child {
  transform: translate(0, 1px) rotate(-40deg);
}
.paginatewri.left:hover i:first-child {
  transform: translate(0, -1px) rotate(30deg);
}
.paginatewri.left:hover i:last-child {
  transform: translate(0, 1px) rotate(-30deg);
}
.paginatewri.left:active i:first-child {
  transform: translate(1px, -1px) rotate(25deg);
}
.paginatewri.left:active i:last-child {
  transform: translate(1px, 1px) rotate(-25deg);
}
.paginatewri.left[data-state=disabled] i:first-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginatewri.left[data-state=disabled] i:last-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginatewri.left[data-state=disabled]:hover i:first-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginatewri.left[data-state=disabled]:hover i:last-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginatewri.right {
  position: relative;
}
.paginatewri.right i {
  transform-origin: 100% 50%;
}
.paginatewri.right i:first-child {
  transform: translate(0, 1px) rotate(40deg);
}
.paginatewri.right i:last-child {
  transform: translate(0, -1px) rotate(-40deg);
}
.paginatewri.right:hover i:first-child {
  transform: translate(0, 1px) rotate(30deg);
}
.paginatewri.right:hover i:last-child {
  transform: translate(0, -1px) rotate(-30deg);
}
.paginatewri.right:active i:first-child {
  transform: translate(1px, 1px) rotate(25deg);
}
.paginatewri.right:active i:last-child {
  transform: translate(1px, -1px) rotate(-25deg);
}
.paginatewri.right[data-state=disabled] i:first-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginatewri.right[data-state=disabled] i:last-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginatewri.right[data-state=disabled]:hover i:first-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginatewri.right[data-state=disabled]:hover i:last-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginatewri[data-state=disabled] {
  opacity: 0.3;
  cursor: default;
}

.counter {
  text-align: center;
  position: relative;
  width: 20%;
  margin-top: -22px;
  font-size: 30px;
  font-weight: bold;
  font-family: "Binggrae";
  text-shadow: 0px 2px 0px rgba(0, 0, 0, 0.2);
  color: #708bef;
  z-index: -1;
}

</style>