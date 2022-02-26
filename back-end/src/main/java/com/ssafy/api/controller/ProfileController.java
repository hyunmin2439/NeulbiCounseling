package com.ssafy.api.controller;

import com.ssafy.api.request.ProfileCheckPostReq;
import com.ssafy.api.request.ProfileModifyMaskPostReq;
import com.ssafy.api.request.ProfileModifyNicknamePostReq;
import com.ssafy.api.request.ProfileModifyPasswordPutReq;
import com.ssafy.api.response.*;
import com.ssafy.api.service.*;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.ProjectDirectoryPathUtil;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.ConsultantRepository;
import com.ssafy.db.repository.MyConsultantRepositorySupport;
import com.ssafy.db.repository.UserRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 유저 정보관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저정보 API", tags = {"Profile"})
@RestController
@RequestMapping("/profile")
public class ProfileController {
        // 리뷰, 미팅, 작성한 글정보, 리뷰정보
        @Autowired
        ProfileService profileService;

        @Autowired
        ConsultantService consultantService;

        @Autowired
        UserService userService;

        @Autowired
        ReviewService reviewService;

        @Autowired
        MeetingHistoryService meetingHistoryService;

        @Autowired
        S3FileUploadService s3FileUploadService;



        @PostMapping("/check/nickname")
        @ApiOperation(value = "닉네임 중복 확인", notes = "<strong>닉네임</strong>이 이미 존재하는지 확인한다.")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 401, message = "인증 실패"),
                @ApiResponse(code = 404, message = "사용자 없음"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<? extends BaseResponseBody> check(
                @RequestBody @ApiParam(value = "닉네임 중복확인 체크", required = true) ProfileCheckPostReq checkInfo) {

//		밸류값
                String nickname = checkInfo.getNickname();
                int profile_cnt = 0;
                profile_cnt = profileService.getUserByNickname(nickname);
                if (profile_cnt == 0) {
                        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
                } else {
                        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "FAIL"));
                }
        }

        @PutMapping("/{user_id}/modify/nickname")
        @ApiOperation(value = "닉네임 변경", notes = "<strong>닉네임</strong>을 변경한다.")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 401, message = "인증 실패"),
                @ApiResponse(code = 404, message = "사용자 없음"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<? extends BaseResponseBody> changeNickname(
                @PathVariable("user_id") Long id, @RequestBody @ApiParam(value = "닉네임 변경", required = true) ProfileModifyNicknamePostReq nickname) {


                profileService.modifyUserNickname(id,nickname.getNickname());


                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        }

        @PutMapping("/{user_id}/consultantProfile/topicCategory")
        @ApiOperation(value = "상담가 프로필 관심 주제 변경", notes = "<strong>상담가 프로필 관심주제</strong>를 변경한다.")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 401, message = "인증 실패"),
                @ApiResponse(code = 404, message = "사용자 없음"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<? extends BaseResponseBody> modifyConsultantProfileTopicCategory(@PathVariable("user_id") Long id, @RequestParam("id") @ApiParam(value = "관심 주제 변경", required = true) Long topicCategoryId) {


                consultantService.modifyConsultantTopicCategory(id,topicCategoryId);

                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        }

        @PutMapping("/{user_id}/consultantProfile/description")
        @ApiOperation(value = "상담가 프로필 설명 변경", notes = "<strong>상담가 프로필 설명</strong>를 변경한다.")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 401, message = "인증 실패"),
                @ApiResponse(code = 404, message = "사용자 없음"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<? extends BaseResponseBody> modifyConsultantProfileDescription(@PathVariable("user_id") Long id, @RequestBody @ApiParam(value = "상담가 설명 변경", required = true) String description) {

                consultantService.modifyConsultantDescription(id,description);


                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        }

        @PostMapping("/{user_id}/{consultant_id}")
        @ApiOperation(value = "선호하는 상담가 추가", notes = "<strong>선호하는 상담가 </strong>추가하기")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 401, message = "인증 실패"),
                @ApiResponse(code = 404, message = "사용자 없음"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<? extends BaseResponseBody> createMyConsultantList(@PathVariable("user_id") Long userId,
                                                                                 @PathVariable("consultant_id") Long consultantId) {

                System.out.println(userId);
                // 유저 아이디와 컨설턴트 아이디를 입력해서 선호하는 상담가 정보를 추가한다.
                profileService.createMyConsultant(userId, consultantId);

                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        }

        @DeleteMapping("/{user_id}/{consultant_id}")
        @ApiOperation(value = "선호하는 상담가 삭제", notes = "<strong>선호하는 상담가 삭제하기</strong>")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공")
        })
        public ResponseEntity<? extends BaseResponseBody> deleteMyConsultantList(@PathVariable("user_id") Long userId,
                                                                                 @PathVariable("consultant_id") Long consultantId) {

                System.out.println(userId);
                // 유저 아이디와 컨설턴트 아이디를 입력해서 선호하는 상담가 정보를 삭제한다.
                profileService.deleteMyConsultant(userId, consultantId);


                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        }

        @GetMapping("/{user_id}/myConsultant")
        @ApiOperation(value = "선호하는 상담가 정보", notes = "<strong>선호하는 상담가 정보</strong>불러오기")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 401, message = "인증 실패"),
                @ApiResponse(code = 404, message = "사용자 없음"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<Page<ConsultantListRes>> getMyConsultantList(@PathVariable("user_id") Long userId,  @PageableDefault(page = 0, size = 4) Pageable pageable) {

                System.out.println(userId);
                // 유저 아이디를 입력해서 해당하는 상담가 정보들을 받아온다.
                Page<ConsultantProfile> consultantProfileList = profileService.getMyConsultantList(userId, pageable);

                return ResponseEntity.status(200).body(ConsultantListRes.of(consultantProfileList));
        }

        @GetMapping("/{user_id}/community")
        @ApiOperation(value = "내가 작성한 글 정보", notes = "<strong>내가 작성한 글 정보</strong>불러오기")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공")
        })
        public ResponseEntity<Page<CommunityListRes>> getCommunityList(@PathVariable("user_id") Long userId,
                                                                       @PageableDefault(page = 0, size = 3) Pageable pageable) {

                // 유저 아이디를 입력해서 해당하는 내가 작성한 글 정보들을 받아온다.
                Page<Community> CommunityList = profileService.getCommunityList(pageable, userId);

                return ResponseEntity.status(200).body(CommunityListRes.of(CommunityList));
        }

        @GetMapping("/{user_id}/comment")
        @ApiOperation(value = "내가 작성한 댓글 정보", notes = "<strong>내가 작성한 댓글 정보</strong>불러오기")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공")
        })
        public ResponseEntity<Page<CommentListRes>> getCommentList(@PathVariable("user_id") Long userId,
                                                                   @PageableDefault(page = 0, size = 3) Pageable pageable) {

                // 유저 아이디를 입력해서 해당하는 작성자가 작성한 댓글 정보들을 받아온다.
                Page<Comment> commentList = profileService.getCommentList(pageable,userId);

                return ResponseEntity.status(200).body(CommentListRes.of(commentList));
        }

        @PostMapping("/{user_id}/mask")
        @ApiOperation(value = "마스크 변경", notes = "<strong>마스크 이미지</strong>를 변경한다.")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 401, message = "인증 실패"),
                @ApiResponse(code = 404, message = "사용자 없음"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<? extends BaseResponseBody> changeMask(
                @PathVariable("user_id") Long id, @RequestBody @ApiParam(value = "마스크 변경", required = true) ProfileModifyMaskPostReq mask_id) {

                profileService.modifyMask(id, mask_id.getMask_id());

                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        }

        @PostMapping("/{user_id}/maskBack")
        @ApiOperation(value = "마스크 배경 변경", notes = "<strong>마스크 배경화면</strong>을 변경한다.")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 401, message = "인증 실패"),
                @ApiResponse(code = 404, message = "사용자 없음"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<? extends BaseResponseBody> changeMaskBackground(
                @PathVariable("user_id") Long id, @RequestBody @ApiParam(value = "마스크 배경 변경", required = true) ProfileModifyMaskPostReq maskBack) {

                profileService.modifyMaskBack(id, maskBack.getMask_id());

                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        }

        @GetMapping("/image/{user_id}")
        @ApiOperation(value = "프로필 이미지 불러오기", notes = "<strong>프로필 이미지</strong>를 불러온다.")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public Resource getProfileImg(
                @PathVariable("user_id") @ApiParam(value = "유저 아이디", required = true) Long userId) throws MalformedURLException {

                User user = userService.getUserById(userId);
                if(user == null) return null;
                // 기본 이미지 내려줄 것
                if(user.getProfileImg() == null || user.getProfileImg().equals(""))
                        s3FileUploadService.findImg("default-profile-image.jpg");

                return new UrlResource("file:" + s3FileUploadService.findImg(user.getProfileImg()));
        }

        @PostMapping("/image/{user_id}")
        @ApiOperation(value = "프로필 이미지 변경", notes = "<strong>프로필 이미지</strong>를 변경한다.")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<? extends BaseResponseBody> modifyProfileImg(
                @PathVariable("user_id") Long userId, @RequestParam("profileImg") @ApiParam(value = "프로파일 이미지", required = true) MultipartFile profileImgInfo) throws IOException {

                int statusCode = profileService.modifyProfileImg(userId, profileImgInfo);

                if(statusCode == 500) return ResponseEntity.status(200).body(BaseResponseBody.of(200, "FAIL"));

                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        }

        @DeleteMapping("/{user_id}/resign")
        @ApiOperation(value = "회원 탈퇴", notes = "<strong>회원탈퇴</strong>")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 401, message = "인증 실패"),
                @ApiResponse(code = 404, message = "사용자 없음"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<? extends BaseResponseBody> changeNickname(@PathVariable("user_id") Long id) {

                userService.deleteUserById(id);

                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        }

        @PutMapping("/{user_id}/modify/password")
        @ApiOperation(value = "비밀번호 변경", notes = "<strong>비밀번호 변경</strong>")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 401, message = "인증 실패"),
                @ApiResponse(code = 404, message = "사용자 없음"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<? extends BaseResponseBody> changePassword(@PathVariable("user_id") Long id, @RequestBody ProfileModifyPasswordPutReq password) {

                profileService.modifyPasswordByUserId(password, id);

                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        }

        @GetMapping("/{user_id}/consultantProfile")
        @ApiOperation(value = "내 상담가 프로필 정보", notes = "<strong>내 상담가 프로필 정보</strong>불러오기")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<ConsultantProfileRes> getConsultantProfile(@PathVariable("user_id") Long userId) {
                ConsultantProfile con = profileService.getConsultantProfile(userId).orElse(null);

                // 상담가 정보 없음
                if(con == null) return ResponseEntity.status(200).body(null);

                return ResponseEntity.status(200).body(ConsultantProfileRes.of(con));
        }

        @GetMapping("/{user_id}/meeting/advice")
        @ApiOperation(value = "내 고민상담 정보", notes = "<strong>내 고민상담 정보</strong>불러오기")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 401, message = "인증 실패"),
                @ApiResponse(code = 404, message = "사용자 없음"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<Map<String, Page<AdviceAndConfessionListRes>>> userMeetingHistoryAdvice(@PathVariable("user_id") Long userId,
                                                                                                      @PageableDefault(page = 0, size = 3) Pageable pageable) {

                // 내가 참가했던 meeting History 정보, meeting 정보

                Page<MeetingHistory> meetingHistories = meetingHistoryService.getAdviceByUserId(userId, pageable);

                Map<String, Page<AdviceAndConfessionListRes>> map = new HashMap<>();

                map.put("advice", AdviceAndConfessionListRes.of(meetingHistories));

                return ResponseEntity.status(200).body(map);
        }

        @GetMapping("/{user_id}/meeting/confession")
        @ApiOperation(value = "내 고해성사 정보", notes = "<strong>내 고해성사 정보</strong>불러오기")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 401, message = "인증 실패"),
                @ApiResponse(code = 404, message = "사용자 없음"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<Map<String, Page<AdviceAndConfessionListRes>>> userMeetingHistoryConfession(@PathVariable("user_id") Long userId,
                                                                                                          @PageableDefault(page = 0, size = 3) Pageable pageable) {

                // 내가 참가했던 meeting History 정보, meeting 정보

                Page<MeetingHistory> meetingHistories = meetingHistoryService.getConfessionByUserId(userId, pageable);

                Map<String, Page<AdviceAndConfessionListRes>> map = new HashMap<>();

                map.put("confession", AdviceAndConfessionListRes.of(meetingHistories));

                return ResponseEntity.status(200).body(map);
        }

        @GetMapping("/{user_id}/review/written")
        @ApiOperation(value = "내가 쓴 리뷰 정보", notes = "<strong>내가 쓴 리뷰 정보</strong>불러오기")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<Map<String, Page<ReviewListRes>>> userWrittenReview(@PathVariable("user_id") Long userId,
                                                                                  @PageableDefault(page = 0, size = 3) Pageable pageable) {

                User user = profileService.findByUserId(userId).orElse(null);

                // 사용자 없음
                if(user == null) return ResponseEntity.status(200).body(null);

                Map<String, Page<ReviewListRes>> map = new HashMap<>();

                // 내가 쓴 리뷰
                Page<Review> writtenReviewList = reviewService.getWrittenReviewList(userId, pageable);
                map.put("writtenReview", ReviewListRes.of(writtenReviewList));

                return ResponseEntity.status(200).body(map);
        }

        @GetMapping("/{user_id}/review/receive")
        @ApiOperation(value = "내가 받은 리뷰 정보", notes = "<strong>내가 받은 리뷰 정보</strong>불러오기")
        @ApiResponses({
                @ApiResponse(code = 200, message = "성공"),
                @ApiResponse(code = 500, message = "서버 오류")
        })
        public ResponseEntity<Map<String, Page<ReviewListRes>>> userReceivedReview(@PathVariable("user_id") Long userId,
                                                                                   @PageableDefault(page = 0, size = 3) Pageable pageable) {

                User user = profileService.findByUserId(userId).orElse(null);

                // 사용자 없음
                if(user == null) return ResponseEntity.status(200).body(null);

                Map<String, Page<ReviewListRes>> map = new HashMap<>();

                // 유저가 상담가로 신청했으면 받은 리뷰
                if(user.isConsultant()) {
                        ConsultantProfile consultantProfile = profileService.getConsultantProfile(user.getId()).orElse(null);
                        Long consultantId = consultantProfile.getId();
                        Page<Review> receivedReviewList = reviewService.getReceivedReviewList(consultantId, pageable);

                        map.put("receivedReview", ReviewListRes.of(receivedReviewList));
                }
                // 유저가 상담가로 신청 안했으면
                else {
                        // 500 코드와 null 값 반환
                        return ResponseEntity.status(200).body(null);
                }
                return ResponseEntity.status(200).body(map);
        }
}
