<template>
  <div>
    <!-- 댓글 작성 -->
    <textarea
      cols="30"
      rows="2"
      v-model="state.commentDescription"
      placeholder="댓글을 작성해주세요."
    ></textarea>
    <br />
    <button type="button" @click="clickCreateComment(0)">등록</button>

    <!-- 댓글 보여주기 -->
    <div v-for="(comment, idx) in state.communityComment" :key=idx>
      <div v-if="comment.layer==0"><h3>{{ comment }}</h3>
        <span v-if="state.userInfo.id === comment.userId">
          <!-- 댓글 수정부분 -->
          <button @click="clickIsUpdate(idx)" v-if="state.commentIsUpdateNumber!=idx">수정</button>
          <div v-if="idx==state.commentIsUpdateNumber">
            <textarea
              cols="30"
              rows="2"
              v-model="comment.description"
              placeholder="댓글을 작성해주세요."
            ></textarea>
            <button @click="clickModifyComment(comment)">수정</button>
          </div>
          <!-- 댓글 삭제부분 -->
          <button @click="clickDeleteComment(comment.commentId)">삭제</button>
        </span>
        <!-- 대댓글 보여주는 부분 -->
        <button @click="clickCommentIsComment(idx, comment.groupNum)" v-if="state.commentIsCommentNumber!=idx">더보기</button>
        <div v-if="idx===state.commentIsCommentNumber">
          <div v-for="(coComment, index) in state.communityComment" :key=index>
            <div v-if="coComment.layer==1&coComment.groupNum==state.commentGroupNum">{{ coComment }}
              <!-- 대댓글 수정 부분 -->
              <span v-if="state.userInfo.id === coComment.userId">
                <button @click="clickCommentUpdate(index)" v-if="state.commentIsCommentUpdateNumber!=index">수정</button>
                <div v-if="index==state.commentIsCommentUpdateNumber">
                  <textarea
                    cols="30"
                    rows="1"
                    v-model="coComment.description"
                    placeholder="댓글을 작성해주세요."
                  ></textarea>
                  <button @click="clickModifyComment(coComment)">수정</button>
                </div>
                <!-- 대댓글 삭제 부분 -->
                <button @click="clickDeleteComment(coComment.commentId)">삭제</button>
              </span>
            </div>
          </div>
          <!-- 대댓글 작성부분 -->
          <input type="text" v-model="state.commentCommentDescription">
          <button @click="clickCreateComment(comment.commentId)">대댓글 작성</button>
          <button @click="clickCommentIsComment(-1, '')">더보기 닫기</button>
          <div></div>
        </div>
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

<style>

</style>
