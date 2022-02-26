<template>
  <div class="container"> 
    <link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet">
    
    <!--페이지 헤더-->
    <div class="row inner-main-header" style="margin-top: 5%">
      <!-- 목록/이전글/다음글 -->
      <div class="row mb-2">
        <!-- <span class="dropdown col-lg-3 row text-center op-7"> 
          <div class="col px-1">
            <button @click="goToCommunity">목록</button>
          </div>
          <div class="col px-1">
            <button>이전글</button>
          </div>
          <div class="col px-1">
            <button>다음글</button>
          </div>
        </span> -->
      <div class="col-7"></div>
      <!-- 해당 글의 작성자일 경우, 수정, 삭제 버튼이 보임-->
        <div class="col-1">
          <!-- <span v-if="state.userInfo.id == state.communityDetail.user_id">
            <div>
              <button type="button" @click="clickModifyCommunity">
                수정하기
              </button>
            </div>
          </span> -->
        </div>
        
        <div class="col-1">
          <!-- <span v-if="state.userInfo.id == state.communityDetail.user_id">
            <div>
              <button type="button" @click="clickDeleteCommunity">
                삭제하기
              </button>
            </div>
          </span> -->
        </div>

      </div>
    </div>
    <!--본문 헤더-->
    <div class="detailheader row-hover pos-relative py-3 px-3 row-lg-4">
      <div class="row align-items-center">
        <div class="col-md-10" id="leftalign">
          <h3>
            <input type="text" id="title" v-model="data.title"
            >
          </h3>
        </div>
      </div>
      <div class="row align-items-center">
        <div class="col-md-8 op-7" id="leftalign">
          <h5>{{state.communityDetail.userNickname}} | 1999-99-99</h5>
        </div>
        <!-- <div class="col-md-4 op-7" id="leftalign">
          <h5>1999-99-99</h5>
        </div> -->
        <div class="col-md-4 op-7">
          <div class="row"> 
            <!-- <div class="col px-1"> <i class="ion-ios-eye-outline icon-1x"></i> <span class="d-block text-sm">{{state.communityDetail.viewCnt}}</span></div>
            <div v-if="state.communityDetail.like" class="col px-1"> <i class="ion-ios-heart icon-1x"></i> <span class="d-block text-sm">{{state.communityDetail.likeCnt}}</span></div>
            <div v-else class="col px-1"> <i class="ion-ios-heart-outline icon-1x"></i> <span class="d-block text-sm">{{state.communityDetail.likeCnt}}</span></div> -->
          </div>
        </div> 
      </div>
    </div>

    <!--본문 내용-->
    <div class="card row-hover pos-relative mt-2 py-3 px-3 row-lg-4" id="maintext">
      <div class="row align-items-left">
        <textarea class="form-control" id="message" v-model="data.description">
        </textarea>
        
        <!-- {{state.communityDetail.description}} -->
      </div>
    </div>

    <hr>
    <!--footer-->
    <div class="communityfooter row align-items-center">
      <div class="col-5"></div>
      <div class="col-1">
        <button @click="communityModifyDetail" class="form-control form-control-md">수정</button>
      </div>
      <div class="col-1">
        <button @click="goToCommunityDetail" class="form-control form-control-md">취소</button>
      </div>
    </div>

  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'CommunityModify',
  setup() {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      userInfo: computed(() => store.getters['root/userInfo']),
      communityDetail: computed(() => store.getters["root/communityDetail"]),
    })
    const data = reactive({
      title: state.communityDetail.title,
      description: state.communityDetail.description,
    })
    const communityModifyDetail = async function () {
      await store.dispatch('root/communityModifyDetail',
      {
       user_id: state.userInfo.id,
       community_id: state.communityDetail.community_id,
       title: data.title,
       description: data.description
      })
      await router.push({
        name: 'CommunityDetail',
        params: {
          community_id: state.communityDetail.community_id
        }
      })
    }

    const goToCommunityDetail = function() {
      router.push({ name: "CommunityDetail", params: state.communityDetail.community_id});
    }

    return { state, data, communityModifyDetail, goToCommunityDetail }
  }
}
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
  height: 400px;
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

textarea {
    width: 100%;
    height: 400px;
    border: none;
    resize: none;
}

.communityfooter {
  position: sticky;
}

</style>