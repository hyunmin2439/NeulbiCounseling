<template>
  <div>
    <br>
    <h3 style="text-align: left;">고해성사 기록</h3><br>
    <div v-if="state.profileHistoryConfession==[]"></div>
    <div v-else class="row d-flex justify-content-start ms-3">
      <div v-for="(confession, index) in state.profileHistoryConfession" :key="index" class="col-4">
        <div class="card">
          <img :src="'https://e202.s3.ap-northeast-2.amazonaws.com/'+confession.userProfileImg" class="card__image">
          <div class="card__overlay">
            <div class="card__header">
              <svg class="card__arc" xmlns="http://www.w3.org/2000/svg"><path /></svg>                 
              <img class="card__thumb" :src="require('@/assets/mask/mask'+confession.userMaskId+'.png')" alt="" />
              <div class="card__header-text">
                <h3 class="card__title">방장 : {{ confession.nickname }}</h3>
                <span class="card__status">주제: {{ state.topicCategoryName[confession.topicCategoryId-1]}}</span>
              </div>
            </div>
            <div class="card__description" style="text-align: center;">
              <span style="font-weight: bold;">{{ confession.description }}</span><br><hr>
              <span>총 참가자 수 : {{ confession.participants }}</span><br><br>
              <div v-if="confession.createdDate!=null&confession.endDate!=null">
                <span>{{ confession.createdDate.substr(0, 16) }}</span><br>
                <span> ~ </span><br>
                <span>{{ confession.endDate.substr(0, 16) }}</span><br>
              </div>
            </div>
          </div>
        </div>      
      </div>
    </div>
    <br>
    <br>
    <div class="d-flex justify-content-center mb-5 mt-2" v-if="state.profileHistoryConfessionLastPageNum!=0">
      <button id="conf_prev" class="paginate left" @click="checkConfessionPage($event)"><i></i><i></i></button>
      <div class="counter">{{state.confessionpage}}페이지 / {{ state.profileHistoryConfessionLastPageNum }}페이지 </div>
      <button id="conf_next" class="paginate right" @click="checkConfessionPage($event)"><i></i><i></i></button>
    </div>
    <div v-else>
      <br><br>
      <span style="font-size: 25px;">아직 고해성사 이력이 없습니다.</span>
    </div>
    <br>
    <hr>
    <br>
  </div>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: "ProfileHistoryConfession",
  setup () {
    const store = useStore()
    const state = reactive ({
      userInfo: computed(() => store.getters['root/userInfo']),
      profileHistoryConfession: computed(() => store.getters['root/profileHistoryConfession']),
      profileHistoryConfessionLastPageNum: computed(() => store.getters['root/profileHistoryConfessionLastPageNum']),
      topicCategoryName: [
        '학업', '가정', '취업', '진로', '연애', '결혼',
      ],
      confessionpage: 1,
    })

    onMounted(async () => {
      if (state.profileHistoryConfessionLastPageNum != 0) {
        const pr = document.querySelector('.paginate.left')
        const pl = document.querySelector('.paginate.right')
        await pr.setAttribute('data-state', state.confessionpage===1 ? 'disabled' : '')
        if (state.confessionpage===1) {
          pr.disabled = true
        } else {
          pr.disabled = false
        }
        
        await pl.setAttribute('data-state', state.confessionpage===state.profileHistoryConfessionLastPageNum ? 'disabled' : '')
        if (state.confessionpage === state.profileHistoryConfessionLastPageNum) {
          pl.disabled = true
        } else {
          pl.disabled = false
        }
      }
    })

    const checkConfessionPage = async function(event) {
      let targetId = event.currentTarget.id;
      if(targetId == "conf_prev") {
        state.confessionpage -= 1;
      }
      else if(targetId == "conf_next") {
        state.confessionpage += 1;
      }

      const pr = document.querySelector('.paginate.left')
      const pl = document.querySelector('.paginate.right')

      pr.setAttribute('data-state', state.confessionpage===1 ? 'disabled' : '')
      if (state.confessionpage===1) {
        pr.disabled = true
      } else {
        pr.disabled = false
      }
      pl.setAttribute('data-state', state.confessionpage===state.profileHistoryConfessionLastPageNum ? 'disabled' : '')
      if (state.confessionpage === state.profileHistoryConfessionLastPageNum) {
        pl.disabled = true
      } else {
        pl.disabled = false
      }

      await store.dispatch("root/profileGetHistoryConfessionMeetingPageSearch", {
        user_id: state.userInfo.id,
        page: state.confessionpage,
        size: 3,
      })
    }

    return { state, onMounted, checkConfessionPage }
  }
}
</script>

<style scoped lang="scss">

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
  width: 80%;
  height: 36vh;  
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
  font-size: 1.5em;
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
  font-size: 1.2em;
  color: #D7BDCA;
}

.card__description {
  padding: 0 2em 2em;
  margin: 0;
  color: #6A515E;
  font-family: "Binggrae-Taom";   
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 8;
  overflow: hidden;

  .front__text-hover {
    position: relative;
    // top: 10px;
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