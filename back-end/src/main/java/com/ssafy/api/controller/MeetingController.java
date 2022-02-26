package com.ssafy.api.controller;

import com.ssafy.api.request.MeetingRoomPostReq;
import com.ssafy.api.request.ReviewPostReq;
import com.ssafy.api.response.MeetingRes;
import com.ssafy.api.response.UserIdAndNickRes;
import com.ssafy.api.service.MeetingHistoryService;
import com.ssafy.api.service.MeetingService;
import com.ssafy.api.service.ReviewService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Meeting;
import com.ssafy.db.entity.MeetingHistory;
import com.ssafy.db.entity.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 화상채팅방 관련 API 요청 처리를 위한 컨트롤러 정의.
 * <p>
 * 1. 화상 채팅방 등록 - 중복 등록 막기 (unique 처리, create table sql 수정)
 *
 * 2. 상담가 목록(완)
 * 3. 상담가 주제별 목록(완)
 * 4. 상담가 검색 (key, value)(완) -> 닉네임만, 추가 검색 기능 확인 필요
 * 5. 상담가 상세 정보(get)(완) -> 상담가 정보를 넘길때 함께 넘기게 만듬(user_id, 닉네임, 프로필 이미지, 포인트 총합, 주제 카테고리, 한줄설명, 컨설팅횟수)
 * 7. 랭킹별 정렬(get)(완) -> point_tot로 정렬하고 상위 10명(user_id, 닉네임, 포인트 총합, 프로필 이미지)
 */

@Api(value = "화상채팅 API", tags = {"meeting"})
@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    MeetingHistoryService meetingHistoryService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @PostMapping("/create")
    @ApiOperation(value = "화상 채팅방 생성", notes = "<strong>화상 채팅방</strong>을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class)
    })
    public ResponseEntity<MeetingRes> createMeetingRoom(@RequestBody @ApiParam(value = "상담가 프로필 정보", required = true) MeetingRoomPostReq meetingRoomRegisterInfo) {

        // 이게 0 이면 고해성사, 아니면 고민상담.
        Long chatRoom = meetingRoomRegisterInfo.getChatRoomId();

        // 고해성사
        if(chatRoom == 0) {
            Meeting meeting = meetingService.createMeetingRoom(meetingRoomRegisterInfo);
            // 2-2. 이후 meeting_history 테이블에도 해당 정보들 추가하기.
            meetingHistoryService.createMeetingHistory(meeting);

            return ResponseEntity.status(200).body(MeetingRes.of(meeting));
        }

        // 고민 상담 시작
        else {
            // 1. 해당 방장 아이디와 채팅룸 아이디로 생성된 방이 있는지 검색
            Meeting hasMeeting = meetingService.getMeetingRoomByOwnerIdAndChatRoomId(meetingRoomRegisterInfo.getOwnerId(), chatRoom);

            // 2. 생성된 방이 존재하지 않을 경우
            if(hasMeeting == null) {
                // 2-1. 존재하지 않으면 해당 정보로 meeitng 테이블에 값들을 저장하기
                Meeting meeting = meetingService.createMeetingRoom(meetingRoomRegisterInfo);
                // 2-2. 이후 meeting_history 테이블에도 해당 정보들 추가하기.
                meetingHistoryService.createMeetingHistory(meeting);

                return ResponseEntity.status(200).body(MeetingRes.of(meeting));
            }
            // 3. 생성된 방이 존재 할 경우
            else {
                // 3-1 해당 방의 isActive가 False인 경우
                if(!hasMeeting.isActive()) {
                    // isActive를 True로 변경
                    meetingService.updateMeetingRoomIsActiveTrueByMeetingRoomId(hasMeeting.getId());
                }
                // MeetingHistory에 추가
                meetingHistoryService.createMeetingHistory(hasMeeting);
                return ResponseEntity.status(200).body(MeetingRes.of(hasMeeting));
            }
        }
    }

    @PutMapping("/close/{meeting_id}/{user_id}/{owner_id}")
    @ApiOperation(value = "화상 채팅방 나가기", notes = "<strong>화상 채팅방</strong>을 나간다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> leaveMeetingRoom(@PathVariable("meeting_id") Long meetingRoomId,
                                                                       @PathVariable("user_id") Long userId,
                                                                       @PathVariable("owner_id") Long ownerId) {

        // 1. userId와 meetingId를 받아온다.

        // userId에 해당하는 User 얻어오기
        User user = userService.getUserById(userId);
        // meetingId에 해당하는 meeting 방 얻어오기
        Meeting meeting = meetingService.getMeetingRoomByMeetingRoomId(meetingRoomId);

        // 방장일때
        // 2. userId와 OwnerId가 같으면 해당 meeting방의 isActive를 false로 변경하고 call_end_time 입력, History에 Create 혹은 Join을 EXIT으로 변경 한다
        if(userId == ownerId) {
            // meetingRoomId에 해당하는 화상 채팅방의 isActive를 false로 변경하고, call_end_time 입력한다.
            meetingService.updateMeetingRoomIsActiveFalseByMeetingRoomId(meetingRoomId);
            meetingHistoryService.updateMeetingHistoryExitWhenOwnerByUserAndMeetingRoom(user, meeting);
        }
        else {
            // 방장이 아닐때
            // 3. userId와 OwnerId가 다르면 meeting은 바꾸지 않고, History에 Create 혹은 Join을 EXIT으로 변경 한다
            meetingHistoryService.updateMeetingHistoryExitByUserAndMeetingRoom(user, meeting);
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/join/{meeting_id}/{user_id}")
    @ApiOperation(value = "화상 채팅방 입장하기", notes = "<strong>화상 채팅방</strong>에 입장한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> enterMeetingRoom(@PathVariable("user_id") Long userId,
                                                                       @PathVariable("meeting_id") Long meetingRoomId) {

        // 받아올 값 : meetingRoomId 와 UserId
        // 해당 미팅룸의 제한 인원과 현재 인원을 비교하여 제한 인원보다 작으면 입장이 가능하도록 한다.

        // 1. 해당 미팅룸의 제한 인원 값 받아오기
        Meeting meeting = meetingService.getMeetingRoomByMeetingRoomId(meetingRoomId);
        int limitParticipants = meeting.getParticipants();

        // 2. 현재 들어가려고 하는 미팅룸의 현재 인원값 받아오기
        Long joinedCount = meetingHistoryService.getMeetingHistoryJoinCountByMeetingRoomId(meetingRoomId);

        // 3. 위의 두 값을 비교한뒤, 입장 가능하면 Meeting_History에 저장, 안될 경우 거절
        // 입장 가능
        if(limitParticipants-1 > joinedCount) {
            
            User user = userService.getUserById(userId);
            
            // 3-1 해당 미팅방에 관련된 history 작성
            meetingHistoryService.createMeetingHistoryByUserAndMeetingRoom(user, meeting);

            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
        // 입장 불가능
        else {
            // 실패 리턴
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Fail"));
        }
    }

    @GetMapping("/enter/{chatRoom_id}")
    @ApiOperation(value = "화상 채팅방 입장하기 join 버튼 누르는 단계", notes = "<strong>화상 채팅방 입장하기 join 버튼 누르는 단계</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class)
    })
    public ResponseEntity<MeetingRes> beforeEnterMeetingRoom(@PathVariable("chatRoom_id") Long chatRoomId) {

        // 받아올 값 : chatRoomId
        // 받은 chatRoomId가 존재하는 meeting 방의 정보를 리턴해달라.
        Meeting meeting = meetingService.getMeetingRoomByChatRoomId(chatRoomId);

        return ResponseEntity.status(200).body(MeetingRes.of(meeting));
    }

    @GetMapping("/enter/confession/{meeting_id}")
    @ApiOperation(value = "화상 채팅방 입장하기 join 버튼 누르는 단계", notes = "<strong>화상 채팅방 입장하기 join 버튼 누르는 단계</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class)
    })
    public ResponseEntity<MeetingRes> enterMeetingRoom(@PathVariable("meeting_id") Long meetingRoomId) {

        // 받아올 값 : meetingRoomId
        // 받은 meetingRoomId가 존재하는 meeting 방의 정보를 리턴해달라.
        Meeting meeting = meetingService.getMeetingRoomByMeetingRoomId(meetingRoomId);
        return ResponseEntity.status(200).body(MeetingRes.of(meeting));
    }

    @PostMapping("/review")
    @ApiOperation(value = "리뷰 생성", notes = "<strong>리뷰</strong>을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> createReview(@RequestBody @ApiParam(value = "리뷰 정보", required = true) ReviewPostReq reviewPostReq) {

        // 리뷰 정보 를 받아온다.
        /**
         * description : 리뷰 내용
         * point : 포인트
         * user : 작성자
         * consultant : 해당 리뷰를 받은 컨설턴트
         * --------------------------------------------------
         * 받아와야할 정보 : userId, consultantId, description, point 4가지.
         */

        // 리뷰 저장 및 User 테이블의 user point_tot 에 더해주기
        reviewService.createReview(reviewPostReq);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/{meeting_id}/list")
    @ApiOperation(value = "해당 채팅방에 출입한 유저", notes = "<strong>화상 채팅방 입장하기 join 버튼 누르는 단계</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class)
    })
    public ResponseEntity<List<UserIdAndNickRes>> meetingRoomInfo(@PathVariable("meeting_id") Long meetingRoomId) {

        // 받아올 값 : meetingRoomId
        // 받은 meetingRoomId에 해당하는 방의 userId와 userNickname 리턴.
        List<MeetingHistory> meetingHistoryList = meetingHistoryService.getMeetingHistoryByMeetingRoomId(meetingRoomId);

        return ResponseEntity.status(200).body(UserIdAndNickRes.of(meetingHistoryList));
    }
}