<template>
  <div>
    <link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet">
    <h3 style="text-align: left;">내가 작성한 게시글</h3>
    
    <br>
    <div v-if="state.profileHistoryCommunity==[]"></div>
    <div v-else class="row d-flex justify-content-start ms-3">
      <div class="col-4" v-for="(community, idx) in state.profileHistoryCommunity" :key="idx">
        <a href="#" class="data-card">
          <h3 v-if="community.title.length > 8">{{ community.title.substr(0,8) }}···</h3>
          <h3 v-else>{{ community.title }}</h3><br>
          <h4>작성자 : {{ community.userNickname }}</h4><br>
          <p><i class="ion-ios-heart-outline icon-1x"></i> 공감수 : {{ community.likeCnt }}</p>
          <p><i class="ion-ios-chatboxes-outline icon-1x"></i> 댓글수: {{ community.commentCnt }}</p>
          <p><i class="ion-ios-eye-outline icon-1x"></i> 조회수 : {{ community.viewCnt }}</p>
          <span class="link-text" @click="goToCommunity(community.id)">
            View This Community
          </span>
        </a>
      </div>
    </div>
    <div class="d-flex justify-content-center mb-5 mt-5" v-if="state.profileHistoryCommunityLastPageNum!=0">
      <button id="community_prev" class="paginatecommunity left" @click="checkCommunityPage($event)"><i></i><i></i></button>
      <div class="counter">{{state.communitypage}}페이지 / {{ state.profileHistoryCommunityLastPageNum }}페이지 </div>
      <button id="community_next" class="paginatecommunity right" @click="checkCommunityPage($event)"><i></i><i></i></button>
    </div>
    <div v-else>
      <br><br>
      <span style="font-size: 25px;">아직 작성한 게시글이 없습니다.</span>
    </div>
    <br>
    <hr>
  </div>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: "ProfileHistoryCommunity",
  setup () {
    const store = useStore()
    const router = useRouter()
    const state = reactive ({
      userInfo: computed(() => store.getters['root/userInfo']),
      profileHistoryCommunity: computed(() => store.getters['root/profileHistoryCommunity']),
      profileHistoryCommunityLastPageNum: computed(() => store.getters['root/profileHistoryCommunityLastPageNum']),
      communitypage: 1,
    })

    onMounted(async() => {
      if (state.profileHistoryCommunityLastPageNum != 0) {
        const prcommunity = document.querySelector('.paginatecommunity.left')
        const plcommunity = document.querySelector('.paginatecommunity.right')
        prcommunity.setAttribute('data-state', state.communitypage===1 ? 'disabled' : '')
        if (state.communitypage===1) {
          prcommunity.disabled = true
        } else {
          prcommunity.disabled = false
        }
        
        plcommunity.setAttribute('data-state', state.communitypage===state.profileHistoryCommunityLastPageNum ? 'disabled' : '')
        if (state.communitypage === state.profileHistoryCommunityLastPageNum) {
          plcommunity.disabled = true
        } else {
          plcommunity.disabled = false
        }
      }
    })

    const checkCommunityPage = async function(event) {
      let targetId = event.currentTarget.id;
      if(targetId == "community_prev") {
        state.communitypage -= 1;
      }
      else if(targetId == "community_next") {
        state.communitypage += 1;
      }

      const prcommunity = document.querySelector('.paginatecommunity.left')
      const plcommunity = document.querySelector('.paginatecommunity.right')

      prcommunity.setAttribute('data-state', state.communitypage===1 ? 'disabled' : '')
      if (state.communitypage===1) {
        prcommunity.disabled = true
      } else {
        prcommunity.disabled = false
      }
      plcommunity.setAttribute('data-state', state.communitypage===state.profileHistoryCommunityLastPageNum ? 'disabled' : '')
      if (state.communitypage === state.profileHistoryCommunityLastPageNum) {
        plcommunity.disabled = true
      } else {
        plcommunity.disabled = false
      }

      await store.dispatch("root/profileGetHistoryCommunityPageSearch", {
        user_id: state.userInfo.id,
        page: state.communitypage,
        size: 3,
      })
    }

    const goToCommunity = async function (communityId) {
      await store.dispatch("root/communityGetDetail", {
        user_id: state.userInfo.id, community_id: communityId
      })
      await router.push({
        name: 'CommunityDetail',
          params: {
            community_id: communityId
          }
      })
    }

    return { state, onMounted, checkCommunityPage, goToCommunity }
  }
}
</script>

<style scoped lang="scss">

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
}

.data-card {
  display: flex;
  flex-direction: column;
  max-width: 20.75em;
  min-height: 20.75em;
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
    font-size: 1.6em;
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

.paginatecommunity {
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
.paginatecommunity i {
  position: absolute;
  left: 0;
  width: 60%;
  height: 5px;
  border-radius: 2.5px;
  background: #708bef;
  transition: all 0.15s ease;
}
.paginatecommunity.left {
  position: relative;
}
.paginatecommunity.left i {
  transform-origin: 0% 50%;
}
.paginatecommunity.left i:first-child {
  transform: translate(0, -1px) rotate(40deg);
}
.paginatecommunity.left i:last-child {
  transform: translate(0, 1px) rotate(-40deg);
}
.paginatecommunity.left:hover i:first-child {
  transform: translate(0, -1px) rotate(30deg);
}
.paginatecommunity.left:hover i:last-child {
  transform: translate(0, 1px) rotate(-30deg);
}
.paginatecommunity.left:active i:first-child {
  transform: translate(1px, -1px) rotate(25deg);
}
.paginatecommunity.left:active i:last-child {
  transform: translate(1px, 1px) rotate(-25deg);
}
.paginatecommunity.left[data-state=disabled] i:first-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginatecommunity.left[data-state=disabled] i:last-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginatecommunity.left[data-state=disabled]:hover i:first-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginatecommunity.left[data-state=disabled]:hover i:last-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginatecommunity.right {
  position: relative;
}
.paginatecommunity.right i {
  transform-origin: 100% 50%;
}
.paginatecommunity.right i:first-child {
  transform: translate(0, 1px) rotate(40deg);
}
.paginatecommunity.right i:last-child {
  transform: translate(0, -1px) rotate(-40deg);
}
.paginatecommunity.right:hover i:first-child {
  transform: translate(0, 1px) rotate(30deg);
}
.paginatecommunity.right:hover i:last-child {
  transform: translate(0, -1px) rotate(-30deg);
}
.paginatecommunity.right:active i:first-child {
  transform: translate(1px, 1px) rotate(25deg);
}
.paginatecommunity.right:active i:last-child {
  transform: translate(1px, -1px) rotate(-25deg);
}
.paginatecommunity.right[data-state=disabled] i:first-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginatecommunity.right[data-state=disabled] i:last-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginatecommunity.right[data-state=disabled]:hover i:first-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginatecommunity.right[data-state=disabled]:hover i:last-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginatecommunity[data-state=disabled] {
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