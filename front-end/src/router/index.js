import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/home/Home.vue";
import SignUp from "../views/main/components/SignUp.vue";
import SignIn from "../views/main/components/SignIn.vue";
import OAuthSuccess from "../views/main/components/OAuthSuccess.vue";
import OAuthFailure from "../views/main/components/OAuthFailure.vue";
import Profile from "../views/profile/Profile.vue";
import History from "../views/history/ProfileHistory.vue"
import Confession from "../views/confession/Confession.vue";
import Advice from "../views/advice/Advice.vue";
import Community from "../views/community/Community.vue";
import CommunityCreate from "../views/community/components/CommunityCreate"
import CommunityDetail from "../views/community/components/CommunityDetail";
import CommunityModify from "../views/community/components/CommunityModify"
import ChatRoom from "../views/chatroom/ChatRoom.vue"
import MeetingConfession from "../views/meeting/MeetingConfession.vue"
import MeetingAdvice from "../views/meeting/MeetingAdvice"
import Notice from "../views/notice/Notice.vue"
import NoticeCreate from "../views/notice/components/NoticeCreate.vue"
import NoticeDetail from '../views/notice/components/NoticeDetail'
import Qna from "../views/qna/Qna.vue"
import QnaCreate from "../views/qna/components/QnaCreate"
import QnaDetail from "../views/qna/components/QnaDetail"
import ReviewAdvice from '../views/review/ReviewAdvice.vue'
import ReviewConfession from '../views/review/ReviewConfession'

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/signup",
    name: "SignUp",
    component: SignUp,
  },
  {
    path: "/signin",
    name: "SignIn",
    component: SignIn,
  },
  {
    path: "/oauth/success",
    name: "OAuthSuccess",
    component: OAuthSuccess,
  },
  {
    path: "/oauth/failure",
    name: "OAuthFailure",
    component: OAuthFailure,
  },
  {
    path: "/user/:user_id",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/history/:user_id",
    name: "History",
    component: History,
  },
  {
    path: "/confession",
    name: "Confession",
    component: Confession,
  },
  {
    path: "/advice",
    name: "Advice",
    component: Advice,
  },
  {
    path: "/community",
    name: "Community",
    component: Community,
  },
  {
    path: "/community/create",
    name: "CommunityCreate",
    component: CommunityCreate,
  },
  {
    path: "/community/:community_id",
    name: "CommunityDetail",
    component: CommunityDetail,
  },
  {
    path: "/community/modify/:community_id",
    name: "CommunityModify",
    component: CommunityModify,
  },
  {
    path: "/chatroom/:user_id",
    name: "ChatRoom",
    component: ChatRoom,
  },
  {
    path: "/meeting/confession/:meeting_id",
    name: "MeetingConfession",
    component: MeetingConfession,
  },
  {
    path: "/meeting/advice/:meeting_id",
    name: "MeetingAdvice",
    component: MeetingAdvice,
  },
  {
    path: "/notice",
    name: "Notice",
    component: Notice,
  },
  {
    path: "/notice/create",
    name: "NoticeCreate",
    component: NoticeCreate,
  },
  {
    path: "/notice/:notice_id",
    name: "NoticeDetail",
    component: NoticeDetail,
  },
  {
    path: "/qna",
    name: "Qna",
    component: Qna,
  },
  {
    path: "/qna/create",
    name: "QnaCreate",
    component: QnaCreate,
  },
  {
    path: "/qna/:qna_id",
    name: "QnaDetail",
    component: QnaDetail,
  },
  {
    path: "/review/advice/:user_id/:consultant_id",
    name: "ReviewAdvice",
    component: ReviewAdvice,
  },
  {
    path: "/review/confession/:user_id/:meeting_id",
    name: "ReviewConfession",
    component: ReviewConfession,
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
