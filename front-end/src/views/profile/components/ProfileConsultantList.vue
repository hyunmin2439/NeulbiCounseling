<template>
  <div>
    <h3 style="text-align: left;">공감한 상담가</h3>
    <br>
    <div v-if="(state.profileConsultantLike)">
      <div class="row d-flex justify-content-start">
        <div v-for="(consultant, index) in state.profileConsultantLike" :key="index" class="col-3 pe-5">
          <div class="card">
            <img :src="'https://e202.s3.ap-northeast-2.amazonaws.com/'+consultant.profileImg" class="card__image">
            <div class="card__overlay">
              <div class="card__header">
                <svg class="card__arc" xmlns="http://www.w3.org/2000/svg"><path /></svg>                     
                <img class="card__thumb" :src="require('@/assets/mask/mask'+consultant.maskId+'.png')" alt="" />
                <div class="card__header-text">
                  <h3 class="card__title">상담가 : {{ consultant.nickname }}</h3> 
                  <span class="card__status">주제: {{ consultant.topicCategoryName }}</span>
                </div>
              </div>
              <p class="card__description">
                {{ consultant.description }}<br>
                상담횟수 : {{ consultant.consultingCnt }}
                <button
                  type="button"
                  class="front__text-hover"
                  @click="clickCreateChatRoom(consultant.id)"
                >
                  1:1 채팅하기
                </button>
              </p>

            </div>
          </div>      
        </div>
        <br>
        <div class="d-flex justify-content-center mt-5 pt-3 mb-3" v-if="state.profileConsultantLastPageNum!=0">
          <button id="prev" class="paginate left" @click="checkProfilePage($event)"><i></i><i></i></button>
          <div class="counter">{{state.profilepage}}페이지 / {{ state.profileConsultantLastPageNum }}페이지 </div>
          <button id="next" class="paginate right" @click="checkProfilePage($event)"><i></i><i></i></button>
        </div>
        <div v-else>
          <br><br>
          <span style="font-size: 25px;">아직 공감한 상담가가 없습니다.</span>
        </div>
      </div>
    </div>
    <div v-else>없습니다.</div>
  </div>
  <br><br><br>
</template>

<script>
import { useStore } from 'vuex'
import { reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: "ProfileConsultantList",
  props:{
    userInfo: Object,
  },
  setup (props) {
    const store = useStore()
    const router = useRouter()
    const state = reactive ({
      userInfo: props.userInfo,
      profileConsultantLike: computed(() => store.getters['root/profileConsultantLike']),
      profileConsultantLastPageNum: computed(() => store.getters['root/profileConsultantLastPageNum']),
      topicCategoryName: [
        '학업', '가정', '취업', '진로', '연애', '결혼',
      ],
      profilepage: 1,
    })

    onMounted(async () => {
      if (state.profileConsultantLastPageNum != 0) {
        const pr = document.querySelector('.paginate.left')
        const pl = document.querySelector('.paginate.right')
        await pr.setAttribute('data-state', state.profilepage===1 ? 'disabled' : '')
        if (state.profilepage===1) {
          pr.disabled = true
        } else {
          pr.disabled = false
        }
        await pl.setAttribute('data-state', state.profilepage===state.profileConsultantLastPageNum ? 'disabled' : '')
        if (state.profilepage === state.profileConsultantLastPageNum) {
          pl.disabled = true
        } else {
          pl.disabled = false
        }
      }
    })

    const checkProfilePage = async function(event) {
      let targetId = event.currentTarget.id;
      if(targetId == "prev") {
        state.profilepage -= 1;
      }
      else if(targetId == "next") {
        state.profilepage += 1;
      }

      const pr = document.querySelector('.paginate.left')
      const pl = document.querySelector('.paginate.right')

      pr.setAttribute('data-state', state.profilepage===1 ? 'disabled' : '')
      if (state.profilepage===1) {
        pr.disabled = true
      } else {
        pr.disabled = false
      }
      pl.setAttribute('data-state', state.profilepage===state.profileConsultantLastPageNum ? 'disabled' : '')
      if (state.profilepage === state.profileConsultantLastPageNum) {
        pl.disabled = true
      } else {
        pl.disabled = false
      }

      await store.dispatch("root/profileGetConsultantLikePageSearch", {
        user_id: state.userInfo.id,
        page: state.profilepage,
        size: 4,
      })
    }
    const clickCreateChatRoom = async function (consultant_id) {
      const body = { userId: state.userInfo.id, consultantId: consultant_id }
      await store.dispatch("root/adviceCreateChatRoom", body)
      await router.push({
        name: "ChatRoom",
        params: {
          user_id: props.userInfo.id,
        }
      })
    }

    return { 
      state,
      onMounted,
      checkProfilePage,
      clickCreateChatRoom
    }
  }
}
</script>

<style scoped lang="scss">

input {
  display: none;
}

.like {
  display: block;
  width: var(--size);
  height: var(--size);
  cursor: pointer;
  border-radius: 999px;
  overflow: visible;
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
  -webkit-tap-highlight-color: transparent;
    --size: 80px;
  --frames: 62;
}

.hearth {
  background-image: url('https://assets.codepen.io/23500/Hashflag-AppleEvent.svg');
  background-size: calc(var(--size) * var(--frames)) var(--size);
  background-repeat: no-repeat;
  background-position-x: calc(var(--size) * (var(--frames) * -1 + 2));
  background-position-y: calc(var(--size) * 0.02);
  width: var(--size);
  height: var(--size);
  --size: 80px;
  --frames: 62;
}

input:checked + .hearth {
  animation: like 1s steps(calc(var(--frames) - 3));  
  animation-fill-mode: forwards;
}

@keyframes like {
  0% {
    background-position-x: 0;
  }
  100% {
    background-position-x: calc(var(--size) * (var(--frames) * -1 + 3));
  }
}

@media (hover: hover) {
  .like:hover {
    background-color: #E1255E15;
    .hearth {
      background-position-x: calc(var(--size) * (var(--frames) * -1 + 1));
    }
  }
}

.cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  margin: 4rem 5vw;
  padding: 0;
  list-style-type: none;
}

.card {
  position: relative;
  display: flex;
  width: 100%;
  height: 32vh;
  border-radius: calc(var(--curve) * 1px);
  overflow: hidden;
  text-decoration: none;
  --surface-color: #fff;
  --curve: 40;
  box-shadow: 0 15px 10px -10px rgba(0, 0, 0, 0.5), 0 1px 4px rgba(0, 0, 0, 0.3),
    0 0 40px rgba(0, 0, 0, 0.1) inset;
}

.card__image {      
  width: 100%;
  height: auto;
}

.card__overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1;      
  border-radius: calc(var(--curve) * 1px);    
  background-color: var(--surface-color);      
  transform: translateY(100%);
  transition: .2s ease-in-out;
}

.card:hover .card__overlay {
  transform: translateY(0);
}

.card__header {
  position: relative;
  display: flex;
  align-items: center;
  gap: 2em;
  padding: 1em;
  border-radius: calc(var(--curve) * 1px) 0 0 0;    
  background-color: var(--surface-color);
  transform: translateY(-100%);
  transition: .2s ease-in-out;
}

.card__arc {
  width: 80px;
  height: 80px;
  position: absolute;
  bottom: 100%;
  right: 0;      
  z-index: 1;
}

.card__arc path {
  fill: var(--surface-color);
  d: path("M 40 80 c 22 0 40 -22 40 -40 v 40 Z");
}       

.card:hover .card__header {
  transform: translateY(0);
}

.card__thumb {
  flex-shrink: 0;
  width: 50px;
  height: 50px;      
  border-radius: 50%;      
}

.card__title {
  font-size: 1em;
  margin: 0 0 .3em;
  color: #6A515E;
}

.card__tagline {
  display: block;
  margin: 1em 0;
  font-family: "MockFlowFont";  
  font-size: .8em; 
  color: #D7BDCA;  
}

.card__status {
  font-size: .8em;
  color: #D7BDCA;
}

.card__description {
  padding: 0 2em 2em;
  margin: 0;
  color: #D7BDCA;
  font-family: "MockFlowFont";   
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 8;
  overflow: hidden;

  .front__text-hover {
    position: relative;
    top: 10px;
    font-size: 15px;
    color: #bbd2f9;
    backface-visibility: hidden;

    font-weight: 700;
    text-transform: uppercase;
    letter-spacing: .4px;

    border: 2px solid #bbd2f9;
    padding: 8px 15px;
    border-radius: 30px;

    background: #bbd2f9;
    color: #fff;
  }
}

// pagination

button {
  -webkit-appearance: none;
  background: transparent;
  border: 0;
  outline: 0;
}

.paginate {
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
.paginate i {
  position: absolute;
  left: 0;
  width: 60%;
  height: 5px;
  border-radius: 2.5px;
  background: #708bef;
  transition: all 0.15s ease;
}
.paginate.left {
  position: relative;
}
.paginate.left i {
  transform-origin: 0% 50%;
}
.paginate.left i:first-child {
  transform: translate(0, -1px) rotate(40deg);
}
.paginate.left i:last-child {
  transform: translate(0, 1px) rotate(-40deg);
}
.paginate.left:hover i:first-child {
  transform: translate(0, -1px) rotate(30deg);
}
.paginate.left:hover i:last-child {
  transform: translate(0, 1px) rotate(-30deg);
}
.paginate.left:active i:first-child {
  transform: translate(1px, -1px) rotate(25deg);
}
.paginate.left:active i:last-child {
  transform: translate(1px, 1px) rotate(-25deg);
}
.paginate.left[data-state=disabled] i:first-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginate.left[data-state=disabled] i:last-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginate.left[data-state=disabled]:hover i:first-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginate.left[data-state=disabled]:hover i:last-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginate.right {
  position: relative;
}
.paginate.right i {
  transform-origin: 100% 50%;
}
.paginate.right i:first-child {
  transform: translate(0, 1px) rotate(40deg);
}
.paginate.right i:last-child {
  transform: translate(0, -1px) rotate(-40deg);
}
.paginate.right:hover i:first-child {
  transform: translate(0, 1px) rotate(30deg);
}
.paginate.right:hover i:last-child {
  transform: translate(0, -1px) rotate(-30deg);
}
.paginate.right:active i:first-child {
  transform: translate(1px, 1px) rotate(25deg);
}
.paginate.right:active i:last-child {
  transform: translate(1px, -1px) rotate(-25deg);
}
.paginate.right[data-state=disabled] i:first-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginate.right[data-state=disabled] i:last-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginate.right[data-state=disabled]:hover i:first-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginate.right[data-state=disabled]:hover i:last-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginate[data-state=disabled] {
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