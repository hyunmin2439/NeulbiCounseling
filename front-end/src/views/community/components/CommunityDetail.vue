<template>
<div>
  <main-header></main-header>
  <div class="container">
    <link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet">

    <!--페이지 헤더-->
    <div class="row inner-main-header" style="margin-top: 5%">
      <!-- 목록/이전글/다음글 -->
      <div class="row mb-3">
        <span class="dropdown col-lg-3 row text-center op-7"> 
          <div class="col-4">
            <button @click="goToCommunity" class="form-control form-control-md col-lg-2" id="input">목록</button>
          </div>
          <!-- <div class="col px-1">
            <button>이전글</button>
          </div>
          <div class="col px-1">
            <button>다음글</button>
          </div> -->
        </span>
      <div class="col-7"></div>
      <!-- 해당 글의 작성자일 경우, 수정, 삭제 버튼이 보임-->
        <div class="col-1">
          <span v-if="state.userInfo.id == state.communityDetail.user_id">
            <div>
              <button type="button" @click="clickModifyCommunity" class="form-control form-control-md col-lg-2" id="input">
                수정하기
              </button>
            </div>
          </span>
        </div>
        
        <div class="col-1">
          <span v-if="state.userInfo.id == state.communityDetail.user_id">
            <div>
              <button type="button" @click="clickDeleteCommunity" class="custom-form-control-red form-control-md col-lg-2" id="input">
                삭제하기
              </button>
            </div>
          </span>
        </div>

      </div>
    </div>

    <!--본문 헤더-->
    <div class="detailheader row-hover pos-relative py-3 px-3 row-lg-4">
      <div class="row align-items-center">
        <div class="col-md-10" id="leftalign">
          <h3>{{state.communityDetail.title}}</h3>
        </div>
      </div>
      <div class="row align-items-center">
        <div class="col-md-8 op-7" id="leftalign">
          <h5>{{state.communityDetail.userNickname}} | {{ state.communityDetail.createdDate.substr(0, 10) }}</h5>
        </div>
        
        <!-- {{{{ notice.date.substr(0, 10) }}}} -->
        <!-- <div class="col-md-4 op-7" id="leftalign">
          <h5>1999-99-99</h5>
        </div> -->
        <div class="col-md-4 op-7">
          <div class="row"> 
            <div class="col px-1"> <i class="ion-ios-eye-outline icon-1x" style="font-size: 28px;"></i> <span class="d-block text-sm">{{state.communityDetail.viewCnt}}</span></div>
            <div v-if="state.communityDetail.like" class="col px-1 mt-1"> <i class="ion-ios-heart icon-2x" style="font-size: 23px; margin-top: 5px;"></i> <span class="d-block text-sm">{{state.communityDetail.likeCnt}}</span></div>
            <div v-else class="col px-1 mt-1"> <i class="ion-ios-heart-outline icon-2x" style="font-size: 23px;"></i> <span class="d-block text-sm">{{state.communityDetail.likeCnt}}</span></div>
          </div>
        </div> 
      </div>
    </div>

    <!-- community_detail -->
    <!-- <p>{{ state.communityDetail }}</p> -->
    <!--본문 내용-->
    <div class="card row-hover pos-relative mt-2 py-3 px-3 row-lg-4" id="maintext">
      <div class="row align-items-left" style="font-size: 25px">
        {{state.communityDetail.description}}
      </div>
    </div>
    
    <!-- 게시글 공감 / 신고 버튼 -->
    <div class="detailbutton row-hover pos-relative py-3 px-3 mt-2 row-lg-4">
      <div class="row align-items-center">

        <div class="col-md-4"></div>
        
        <div class="col-md-2" @click="clickCommunityLike">
          <div v-if="state.communityDetail.like" class="form-control form-control-md col-lg-2" style="cursor: pointer;">
            <div class="align-items-center" id="likebtn">
              <span><i class="ion-ios-heart icon-1x"></i> 공감 취소</span>
            </div>
          </div>
          <div v-else class="form-control form-control-md col-lg-2" id="likebtn">
            <span><i class="ion-ios-heart-outline icon-1x"></i> 공감 누르기</span>
          </div>
        </div>

        <div class="col-md-2">
          <button @click="state.reportBool = !state.reportBool" class="custom-form-control-red form-control-md col-lg-2" data-bs-toggle="modal" data-bs-target="#exampleModal2" id="input">신고</button>
        </div>
        <!-- 신고 모달 -->
        <div class="modal fade" id="exampleModal2" v-if="state.reportBool" tabindex="-1" aria-labelledby="exampleModalLabel2" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered" >
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel2">신고하기</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                <form>
                 <textarea
                    cols="30"
                    rows="5"
                    v-model="state.reportMsg"
                    placeholder="신고 내용을 작성해주세요."
                  ></textarea
                  ><br />
                </form>
              </div>
              <div class="modal-footer">
                <button class="custom-form-control-red" type="button" @click="clickCommunityReportDetail">보내기</button> 
                <!-- <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Select</button> -->
              </div>
            </div>
          </div>
        </div>
        <!-- <div v-if="state.reportBool">
          <textarea
            cols="30"
            rows="5"
            v-model="state.reportMsg"
            placeholder="신고 내용을 작성해주세요."
          ></textarea
          ><br />
          <button type="button" @click="clickCommunityReportDetail">보내기</button>
        </div> -->

        <div class="col-md-4"></div>

      </div>
    </div>

    <hr>

    <!-- 게시글에 대한 댓글 -->
    <div class="mt-5">
      <comment-view
        :userInfo="state.userInfo"
        :communityDetail="state.communityDetail"
      >
      </comment-view>
    
      <!-- 작성자일 때 나오는 수정, 삭제 버튼 -->
      <!-- <div v-if="state.userInfo.id == state.communityDetail.user_id">
        <button type="button" @click="clickModifyCommunity">
          수정하기
        </button>
        <button type="button" @click="clickDeleteCommunity">
          삭제하기
        </button>
      </div> -->
    </div>
    <br>
    <br>
  </div>
</div>
</template>

<script>
import MainHeader from "@/views/main/components/MainHeader.vue";

import { computed, reactive, onMounted } from "vue"
import { useStore } from "vuex"
import { useRouter } from "vue-router"
import CommentView from './CommentView.vue'

export default {
  name: "CommunityDetail",
  components: {
    MainHeader,
    CommentView,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      userInfo: computed(() => store.getters["root/userInfo"]),
      communityDetail: computed(() => store.getters["root/communityDetail"]),
      communityLike: computed(() => store.getters["root/communityLike"]),
      reportBool: false,
      reportMsg: "",
    });

    onMounted(() => {
      window.scrollTo(0, 0);
    })

    const clickModifyCommunity = function () {
      router.push({
        name: 'CommunityModify',
        params: {
          community_id: state.communityDetail.community_id
        }
      })
    }

    const clickDeleteCommunity = async function () {
      const body = {
        user_id: state.userInfo.id, community_id: state.communityDetail.community_id
      }
      await store.dispatch("root/communityDeleteDetail", body)
      await router.push({ name: 'Community' })
    };

    const clickCommunityReportDetail = function () {
      store.dispatch("root/reportDetail", {
        category: 'community',
        reportUserId: state.communityDetail.user_id,
        userId: state.userInfo.id,
        reportMsg: state.reportMsg,
      })
    }

    const clickCommunityLike = async function () {
      await store.dispatch("root/communityModifyLike", {
        userId: state.userInfo.id,
        communityId: state.communityDetail.community_id
      })
      await store.dispatch('root/communityGetDetail', {
        user_id: state.userInfo.id,
        community_id: state.communityDetail.community_id
      })
    }

    const goToCommunity = function() {
      router.push({ name: "Community" });
    }

    return {
      state, onMounted,
      router,
      clickModifyCommunity,
      clickDeleteCommunity,
      clickCommunityReportDetail,
      clickCommunityLike,
      goToCommunity,
    };
  },
};
</script>

<style scoped lang="scss">
.detailheader {
    position: relative;
    display: flex;
    flex-direction: column;
    min-width: 0;
    word-wrap: break-word;
    background-color: white;
    background-clip: border-box;
    border-top: 2px solid black;
    border-bottom: 2px solid rgba(0,0,0,.125);
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
  height: 500px;
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
    border-left:0;
    border-right:0;
}

#leftalign {
  text-align: left;
}

#likebtn:hover {
  font-weight: bolder;
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
    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
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
    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}

textarea {
    width: 100%;
    height: 150px;
    border: none;
    resize: none;
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
    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}

</style>
