<template>
  <div>
    <!-- 댓글 작성 -->
    <div class="newcommnet mt-3">
      <form>
        <div class="mb-3">
          <h5 class="pull-left" id="leftalign">총 {{state.communityComment.length}}개의 댓글이 있습니다.</h5>
        </div>
        <fieldset>
            <div class="row align-items-center mt-3">
                <div class="col-1">
                  <div class="img-center">
                  <img :src="require('@/assets/mask/mask'+state.userInfo.maskId+'.png')">
                  <!-- <img src="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxNzAxMTRfMjYy%2FMDAxNDg0MzcxOTkxNzA4._N73NTpWleCLp8M6gXR8vpdDAZoAQ2mTJLimKBYFtRwg.5LEqnsukFugxlrTdlYk5hkxEKoVdUbTVsjL6gqJ03vIg.PNG.koomarin%2F%253F%253F%253F%253F%257B%253F_%253F%253F%253F%253F%253F%253F%253F.png&type=sc960_832" alt="" /> -->
                  </div>
                  <!-- <img class="img-responsive" src="https://bootdey.com/img/Content/avatar/avatar1.png" alt=""> -->
                </div>
                <div class="form-group col-9">
                  <textarea class="form-control" v-model="state.commentDescription" id="message" placeholder="댓글을 작성해주세요" required=""></textarea>
                </div>
                <div class="col-2">
                  <button type="button" @click="clickCreateComment(0)" class="btn btn-normal">작성</button>
                </div>
            </div>  	
        </fieldset>
      </form>
    </div>
    <br>
    <hr>

    <!-- 댓글 보여주기 -->
    <div v-for="(comment, idx) in state.communityComment" :key=idx class="card row-hover pos-relative" id="board-style1"> 
      <div v-if="comment.layer==0" class="row align-items-center py-1 px-3">
        <!-- {{state.communityComment}} -->
        <div class="col-1">
          <!-- {{comment.maskId}} -->
          <img :src="require('@/assets/mask/mask'+comment.maskId+'.png')">
          <!-- <img src="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxNzAxMTRfMjYy%2FMDAxNDg0MzcxOTkxNzA4._N73NTpWleCLp8M6gXR8vpdDAZoAQ2mTJLimKBYFtRwg.5LEqnsukFugxlrTdlYk5hkxEKoVdUbTVsjL6gqJ03vIg.PNG.koomarin%2F%253F%253F%253F%253F%257B%253F_%253F%253F%253F%253F%253F%253F%253F.png&type=sc960_832" alt="" /> -->
        </div>
        <div class="col-8" id="leftalign">
          <div>
            {{comment.userNickname}}
            <!-- <span>2주 전</span> -->
          </div>
          <div class="mt-2">
            <div v-if="idx!=state.commentIsUpdateNumber">
              <h5>{{comment.description}}</h5>
              <textarea class="form-control" v-model="comment.description" id="message" placeholder="댓글을 작성해주세요" required="" style="display: none"></textarea>
            </div>
        
            <div v-if="idx==state.commentIsUpdateNumber">
              <textarea class="form-control" v-model="comment.description" id="message" placeholder="댓글을 작성해주세요" required=""></textarea>
            </div>
          </div>
          <!--대댓글에 관한-->
          <span @click="clickCommentIsComment(idx, comment.groupNum)" v-if="state.commentIsCommentNumber!=idx" id="cocomment">
            <span style="cursor: pointer;"><i class="ion-arrow-down-b"></i> 답글 보기</span>
          </span>
          
        </div>
        <div class="col-1"></div>
        <!--root 댓글의 수정/삭제-->
        <div class="col-1" v-if="state.userInfo.id === comment.userId">
          <button class="form-control form-control-md col-lg-2" @click="clickIsUpdate(idx)" v-if="state.commentIsUpdateNumber!=idx">수정</button>
          <div v-if="idx==state.commentIsUpdateNumber">
            <!-- <textarea class="form-control" v-model="comment.description" id="message" placeholder="댓글을 작성해주세요" required=""></textarea> -->
            <button class="form-control form-control-md col-lg-2" @click="clickModifyComment(comment)">수정</button>
          </div>
        </div>

        <div class="col-1" v-if="state.userInfo.id === comment.userId">
            <button class="custom-form-control-red form-control-md col-lg-2" @click="clickDeleteComment(comment.commentId)">삭제</button>
        </div>
        <hr class="mt-3">
      </div>
      
      <!-- <hr> -->
      <div v-if="idx===state.commentIsCommentNumber" class="container">
        <div v-for="(coComment, index) in state.communityComment" :key=index>
          <div v-if="coComment.layer==1&coComment.groupNum==state.commentGroupNum" class="row align-items-center mb-4">
            <!-- {{ coComment }} -->
            <div class="col-1">L</div>
            <div class="col-1" id="customimg">
              <img :src="require('@/assets/mask/mask'+coComment.maskId+'.png')">
              <!-- <img :src="require('@/assets/mask/mask'+state.userInfo.maskId+'.png')"> -->
            </div>
            <div class="col-6" id="leftalign">
              <div>
                {{ coComment.userNickname }}
                <!-- <span>방금 전</span> -->
              </div>
              <div class="mt-2">
                <div v-if="index!=state.commentIsCommentUpdateNumber">
                  <h5>{{ coComment.description }}</h5>
                  <textarea class="form-control" v-model="coComment.description" id="message" placeholder="댓글을 작성해주세요" required="" style="display: none"></textarea>
                </div>
            
                <div v-if="index==state.commentIsCommentUpdateNumber">
                  <textarea class="form-control" v-model="coComment.description" id="message" placeholder="댓글을 작성해주세요" required=""></textarea>
                </div>
                
              </div>
            </div>
            <!--대댓글 수정/삭제하기-->
            <div class="col-1"></div>
            <div class="col-1" v-if="state.userInfo.id === coComment.userId">
              <button class="form-control form-control-md col-lg-2" @click="clickCommentUpdate(index)" v-if="state.commentIsCommentUpdateNumber!=index">수정</button>
              <div v-if="index==state.commentIsCommentUpdateNumber">
                <!-- <textarea class="form-control" v-model="coComment.description" id="message" placeholder="댓글을 작성해주세요" required=""></textarea> -->
                <button @click="clickModifyComment(coComment)" class="form-control form-control-md col-lg-2" id="input">수정</button>
                </div>
            </div>
                
            <div class="col-1" v-if="state.userInfo.id === coComment.userId">
                <button class="custom-form-control-red form-control-md col-lg-2" @click="clickDeleteComment(coComment.commentId)">삭제</button>
            </div>
            <br><br>
        </div>
      </div>

        <!-- 대댓글 작성부분 -->
        <div class="newcommnet">
          <form>
            <fieldset>
                <div class="row">
                    <div class="col-2 text-center">
                      <img :src="require('@/assets/mask/mask'+state.userInfo.maskId+'.png')">
                      <!-- <img src="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxNzAxMTRfMjYy%2FMDAxNDg0MzcxOTkxNzA4._N73NTpWleCLp8M6gXR8vpdDAZoAQ2mTJLimKBYFtRwg.5LEqnsukFugxlrTdlYk5hkxEKoVdUbTVsjL6gqJ03vIg.PNG.koomarin%2F%253F%253F%253F%253F%257B%253F_%253F%253F%253F%253F%253F%253F%253F.png&type=sc960_832" alt="" /> -->
                      <!-- <img class="img-responsive" src="https://bootdey.com/img/Content/avatar/avatar1.png" alt=""> -->
                    </div>
                    <div class="form-group col-8">
                      <textarea class="form-control" v-model="state.commentCommentDescription" id="message" placeholder="댓글을 작성해주세요" required=""></textarea>
                    </div>
                    <div class="col-1">
                      <button type="button" @click="clickCreateComment(comment.commentId)" class="btn btn-normal">작성</button>
                    </div>
                    <div class="col-1">
                      <button type="button" @click="clickCommentIsComment(-1, '')" class="btn btn-normal">닫기</button> 
                    </div>
                </div> 
                <hr>	
            </fieldset>
          </form>
        </div>

        <!-- <div class="row">
          <div class="form-control col-8">
            <textarea class="form-control" v-model="state.commentCommentDescription" id="message" placeholder="댓글을 작성해주세요" required=""></textarea>
          </div>
          <div class="col-2">
            <span @click="clickCreateComment(comment.commentId)">대댓글 작성</span>
          </div>
        </div> -->
        
        
        <!-- <input type="text" v-model="state.commentCommentDescription"> -->
        <!-- <span @click="clickCommentIsComment(-1, '')">닫기</span> -->
        <div></div>
      </div>
      
      
    </div>

    

  </div>
</template>

<script>
import { computed, reactive } from "vue"
import { useStore } from "vuex"

export default {
  name: 'CommentView',
  props: {
    userInfo: Object,
    communityDetail: Object,
  },
  setup (props) {
    const store = useStore()
    const state = reactive({
      userInfo: props.userInfo,
      communityDetail: props.communityDetail,
      commentDescription: "",
      communityComment: computed(() => store.getters["root/communityComment"]),
      commentIsUpdateNumber: -1,
      commentIsCommentNumber: -1,
      commentGroupNum: '',
      commentIsCommentUpdateNumber: -1,
      commentCommentDescription: '',
    })

    const clickCreateComment = async function (commentId) {
      await store.dispatch("root/communityCreateComment", {
        communityId: state.communityDetail.community_id,
        userId: state.userInfo.id,
        description: commentId===0 ? state.commentDescription : state.commentCommentDescription,
        groupNum: commentId
      })
      state.commentDescription = ""
      state.commentCommentDescription = ""
    }

    const clickIsUpdate = function (idx) {
      if (state.commentIsUpdateNumber === -1) {
        state.commentIsUpdateNumber = idx
      } else {
        state.commentIsUpdateNumber = -1
      }
    }

    const clickModifyComment = async function (comment) {
      await store.dispatch('root/communityModifyComment',{
        userId: state.userInfo.id,
        commentId: comment.commentId,
        community_id: state.communityDetail.community_id,
        description: comment.description
      })
      state.commentIsUpdateNumber = -1
      state.commentIsCommentUpdateNumber = -1
    }

    const clickDeleteComment = async function (commentId) {
      await store.dispatch("root/communityDeleteComment",{
        userId: state.userInfo.id,
        commentId: commentId,
        community_id: state.communityDetail.community_id
      })
    }

    const clickCommentIsComment = function (idx, groupNum) {
      if (state.commentIsCommentNumber === -1) {
        state.commentIsCommentNumber = idx
        state.commentGroupNum = groupNum
      } else {
        state.commentIsCommentNumber = idx
        state.commentGroupNum = groupNum
      }
      
    }

    const clickCommentUpdate = function (index) {
      if (state.commentIsCommentUpdateNumber === -1) {
        state.commentIsCommentUpdateNumber = index
      } else {
        state.commentIsCommentUpdateNumber = -1
      }
    }

    

    return {
      state,
      clickCreateComment,
      clickIsUpdate,
      clickModifyComment,
      clickDeleteComment,
      clickCommentIsComment,
      clickCommentUpdate,
    }
  }
}
</script>

<style scoped lang="scss">
#leftalign {
  text-align: left;
}

img {
  float: right;
  width: 40px;
  height: 40px;
  // margin-left: 15px;
  margin-right: 20%;
  border-radius: 50%;
  // -o-object-fit: cover;
  // object-fit: cover;
}

#customimg {
  float: center;
  width: 40px;
  height: 40px;
  // margin-left: 15px;
  margin-right: 1%;
  border-radius: 50%;
  // -o-object-fit: cover;
  // object-fit: cover;
}

#img-center {
  text-align: center;
  position: absolute;
  top: 0;
  right: -200%;
  bottom: 0;
  left: -200%;
}

#board-style1 {
  border-top: 0;
  border-bottom: 0;
  border-left: 0;
  border-right: 0;
}

#cocomment {
  color: blue;
  // text-decoration: underline;
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