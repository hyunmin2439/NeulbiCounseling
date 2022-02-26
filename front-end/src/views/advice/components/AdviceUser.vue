<template>
  <div class="mb-4 d-flex justify-content-center">
    <div class="outer-div">
      <div class="inner-div">
        <div class="front">
          <div class="front__bkg-photo">
            <img :src="profile.profileImgThumbnail" style="width: 100%;">
          </div>
          <div class="front__face-photo">
            <img :src="require('@/assets/mask/mask'+state.userInfo.maskId+'.png')" style="position: relative; width: 100%; z-index: 71;">
            <img :src="require('@/assets/back/back'+state.userInfo.backId+'.png')" style="position: relative; width: 150%; top: -120%; z-index: 70;">
          </div>
          <div class="front__text" style="font-family: Binggrae;">
            <h3 class="front__text-header">{{ state.userInfo.nickname }}</h3>
            <p>{{ state.userInfo.email }}<br>
            포인트: {{ state.userInfo.pointTot }}</p>
                    
            <button @click="goToConfession" class="front__text-hover mb-4">고해성사 페이지</button><br>
            <button v-if="state.userInfo.consultant==0" class="front__text-hover"  data-bs-toggle="modal" data-bs-target="#exampleModal">상담가 신청</button>
            <button v-else class="front__text-hover" @click="goToProfile">내 프로필 페이지</button>
          </div>
        </div>
      </div>
    </div>
    <!-- 마스크 모달 -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">상담가 신청하세요.</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form>
              <h3 class="py-3">상담가 신청</h3>
              <br>
              <!-- 선택하여 정수를 반환하도록 수정해야함 -->
              <div>
                <div class="pagination my-3 mx-2">
                  <div id="topic_div_1" @click="clickSearchList(1, $event)" style="cursor: pointer;">
                    <p>
                      학업
                    </p>
                  </div>
                  <div id="topic_div_2" @click="clickSearchList(2, $event)" style="cursor: pointer;">
                    <p>
                      가정
                    </p>
                  </div>
                  <div id="topic_div_3" @click="clickSearchList(3, $event)" style="cursor: pointer;">
                    <p>
                      취업
                    </p>
                  </div>
                </div>
                <div class="pagination my-3 mx-2">
                  <div id="topic_div_4" @click="clickSearchList(4, $event)" style="cursor: pointer;">
                    <p>
                      진로
                    </p>
                  </div>
                  <div id="topic_div_5" @click="clickSearchList(5, $event)" style="cursor: pointer;">
                    <p>
                      연애
                    </p>
                  </div>
                  <div id="topic_div_6" @click="clickSearchList(6, $event)" style="cursor: pointer;">
                    <p>
                      결혼
                    </p>
                  </div>
                </div>
              </div>
              <br>
              <div class="d-flex justify-content-center my-3">
                <div class="searchBox">
                  <input class="searchInput" type="text" placeholder="Description" v-model="state.description">
                </div>
              </div>
              <br><br>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">종료</button>
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="clickCreateConsultant">신청</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import { useStore } from 'vuex'
import { useRoute, useRouter } from "vue-router"
export default {
  name: "AdviceUser",
  props: {
    userInfo: Object,
  },
  setup(props) {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const state = reactive({
      userInfo: props.userInfo,
      profileImgThumbnail : `/profile/image/${props.userInfo.id}`,
      categories: [
        { value: "학업", number: "1" },
        { value: "가정", number: "2" },
        { value: "취업", number: "3" },
        { value: "진로", number: "4" },
        { value: "연애", number: "5" },
        { value: "결혼", number: "6" },
      ],
      categoryName: 'select',
      categoryId: 0,
      description: '',
    });

    const profile = reactive({
      profileImgThumbnail : computed(() => `https://e202.s3.ap-northeast-2.amazonaws.com/${state.userInfo.profileImg}`),
    })

    const clickSearchList = function (num, event) {
      state.categoryId = num
      state.categoryName = state.categories[num-1].value
      let targetId = event.currentTarget.id
      const topic_tag = document.getElementById(targetId)
      document.getElementById('topic_div_1').setAttribute('data-state', '')
      document.getElementById('topic_div_2').setAttribute('data-state', '')
      document.getElementById('topic_div_3').setAttribute('data-state', '')
      document.getElementById('topic_div_4').setAttribute('data-state', '')
      document.getElementById('topic_div_5').setAttribute('data-state', '')
      document.getElementById('topic_div_6').setAttribute('data-state', '')
      topic_tag.setAttribute('data-state', 'active')
    }

    const clickCreateConsultant = async function () {
      await store.dispatch("root/adviceCreateConsultant", {
        description: state.description,
        topicCategoryId: state.categoryId,
        userId: state.userInfo.id,
      })
      await store.dispatch("root/userGetInfo", localStorage.getItem('jwt'))
      await store.dispatch("root/adviceGetConsultantList", state.userInfo.id)
      await store.dispatch("root/adviceGetRankList")
      window.location.reload()
    }


    const goToConfession = function () {
      router.push({ name: 'Confession' })
    }

    const goToProfileConsultant = function () {
      router.push({
        name: 'Profile',
        params: {
          user_id: state.userInfo.id
        },
        query: {
          value: 'AdviceUser'
        }
      })
    }

    const goToProfile = function () {
      router.push({
        name: 'Profile',
        params: {
          user_id: state.userInfo.id
        },
      })
    }

    return { state, profile, clickSearchList, clickCreateConsultant, goToConfession, goToProfileConsultant, goToProfile };
  },
};
</script>

<style scoped lang="scss">

.outer-div,
.inner-div {
  height: 70%;
  max-width: 75%;
  margin: 0 auto;
  position: relative;
}

.outer-div {
  perspective: 900px;
  perspective-origin: 50% calc(50% - 18em);
}

.inner-div {
  margin: 0 auto;
  border-radius: 5px;
  font-weight: 400;
  color: #071011;
  font-size: 1rem;
  text-align: center;
  transition: all 0.6s cubic-bezier(0.8, -0.4, 0.2, 1.7);
  transform-style: preserve-3d;

  
  &:hover .social-icon {
    opacity: 1;
    top: 0;
  }

  /*&:hover .front__face-photo,
  &:hover .front__footer {
    opacity: 1;
  }*/
}


.front {
  position: relative;
  top: 0;
  left: 0;
  backface-visibility: hidden;
}

.front {
  height: 100%;
  background: #fff;
  backface-visibility: hidden;
  border-radius: calc(var(--curve) * 1px);
  --surface-color: #fff;
  --curve: 40;
  box-shadow: 0 15px 10px -10px rgba(0, 0, 0, 0.5), 0 1px 4px rgba(0, 0, 0, 0.3),
    0 0 40px rgba(0, 0, 0, 0.1) inset;
}

.front__bkg-photo {
  position: relative;
  height: 13vh;
  width: 100%;
  // background: url("https://images.unsplash.com/photo-1511207538754-e8555f2bc187?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=88672068827eaeeab540f584b883cc66&auto=format&fit=crop&w=1164&q=80")
  //   no-repeat;
  // background-size: cover;
  backface-visibility: hidden;
  overflow: hidden;
  border-top-left-radius: calc(var(--curve) * 1px);
  border-top-right-radius: calc(var(--curve) * 1px);
  --surface-color: #fff;
  --curve: 40;

  &:after {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
  }
}

.front__face-photo {
  position: relative;
  top: -60px;
  height: 120px;
  width: 120px;
  margin: 0 auto;
  border-radius: 50%;
  border: 3px solid black;
  background: white;
  overflow: hidden;
 /* backface-visibility: hidden;
  transition: all 0.6s cubic-bezier(0.8, -0.4, 0.2, 1.7);
  z-index: 3;*/
}

.front__text {
  position: relative;
  top: -55px;
  margin: 0 auto;
  font-family: "Montserrat";
  font-size: 22px;
  backface-visibility: hidden;

  .front__text-header {
    font-weight: 700;
    font-family: "Oswald";
    text-transform: uppercase;
    font-size: 20px;
  }

  .front__text-para {
    position: relative;
    top: -5px;

    color: #000;
    font-size: 14px;
    letter-spacing: 0.4px;
    font-weight: 400;
    font-family: "Montserrat", sans-serif;
  }

  .front-icons {
    position: relative;
    top: 0;
    font-size: 14px;
    margin-right: 6px;
    color: gray;
  }

  .front__text-hover {
    position: relative;
    top: 10px;
    font-size: 15px;
    color: #bbd2f9;
    backface-visibility: hidden;
    font-family: "Binggrae-Taom";

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

.social-media-wrapper {
  font-size: 36px;

  .social-icon {
    position: relative;
    top: 20px;
    margin-left: 5px;
    margin-right: 5px;
    opacity: 0;
    color: #fff;
    transition: all 0.4s cubic-bezier(0.3, 0.7, 0.1, 1.9);
  }

  .social-icon:nth-child(1) {
    transition-delay: 0.6s;
  }

  .social-icon:nth-child(2) {
    transition-delay: 0.7s;
  }

  .social-icon:nth-child(3) {
    transition-delay: 0.8s;
  }

  .social-icon:nth-child(4) {
    transition-delay: 0.9s;
  }
}

.fab {
  position: relative;
  top: 0;
  left: 0;
  transition: all 200ms ease-in-out;
}

.fab:hover {
  top: -5px;
}


// 버튼들

.pagination {
  display: flex;
  justify-content: center;
}
.pagination div {
  // flex: 1;
  margin: 0px 5px;
  background: #dde1e7;
  border-radius: 3px;
  width: 100%;
  box-shadow: -3px -3px 7px #ffffff73, 3px 3px 5px rgba(94, 104, 121, 0.288);
}
.pagination div p {
  font-size: 18px;
  text-decoration: none;
  color: #4d3252;
  height: 80%;
  width: 100%;
  // display: block;
  line-height: 45px;
  margin-bottom: 0px;
}
.pagination div[data-state=active] {
  box-shadow: inset -3px -3px 7px #ffffff73,
    inset 3px 3px 5px rgba(94, 104, 121, 0.288);
}
.pagination div[data-state=active] p {
  font-size: 17px;
  font-weight: bold;
}

// 검색창
.searchBox {
  position: absolute;
  // transform:  translate(-50%,50%);
  background: white;
  height: 10%;
  width: 90%;
  border-radius: 40px;
  padding: 10px;
  border: 2px solid #bbd2f9;
}


.searchInput {
  border:none;
  background: none;
  outline:none;
  float:left;
  padding: 0;
  color: black;
  font-size: 16px;
  transition: 0.4s;
  line-height: 0px;
  width: 90%;
  padding: 0 6px;
}


@media screen and (max-width: 620px) {
.searchBox:hover > .searchInput {
    width: 150px;
    padding: 0 6px;
  }
}
</style>
