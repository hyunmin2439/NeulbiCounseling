import state from "./state";

export function MAIN_EMAIL_BOOL(state, data) {
  state.mainEmailBool = data
}

export function MAIN_NICKNAME_BOOL(state, data) {
  state.mainNicknameBool= data
}

export function MAIN_IS_SIGNIN(state, data) {
  state.mainIsSignIn = data;
}

export function GET_USER_INFO(state, userInfo) {
  state.userInfo = userInfo;
}

export function PROFILE_GET_NICKNAME(state, data) {
  state.profileNicknameBool = data;
}

export function PROFILE_GET_CONSULTANT_PROFILE(state, data) {
  state.profileConsultantProfile = []
  if (data == null) {
    state.profileConsultantProfile = {
      nickname: data,
      pointTot: data,
      topicCategoryName: data,
      description: data,
      consultingCnt: data
    }
  } else {
    state.profileConsultantProfile = {
      nickname: data.nickname,
      pointTot: data.pointTot,
      topicCategoryName: data.topicCategoryName,
      description: data.description,
      consultingCnt: data.consultingCnt
    }
  }
}

export function PROFILE_CONSULTANT_LIKE(state, data) {
  state.profileConsultantLastPageNum = data.totalPages
  state.profileConsultantLike = []
  state.profileConsultantLike = data.content
}

export function PROFILE_GET_HISTORY_CONFESSION_MEETING(state, data) {
  state.profileHistoryConfessionLastPageNum = data.totalPages
  state.profileHistoryConfession = []
  let min = Math.min(3, data.content.length)
  for (let i = 0; i < min; i++) {
    state.profileHistoryConfession.push({
      nickname: data.content[i].userNickname,
      userMaskId: data.content[i].userMaskId,
      userProfileImg: data.content[i].userProfileImg,
      topicCategoryId: data.content[i].topicCategoryId,
      description: data.content[i].meeting.description,
      title: data.content[i].meeting.title,
      createdDate: data.content[i].meetingHistory.createdDate,
      endDate: data.content[i].meetingHistory.modifiedDate,
      participants: data.content[i].meeting.participants
    })
  }
}

export function PROFILE_GET_HISTORY_ADVICE_MEETING(state, data) {
  state.profileHistoryAdviceLastPageNum = data.totalPages
  state.profileHistoryAdvice = []
  let min = Math.min(3, data.content.length)
  for (let i = 0; i < min; i++) {
    state.profileHistoryAdvice.push({
      nickname: data.content[i].userNickname,
      userMaskId: data.content[i].userMaskId,
      userProfileImg: data.content[i].userProfileImg,
      topicCategoryId: data.content[i].topicCategoryId,
      createdDate: data.content[i].meetingHistory.createdDate,
      endDate: data.content[i].meeting.callEndTime,
    })
  }
}

export function PROFILE_GET_HISTORY_RECEIVED_REVIEW(state, data) {
  if (data=="") {
    state.profileHistoryReceivedReview = data
  state.profileHistoryReceivedReviewLastPageNum = data
  } else {
    state.profileHistoryReceivedReview = data.content
    state.profileHistoryReceivedReviewLastPageNum = data.totalPages
  }
}

export function PROFILE_GET_HISTORY_WRITTEN_REVIEW(state, data) {
  state.profileHistoryWrittenReview = data.content
  state.profileHistoryWrittenReviewLastPageNum = data.totalPages
}

export function PROFILE_GET_HISTORY_COMMUNITY(state, data) {
  state.profileHistoryCommunityLastPageNum = data.totalPages
  state.profileHistoryCommunity = data.content
}

export function PROFILE_GET_HISTORY_COMMENT(state, data) {
  state.profileHistoryComment = data.content
  state.profileHistoryCommentLastPageNum = data.totalPages
}

export function CONFESSION_GET_LAST_PAGE_NUM(state, data) {
  state.confessionLastPageNum = 0
  state.confessionLastPageNum = data
}

export function CONFESSION_GET_CONSULTANT_LIST(state, data) {
  state.confessionMeetingList = []
  let min = Math.min(6, data.length)
  for (let i = 0; i < min; i++) {
    state.confessionMeetingList.push({
      ownerId: data[i].ownerId,
      ownerNickname: data[i].ownerNickname,
      title: data[i].title,
      description: data[i].description,
      meetingId: data[i].meetingId,
      participants: data[i].participants,
      currJoinParticipants: data[i].currJoinParticipants,
      topicCategoryId: data[i].topicCategoryId,
      topicCategoryName: data[i].topicCategoryName,
      maskId: data[i].maskId,
      backId: data[i].backId,
      profileImg: data[i].profileImg
    })
  }
}

export function CONFESSION_CREATE_MEETING_INFO(state, data) {
  state.confessionMeetingInfo = []
  state.confessionMeetingInfo.push({
    meetingId: data.meeting.id,
    ownerId: data.ownerId,
    ownerNickname: data.ownerNickname,
    topicCategoryId: data.topicCategoryId,
    title: data.meeting.title,
    description: data.meeting.description,
    isActive: data.meeting.active,
    participants: data.meeting.participants
  })
  state.confessionMeetingInfo = state.confessionMeetingInfo[0]
}

export function CONFESSION_REVIEW_LIST(state, data) {
  state.confessionReviewList = []
  state.confessionReviewList = data
}

export function ADVICE_GET_RANK_LIST(state, data) {
  state.adviceRankList = []
  let min = Math.min(3, data.length)
  for (let i = 0; i < min; i++) {
    state.adviceRankList.push({
      id: data[i].id,
      nickname: data[i].nickname,
      pointTot: data[i].pointTot,
      profileImg: data[i].profileImg,
    })
  }
}

export function ADVICE_GET_LAST_PAGE_NUM(state, data) {
  state.adviceLastPageNum = 0
  state.adviceLastPageNum = data
}

export function ADVICE_GET_CONSULTANT_LIST(state, data) {
  state.adviceConsultantList = []
  let min = Math.min(6, data.length)
  for (let i = 0; i < min; i++) {
    state.adviceConsultantList.push({
      id: data[i].id,
      nickname: data[i].nickname,
      profileImg: data[i].profileImg,
      pointTot: data[i].pointTot,
      topicCategoryName: data[i].topicCategoryName,
      description: data[i].description,
      consultingCnt: data[i].consultingCnt,
      favConsultant: data[i].favConsultant,
      maskId: data[i].maskId,
      backId: data[i].backId,
    })
  }
}

export function COMMUNITY_GET_COMMUNITY_LIST(state, data) {
  state.communityList = data
}

export function COMMUNITY_GET_LAST_PAGE_NUM(state, data) {
  state.communityLastPageNum = 0
  state.communityLastPageNum = data
}

export function COMMUNITY_GET_DETAIL(state, payload) {
  state.communityComment = payload.commentListRes
  state.communityDetail = {
    description: payload.description,
    community_id: payload.id,
    like: payload.like,
    likeCnt: payload.likeCnt,
    title: payload.title,
    user_id: payload.userId,
    userNickname: payload.userNickname,
    viewCnt: payload.viewCnt,
    createdDate: payload.createdDate
  }
}

export function CHATROOM_GET_LIST(state, data) {
  state.chatRoomList = data
}

export function CHATROOM_GET_DETAIL_ID(state, data) {
  state.chatRoomId = data
}

export function CHATROOM_GET_DETAIL_NICKNAME(state, data) {
  state.chatRoomNickname = data
}

export function CHATROOM_GET_DETAIL_MESSAGE(state, data) {
  state.chatRoomMessage = data
}

export function CHATROOM_GET_USER_LIST(state, data) {
  state.chatRoomUserList = data
}

export function ADVICE_CREATE_MEETING_INFO(state, data) {
  state.adviceMeetingInfo = []
  state.adviceMeetingInfo.push({
    meetingId: data.meeting.id,
    ownerId: data.ownerId,
    ownerNickname: data.ownerNickname,
    topicCategoryId: data.topicCategoryId,
    title: data.meeting.title,
    description: data.meeting.description,
    isActive: data.meeting.active,
    participants: data.meeting.participants
  })
  state.adviceMeetingInfo = state.adviceMeetingInfo[0]
}

export function CHATROOM_GET_MEETING_IS_ACTIVE(state, data) {
  state.meetingIsActive = false
  state.meetingIsActive = data
}

export function MEETING_PARTICIPANT_LIMIT(state, data) {
  state.meetingParticipantLimit = true
  console.log(state.meetingParticipantLimit, 'limit')
  state.meetingParticipantLimit = data
}

export function NOTICE_GET_LAST_PAGE_NUM(state, data) {
  state.noticeLastPageNum = 0
  state.noticeLastPageNum = data
}

export function NOTICE_GET_NOTICE_LIST(state, data) {
  state.noticeList = []
  state.noticeList = data
}

export function NOTICE_GET_NOTICE_DETAIL(state, data) {
  state.noticeDetail = data
}

export function QNA_GET_LAST_PAGE_NUM(state, data) {
  state.qnaLastPageNum = 0
  state.qnaLastPageNum = data
}

export function QNA_GET_QUESTION_LIST(state, data) {
  state.qnaList = []
  state.qnaList = data
}

export function QNA_GET_QUESTION_DETAIL(state, data) {
  state.qnaDetail = data
}

export function QNA_GET_QUESTION_ANSWER(state, data) {
  state.qnaAnswerList = []
  let min = Math.min(6, data.length)
  for (let i = 0; i < min; i++) {
    state.qnaAnswerList.push({
      answerId: data[i].answerId,
      date: data[i].date,
      description: data[i].description,
      userId: data[i].userId,
    })
  }
}