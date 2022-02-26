import $axios from "axios";

export async function mainSingUp({ state }, payload) {
  const url = "/user/signup";
  let body = payload;
  await $axios.post(url, body)
    .then((res) => {
      alert('회원가입에 성공했습니다.')
    })
    .catch((err) => {
      console.log(err, '회원가입')
      alert('회원가입에 실패했습니다.')
    })
}

export async function mainUserInfoCheck({ commit }, payload) {
  const url = "/user/check";
  let body = payload;
  console.log(url, body);
  await $axios
    .post(url, body)
    .then((res) => {
      console.log("성공", res);
      if (res.data.message === "Duplicated Email") {
        commit("MAIN_EMAIL_BOOL", false);
      } else if (res.data.message === "Success Email") {
        commit("MAIN_EMAIL_BOOL", true);
      } else if (res.data.message === "Duplicated Nickname") {
        commit("MAIN_NICKNAME_BOOL", false);
      } else if (res.data.message === "Success Nickname") {
        commit("MAIN_NICKNAME_BOOL", true);
      }
    })
    .catch((err) => {
      console.log(err, "유저정보 중복체크");
    });
}

export async function mainSignIn({ state, commit, dispatch }, payload) {
  const url = "/user/signin";
  let body = payload;
  await $axios
    .post(url, body)
    .then(async (res) => {
      if (res.data.accessToken) {
        let token = res.data.accessToken;
        commit("MAIN_IS_SIGNIN", true);
        localStorage.setItem("jwt", token);
        if (state.mainIsSignIn) {
          await dispatch("userGetInfo", token);
        } else {
          console.log("유저 정보 없음");
        }
      } else {
        commit("MAIN_IS_SIGNIN", false);
      }
    })
    .catch((err) => {
      alert('로그인에 실패했습니다.\n 아이디와 비밀번호를 확인해주세요.')
      console.log(err, "로그인");
    });
}

export async function userGetInfo({ commit }, token) {
  const url = `/user/info`;
  await $axios
    .get(url, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    .then((res) => {
      if (res.status === 200) {
        commit("GET_USER_INFO", res.data);
      } else {
        console.log("유저 정보 없음");
      }
    })
    .catch((err) => {
      console.log(err, "유저정보 받기");
    });
}

export function mainFindPassword({ state }, payload) {
  const url = "/user/resetPassword";
  let body = payload;
  $axios
    .post(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "이메일보내기");
    });
}

export async function profileGetNickname({ state, commit }, payload) {
  const url = "/profile/check/nickname";
  let body = payload;
  await $axios
    .post(url, body)
    .then((res) => {
      if (res.data.message === "SUCCESS") {
        commit("PROFILE_GET_NICKNAME", true);
        alert("사용 가능한 닉네임 입니다.")
      } else if (res.data.message === "FAIL") {
        commit("PROFILE_GET_NICKNAME", false);
        alert("동일한 닉네임이 이미 존재합니다.")
      }
    })
    .catch((err) => {
      console.log(err, "프로필 닉네임 조회");
    });
}

export async function profileModifyNickname({ state, commit }, payload) {
  const user_id = payload.user_id;
  const body = { nickname: payload.nickname };
  const url = `/profile/${user_id}/modify/nickname`;
  await $axios
    .put(url, body)
    .then((res) => {
      commit("PROFILE_GET_NICKNAME", false);
      alert('닉네임이 성공적으로 변경되었습니다.')
    })
    .catch((err) => {
      console.log(err, "프로필 닉네임 수정");
    });
}

export async function profileModifyMask({ state }, payload) {
  const user_id = payload.user_id;
  const body = { mask_id: payload.mask_id };
  const url = `/profile/${user_id}/mask`;
  await $axios
    .post(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "프로필 마스크 수정");
    });
}

export async function profileModifyBack({ state }, payload) {
  const user_id = payload.user_id;
  const body = { mask_id: payload.mask_id };
  const url = `/profile/${user_id}/maskBack`;
  await $axios
    .post(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "프로필 마스크 수정");
    });
}

export async function profileModifyProfileImg({ state }, payload) {
  const user_id = payload.user_id;
  const url = `/profile/image/${user_id}`;
  const header = { headers: { "Content-Type": "multipart/form-data" } };

  const formData = new FormData();
  formData.append("profileImg", payload.profile_img);

  await $axios
    .post(url, formData, header)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "프로필 이미지 변경");
    });
}

export async function profileModifyPassword({ state }, payload) {
  const user_id = payload.user_id;
  const url = `/profile/${user_id}/modify/password`;
  const body = { password: payload.password };
  await $axios
    .put(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "프로필 비밀번호 변경");
    });
}

export async function profileResignUser({ state, commit }, payload) {
  const user_id = payload.user_id;
  const url = `/profile/${user_id}/resign`;
  await $axios
    .delete(url)
    .then((res) => {
      commit("MAIN_IS_SIGNIN", false);
      commit("GET_USER_INFO", null);
      localStorage.removeItem("jwt");
    })
    .catch((err) => {
      console.log(err, "회원탈퇴");
    });
}

export async function profileGetConsultantProfile({ state, commit }, payload) {
  const user_id = payload;
  const url = `/profile/${user_id}/consultantProfile`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("PROFILE_GET_CONSULTANT_PROFILE", res.data);
      } else {
        commit("PROFILE_GET_CONSULTANT_PROFILE", 1);
      }
    })
    .catch((err) => {
      console.log(err, "프로필 상담가 정보");
    });
}

export async function profileGetConsultantLike({ state, commit }, payload) {
  const user_id = payload;
  const url = `profile/${user_id}/myConsultant`;
  await $axios
    .get(url)
    .then((res) => {
      console.log(res)
      commit("PROFILE_CONSULTANT_LIKE", res.data);
    })
    .catch((err) => {
      console.log(err, "프로필 내가 찜한 상담가");
    });
}

export async function profileGetConsultantLikePageSearch({ state, commit }, payload) {
  const page = payload.page
  const size = payload.size
  const user_id = payload.user_id;
  const url = `profile/${user_id}/myConsultant?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("PROFILE_CONSULTANT_LIKE", res.data);
    })
    .catch((err) => {
      console.log(err, "프로필 내가 찜한 상담가");
    });
}

export async function profileModifyTopicCategory({ state }, payload) {
  const user_id = payload.id;
  const id = payload.topicCategoryId;
  const url = `/profile/${user_id}/consultantProfile/topicCategory?id=${id}`;
  const body = { id: payload.topicCategoryId };
  await $axios
    .put(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "프로필 상담가 주제 수정");
    });
}

export async function profileModifyDescription({ state }, payload) {
  const user_id = payload.user_id;
  const body = payload.description;
  const url = `profile/${user_id}/consultantProfile/description`;
  await $axios
    .put(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "프로필 상담가 설명 수정");
    });
}

export async function profileGetHistoryConfessionMeeting({ state, commit }, payload) {
  const user_id = payload.user_id;
  const url = `profile/${user_id}/meeting/confession`;
  await $axios
    .get(url)
    .then((res) => {
      commit("PROFILE_GET_HISTORY_CONFESSION_MEETING", res.data.confession);
    })
    .catch((err) => {
      console.log(err, "프로필 미팅 정보 조회");
    });
}

export async function profileGetHistoryConfessionMeetingPageSearch({ state, commit }, payload) {
  const user_id = payload.user_id;
  const page = payload.page
  const size = payload.size
  const url = `profile/${user_id}/meeting/confession?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("PROFILE_GET_HISTORY_CONFESSION_MEETING", res.data.confession);
    })
    .catch((err) => {
      console.log(err, "프로필 미팅 정보 조회");
    });
}

export async function profileGetHistoryAdviceMeeting({ state, commit }, payload) {
  const user_id = payload.user_id;
  const url = `profile/${user_id}/meeting/advice`;
  await $axios
    .get(url)
    .then((res) => {
      console.log(res)
      commit("PROFILE_GET_HISTORY_ADVICE_MEETING", res.data.advice);
    })
    .catch((err) => {
      console.log(err, "프로필 미팅 정보 조회");
    });
}

export async function profileGetHistoryAdviceMeetingPageSearch({ state, commit }, payload) {
  const user_id = payload.user_id;
  const page = payload.page
  const size = payload.size
  const url = `profile/${user_id}/meeting/advice?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("PROFILE_GET_HISTORY_ADVICE_MEETING", res.data.advice);
    })
    .catch((err) => {
      console.log(err, "프로필 미팅 정보 조회");
    });
}

export async function profileGetHistoryReceivedReview({ state, commit }, payload) {
  const user_id = payload.user_id;
  const url = `profile/${user_id}/review/receive`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.data == "") {
        commit("PROFILE_GET_HISTORY_RECEIVED_REVIEW", res.data);
      } else {
        commit("PROFILE_GET_HISTORY_RECEIVED_REVIEW", res.data.receivedReview);
      }
    })
    .catch((err) => {
      console.log(err, "프로필 리뷰 정보 조회");
    });
}

export async function profileGetHistoryReceivedReviewPageSearch({ state, commit }, payload) {
  const user_id = payload.user_id;
  const page = payload.page
  const size = payload.size
  const url = `profile/${user_id}/review/receive?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("PROFILE_GET_HISTORY_RECEIVED_REVIEW", res.data.receivedReview);
    })
    .catch((err) => {
      console.log(err, "프로필 리뷰 정보 조회");
    });
}

export async function profileGetHistoryWrittenReview({ state, commit }, payload) {
  const user_id = payload.user_id;
  const url = `profile/${user_id}/review/written`;
  await $axios
    .get(url)
    .then((res) => {
      commit("PROFILE_GET_HISTORY_WRITTEN_REVIEW", res.data.writtenReview);
    })
    .catch((err) => {
      console.log(err, "프로필 리뷰 정보 조회");
    });
}

export async function profileGetHistoryWrittenReviewPageSearch({ state, commit }, payload) {
  const user_id = payload.user_id;
  const page = payload.page
  const size = payload.size
  const url = `profile/${user_id}/review/written?page=${page}&size=${size}`;
  console.log(url)
  await $axios
    .get(url)
    .then((res) => {
      commit("PROFILE_GET_HISTORY_WRITTEN_REVIEW", res.data.writtenReview);
    })
    .catch((err) => {
      console.log(err, "프로필 리뷰 정보 조회");
    });
}

export async function profileGetHistoryCommunity({ state, commit }, payload) {
  const user_id = payload.user_id;
  const url = `profile/${user_id}/community`;
  await $axios
    .get(url)
    .then((res) => {
      commit("PROFILE_GET_HISTORY_COMMUNITY", res.data);
    })
    .catch((err) => {
      console.log(err, "프로필 작성한 게시글 조회");
    });
}

export async function profileGetHistoryCommunityPageSearch({ state, commit }, payload) {
  const user_id = payload.user_id;
  const page = payload.page
  const size = payload.size
  const url = `profile/${user_id}/community?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("PROFILE_GET_HISTORY_COMMUNITY", res.data);
    })
    .catch((err) => {
      console.log(err, "프로필 작성한 게시글 조회");
    });
}

export async function profileGetHistoryComment({ state, commit }, payload) {
  const user_id = payload.user_id;
  const url = `profile/${user_id}/comment`;
  await $axios
    .get(url)
    .then((res) => {
      commit("PROFILE_GET_HISTORY_COMMENT", res.data);
    })
    .catch((err) => {
      console.log(err, "프로필 작성한 댓글 조회");
    });
}

export async function profileGetHistoryCommentPageSearch({ state, commit }, payload) {
  const user_id = payload.user_id;
  const page = payload.page
  const size = payload.size
  const url = `profile/${user_id}/comment?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("PROFILE_GET_HISTORY_COMMENT", res.data);
    })
    .catch((err) => {
      console.log(err, "프로필 작성한 댓글 조회");
    });
}

export async function confessionGetMeetingList({ state, commit }, payload) {
  const url = "/confession";
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("CONFESSION_GET_LAST_PAGE_NUM", res.data.totalPages);
        commit("CONFESSION_GET_CONSULTANT_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "고해성사 미팅 정보 조회");
    });
}

export async function confessionGetCategoryList({ commit }, payload) {
  const topicCategoryId = payload;
  const url = `/confession/${topicCategoryId}`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("CONFESSION_GET_LAST_PAGE_NUM", res.data.totalPages);
        commit("CONFESSION_GET_CONSULTANT_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "고해성사 주제별 상담가 정보 조회");
    });
}

export async function confessionGetSearchList({ state, commit }, payload) {
  const key = payload.key;
  const value = payload.value;
  const url = `/confession/search/${key}/${value}`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("CONFESSION_GET_LAST_PAGE_NUM", res.data.totalPages);
        commit("CONFESSION_GET_CONSULTANT_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "고해성사 검색");
    });
}

export async function confessionPageSearch({ state, commit }, payload) {
  const page = payload.page;
  const size = payload.size;
  const url = `/confession?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("CONFESSION_GET_CONSULTANT_LIST", res.data.content);
    })
    .catch((err) => {
      console.log(err, "고해성사 페이지 서치?");
    });
}

export async function confessionTopicPageSearch({ state, commit }, payload) {
  const topicCategoryId = payload.topicCategoryId;
  const page = payload.page;
  const size = payload.size;
  const url = `/confession/${topicCategoryId}?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("CONFESSION_GET_CONSULTANT_LIST", res.data.content);
    })
    .catch((err) => {
      console.log(err, "고해성사 페이지 서치?");
    });
}

export async function confessionSearchPageSearch({ state, commit }, payload) {
  const key = payload.key;
  const value = payload.value;
  const size = payload.size;
  const page = payload.page;
  const url = `/confession/search/${key}/${value}?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("CONFESSION_GET_CONSULTANT_LIST", res.data.content);
    })
    .catch((err) => {
      console.log(err, "고해성사 페이지 서치?");
    });
}

export async function confessionCreateMeeting({ state, commit }, payload) {
  const url = `/meeting/create`;
  const body = payload;
  await $axios
    .post(url, body)
    .then((res) => {
      commit("CONFESSION_CREATE_MEETING_INFO", res.data);
      commit("MEETING_PARTICIPANT_LIMIT", true);
    })
    .catch((err) => {
      console.log(err, "고해성사 채팅방 생성");
    });
}

export async function confessionEnterMeeting({ state, commit }, payload) {
  const meeting_id = payload;
  const url = `/meeting/enter/confession/${meeting_id}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("CONFESSION_CREATE_MEETING_INFO", res.data);
    })
    .catch((err) => {
      console.log(err, "고해성사 채팅방 참가");
    });
}

export async function confessionReviewList({ state, commit }, payload) {
  const meeting_id = payload;
  const url = `/meeting/${meeting_id}/list`;
  await $axios
    .get(url)
    .then((res) => {
      commit("CONFESSION_REVIEW_LIST", res.data);
    })
    .catch((err) => {
      console.log(err);
    });
}

export async function adviceGetConsultantList({ commit }, payload) {
  const user_id = payload;
  const url = `/advice/${user_id}`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("ADVICE_GET_LAST_PAGE_NUM", res.data.totalPages);
        commit("ADVICE_GET_CONSULTANT_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "고민상담 상담가 정보 조회");
    });
}

export async function adviceGetRankList({ commit }) {
  const url = `/advice/rank`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("ADVICE_GET_RANK_LIST", res.data);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "고민상담 랭킹");
    });
}

export async function adviceCreateConsultant({ state, dispatch }, payload) {
  const url = `/advice`;
  await $axios
    .post(url, payload)
    .then((res) => {
      if (res.status == 200) {
        console.log(res)
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "고민상담 상담가 등록");
    });
}

export async function adviceGetCategoryList({ commit }, payload) {
  const user_id = payload.user_id;
  const topic_category_id = payload.topic_category_id;
  const url = `/advice/${user_id}/${topic_category_id}`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("ADVICE_GET_LAST_PAGE_NUM", res.data.totalPages);
        commit("ADVICE_GET_CONSULTANT_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "고민상담 주제별 상담가 정보 조회");
    });
}

export async function adviceGetSearchList({ state, commit }, payload) {
  const user_id = payload.user_id;
  const key = payload.key;
  const value = payload.value;
  const url = `/advice/search/${user_id}/${key}/${value}`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("ADVICE_GET_LAST_PAGE_NUM", res.data.totalPages);
        commit("ADVICE_GET_CONSULTANT_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "고민상담 검색");
    });
}

export async function advicePageSearch({ state, commit }, payload) {
  const user_id = payload.user_id;
  const page = payload.page;
  const size = payload.size;
  const url = `/advice/${user_id}?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("ADVICE_GET_CONSULTANT_LIST", res.data.content);
    })
    .catch((err) => {
      console.log(err, "고민상담 페이지 서치?");
    });
}

export async function adviceTopicPageSearch({ state, commit }, payload) {
  const user_id = payload.user_id;
  const topic_category_id = payload.topicCategoryId;
  const page = payload.page;
  const size = payload.size;
  const url = `/advice/${user_id}/${topic_category_id}?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      console.log(res)
      commit("ADVICE_GET_CONSULTANT_LIST", res.data.content);
    })
    .catch((err) => {
      console.log(err, "고민상담 페이지 서치?");
    });
}

export async function adviceSearchPageSearch({ state, commit }, payload) {
  const user_id = payload.user_id;
  const key = payload.key;
  const value = payload.value;
  const size = payload.size;
  const page = payload.page;
  const url = `/advice/search/${user_id}/${key}/${value}?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("ADVICE_GET_CONSULTANT_LIST", res.data.content);
    })
    .catch((err) => {
      console.log(err, "고민상담 페이지 서치?");
    });
}

export async function adviceCreateChatRoom({ commit, dispatch }, payload) {
  const url = `/chatroom/create`;
  const body = payload;
  await $axios
    .post(url, body)
    .then(async (res) => {
      if (res.status == 200) {
        const chatRoom_id = Number(res.data.message);
        await dispatch("chatRoomGetList", body);
        await dispatch("chatRoomGetDetail", {
          user_id: body.userId,
          chatRoom_id: chatRoom_id,
        });
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "채팅방 생성");
    });
}

export async function adviceModifyConsultantLike({ state }, payload) {
  const user_id = payload.user_id;
  const consultant_id = payload.consultant_id;
  const url = `profile/${user_id}/${consultant_id}`;
  await $axios
    .post(url)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err);
    });
}

export async function communityGetCommunityList({ commit }) {
  const url = "/community";
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("COMMUNITY_GET_LAST_PAGE_NUM", res.data.totalPages);
        commit("COMMUNITY_GET_COMMUNITY_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "커뮤니티 게시글 조회");
    });
}

export async function communityCreateCommunity({ state }, payload) {
  const url = "/community";
  let body = payload;
  await $axios
    .post(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "커뮤니티 게시글 생성");
    });
}

export async function communityGetSearchList({ commit }, payload) {
  const url = `community/search/${payload.key}/${payload.value}`;
  // ?page=${payload.page}&size=${payload.size}
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("COMMUNITY_GET_LAST_PAGE_NUM", res.data.totalPages);
        commit("COMMUNITY_GET_COMMUNITY_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "커뮤니티 검색");
    });
}

export async function communityGetSelectList({ state, commit }, payload) {
  const key = payload.key;
  const url = `community/${key}`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("COMMUNITY_GET_LAST_PAGE_NUM", res.data.totalPages);
        commit("COMMUNITY_GET_COMMUNITY_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "커뮤니티 정렬");
    });
}

export async function communityPageSearch({ state, commit }, payload) {
  const page = payload.page;
  const size = payload.size;
  const url = `/community?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("COMMUNITY_GET_LAST_PAGE_NUM", res.data.totalPages);
      commit("COMMUNITY_GET_COMMUNITY_LIST", res.data.content);
    })
    .catch((err) => {
      console.log(err, "고민상담 페이지 서치?");
    });
}

export async function communityTopicPageSearch({ state, commit }, payload) {
  const key = payload.topic;
  const page = payload.page;
  const size = payload.size;
  const url = `/community/${key}?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("COMMUNITY_GET_LAST_PAGE_NUM", res.data.totalPages);
      commit("COMMUNITY_GET_COMMUNITY_LIST", res.data.content);
    })
    .catch((err) => {
      console.log(err, "고민상담 페이지 서치?");
    });
}

export async function communitySearchPageSearch({ state, commit }, payload) {
  const key = payload.key;
  const value = payload.value;
  const page = payload.page;
  const size = payload.size;
  const url = `/community/search/${key}/${value}?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("COMMUNITY_GET_LAST_PAGE_NUM", res.data.totalPages);
      commit("COMMUNITY_GET_COMMUNITY_LIST", res.data.content);
    })
    .catch((err) => {
      console.log(err, "고민상담 페이지 서치?");
    });
}

export async function communityGetDetail({ commit }, payload) {
  const user_id = payload.user_id;
  const community_id = payload.community_id;
  const url = `/community/${community_id}/${user_id}`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("COMMUNITY_GET_DETAIL", res.data);
      }
    })
    .catch((err) => {
      console.log(err, "게시글 상세 정보 조회");
    });
}

export async function communityModifyDetail({ dispatch }, payload) {
  const community_id = payload.community_id;
  const body = {
    communityId: payload.community_id,
    description: payload.description,
    title: payload.title,
    userId: payload.user_id,
  };
  const url = `/community/${community_id}`;
  await $axios
    .put(url, body)
    .then((res) => {
      if (res.status == 200) {
        dispatch("communityGetDetail", {
          user_id: body.userId,
          community_id: body.communityId,
        });
      }
    })
    .catch((err) => {
      console.log(err, "커뮤니티 게시글 수정");
    });
}

export async function communityDeleteDetail({ state }, payload) {
  const community_id = payload.community_id;
  const url = `/community/${community_id}/delete`;
  await $axios
    .delete(url, {
      data: {
        communityId: payload.community_id,
        userId: payload.user_id,
      },
    })
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "커뮤니티 게시글 삭제");
    });
}

export async function communityModifyLike({ state }, payload) {
  const community_id = payload.communityId;
  const body = { userId: payload.userId, communityId: payload.communityId };
  const url = `/community/${community_id}/like`;
  await $axios
    .post(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "커뮤니티 좋아요");
    });
}

export async function communityCreateComment({ state, dispatch }, payload) {
  const body = payload;
  const community_id = payload.communityId;
  const url = `/comment/${community_id}`;
  await $axios
    .post(url, body)
    .then((res) => {
      dispatch("communityGetDetail", {
        user_id: body.userId,
        community_id: body.communityId,
      });
    })
    .catch((err) => {
      console.log(err, "커뮤니티 댓글 생성");
    });
}

export async function communityModifyComment({ state, dispatch }, payload) {
  const community_id = payload.community_id;
  const url = `/comment/${community_id}`;
  const body = {
    userId: payload.userId,
    commentId: payload.commentId,
    description: payload.description,
  };
  await $axios
    .put(url, body)
    .then((res) => {
      dispatch("communityGetDetail", {
        user_id: body.userId,
        community_id: body.community_id,
      });
    })
    .catch((err) => {
      console.log(err, "커뮤니티 댓글 수정");
    });
}

export async function communityDeleteComment({ state, dispatch }, payload) {
  const community_id = payload.community_id;
  const url = `/comment/${community_id}`;
  await $axios
    .delete(url, {
      data: {
        userId: payload.userId,
        commentId: payload.commentId,
      },
    })
    .then((res) => {
      dispatch("communityGetDetail", {
        user_id: payload.userId,
        community_id: payload.community_id,
      });
    })
    .catch((err) => {
      console.log(err, "댓글 삭제");
    });
}

export async function chatRoomGetList({ state, commit, dispatch }, payload) {
  const user_id = payload.userId;
  const url = `chatroom/${user_id}`;
  await $axios
    .get(url)
    .then((res) => {
      console.log(res.data)
      commit("CHATROOM_GET_LIST", res.data);
      commit("CHATROOM_GET_DETAIL_ID", res.data[0].consultantNickName);
      dispatch("chatRoomGetDetail", {
        user_id: state.userInfo.id,
        chatRoom_id: res.data[0].id,
      });
    })
    .catch((err) => {
      console.log(err, "채팅방 목록 조회");
    });
}

export function chatRoomGetDetail({ state, commit }, payload) {
  const user_id = payload.user_id;
  const chatRoom_id = payload.chatRoom_id;
  const url = `chatroom/${user_id}/${chatRoom_id}`;
  $axios
    .get(url)
    .then((res) => {
      commit("CHATROOM_GET_DETAIL_ID", []);
      commit("CHATROOM_GET_DETAIL_ID", chatRoom_id);
      commit("CHATROOM_GET_DETAIL_MESSAGE", []);
      commit("CHATROOM_GET_DETAIL_MESSAGE", res.data.chatMessage);
      commit("CHATROOM_GET_USER_LIST", res.data.userList);
      commit("CHATROOM_GET_MEETING_IS_ACTIVE", res.data.active);
    })
    .catch((err) => {
      console.log(err, "채팅방 메시지 디테일 조회");
    });
}

export async function chatRoomCreateMeeting({ state, commit }, payload) {
  const url = `/meeting/create`;
  const body = payload;
  console.log(url, body);
  await $axios
    .post(url, body)
    .then((res) => {
      commit("ADVICE_CREATE_MEETING_INFO", res.data);
      commit("MEETING_PARTICIPANT_LIMIT", true);
    })
    .catch((err) => {
      console.log(err, "고민상담 채팅방 생성");
    });
}

export async function chatRoomEnterMeeting({ state, commit }, payload) {
  const chatRoom_id = payload;
  const url = `/meeting/enter/${chatRoom_id}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("ADVICE_CREATE_MEETING_INFO", res.data);
    })
    .catch((err) => {
      console.log(err, "고민상담 채팅방 참가");
    });
}

export async function meetingExit({ state }, payload) {
  const meeting_id = payload.meeting_id;
  const user_id = payload.user_id;
  const owner_id = payload.owner_id;
  const url = `/meeting/close/${meeting_id}/${user_id}/${owner_id}`;
  await $axios
    .put(url)
    .then((res) => {
      console.log("meeting 종료", res);
    })
    .catch((err) => {
      console.log(err, "화상채팅 종료");
    });
}

export async function meetingJoinRoom({ state, commit }, payload) {
  const meeting_id = payload.meeting_id;
  const user_id = payload.user_id;
  const url = `/meeting/join/${meeting_id}/${user_id}`;
  await $axios
    .get(url)
    .then((res) => {
      console.log(res, "-==================");
      if (res.data.message === "Success") {
        commit("MEETING_PARTICIPANT_LIMIT", true);
      } else {
        commit("MEETING_PARTICIPANT_LIMIT", false);
        console.log("참가인원 제한");
      }
    })
    .catch((err) => {
      console.log(err, "화상채팅 참가");
    });
}

export async function reportDetail({ state }, payload) {
  const category = payload.category;
  const body = payload;
  const url = `/report/${category}`;
  await $axios
    .post(url, body)
    .then((res) => {
      if (res.status == 200) {
        alert("신고 성공");
      }
    })
    .catch((err) => {
      console.log(err, "신고하기");
    });
}

export async function noticeGetNoticeList({ state, commit }, payload) {
  const url = "/notice";
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("NOTICE_GET_LAST_PAGE_NUM", res.data.totalPages);
        commit("NOTICE_GET_NOTICE_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "공지사항 조회");
    });
}

export async function noticePageSearch({ state, commit }, payload) {
  const size = payload.size;
  const page = payload.page;
  const url = `/notice?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("NOTICE_GET_NOTICE_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "공지사항 페이지 서치");
    });
}

export async function noticeGetNoticeDetail({ state, commit }, payload) {
  const notice_id = payload;
  const url = `/notice/${notice_id}`;
  await $axios
    .get(url)
    .then((res) => {
      commit("NOTICE_GET_NOTICE_DETAIL", res.data);
    })
    .catch((err) => {
      console.log(err, "공지사항 디테일 조회");
    });
}

export async function noticeCreateNotice({ state }, payload) {
  const body = payload;
  const url = `/notice`;
  await $axios
    .post(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "공지사항 생성");
    });
}

export async function noticeModifyNotice({ state }, payload) {
  const body = payload;
  const notice_id = body.noticeId;
  const url = `/notice/${notice_id}`;
  await $axios
    .put(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "공지사항 수정");
    });
}

export async function noticeDeleteNotice({ state }, payload) {
  const notice_id = payload.noticeId;
  const url = `/notice/${notice_id}`;
  await $axios
    .delete(url, {
      data: {
        noticeId: payload.noticeId,
        userId: payload.userId,
      },
    })
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "공지사항 삭제");
    });
}

export async function qnaGetQuestionList({ state, commit }, payload) {
  const user_id = payload;
  const url = `/qna/${user_id}`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("QNA_GET_LAST_PAGE_NUM", res.data.totalPages);
        commit("QNA_GET_QUESTION_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "Q&A 조회");
    });
}

export async function qnaGetSearchQuestionList({ state, commit }, payload) {
  const search_word = payload.search_word;
  const user_id = payload.user_id;
  const url = `/qna/search/${search_word}/${user_id}`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("QNA_GET_LAST_PAGE_NUM", res.data.totalPages);
        commit("QNA_GET_QUESTION_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "Q&A 검색");
    });
}

export async function qnaPageSearch({ state, commit }, payload) {
  const user_id = payload.user_id;
  const size = payload.size;
  const page = payload.page;
  const url = `/qna/${user_id}?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("QNA_GET_QUESTION_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "Q&A 페이지 서치");
    });
}

export async function qnaSearchPageSearch({ state, commit }, payload) {
  const search_word = payload.search_word;
  const user_id = payload.user_id;
  const size = payload.size;
  const page = payload.page;
  const url = `/qna/search/${search_word}/${user_id}?page=${page}&size=${size}`;
  await $axios
    .get(url)
    .then((res) => {
      if (res.status == 200) {
        commit("QNA_GET_QUESTION_LIST", res.data.content);
      } else {
        console.log("오류 발생");
      }
    })
    .catch((err) => {
      console.log(err, "Q&A 페이지 서치");
    });
}

export async function qnaGetQuestionDetail({ state, commit }, payload) {
  const qna_id = payload.qna_id;
  const user_id = payload.user_id;
  const url = `/qna/${qna_id}/${user_id}`;
  await $axios
    .get(url)
    .then((res) => {
      console.log(res.data);
      commit("QNA_GET_QUESTION_DETAIL", res.data.qnaRes);
      commit("QNA_GET_QUESTION_ANSWER", res.data.answerResList);
    })
    .catch((err) => {
      console.log(err, "Q&A 디테일 조회");
    });
}

export async function qnaCreateQuestion({ state }, payload) {
  console.log(payload);
  const body = payload;
  const url = "/qna";
  await $axios
    .post(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "Q&A 생성");
    });
}

export async function qnaModifyQuestion({ state }, payload) {
  const body = payload;
  const qna_id = body.qnaId;
  const url = `/qna/${qna_id}`;
  await $axios
    .put(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "Q&A 수정");
    });
}

export async function qnaDeleteQuestion({ state }, payload) {
  const qna_id = payload.qnaid;
  const url = `/qna/${qna_id}`;
  await $axios
    .delete(url, {
      data: {
        qnaId: payload.qnaId,
        userId: payload.userId,
      },
    })
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err, "Q&A 삭제");
    });
}

export async function qnaCreateAnswer({ state }, payload) {
  const body = payload;
  const url = "/answer";
  await $axios
    .post(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      alert("댓글 생성할 수 없습니다.");
      console.log(err, "Q&A 댓글 생성");
    });
}

export async function qnaModifyAnswer({ state }, payload) {
  const answer_id = payload.answerId;
  const body = payload;
  const url = `/answer/${answer_id}`;
  await $axios
    .put(url, body)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      alert("댓글 수정할 수 없습니다.");
      console.log(err, "Q&A 댓글 수정");
    });
}

export async function qnaDeleteAnswer({ state }, payload) {
  const answer_id = payload.answerId;
  const url = `/answer/${answer_id}`;
  await $axios
    .delete(url, {
      data: {
        userId: payload.userId,
        answerId: payload.answerId,
      },
    })
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      alert("댓글 삭제할 수 없습니다.");
      console.log(err, "Q&A 댓글 삭제");
    });
}

export async function reviewSubmitReview({ state }, payload) {
  const body = payload;
  const url = `/meeting/review`;
  await $axios
    .post(url, body)
    .then((res) => {
      alert("점수 부여가 성공적으로 완료됐습니다.")
      console.log(res);
    })
    .catch((err) => {
      console.log(err);
    });
}
