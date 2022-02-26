package com.ssafy.api.controller;

/*
 * 고해성사 관련 API 요청 처리 컨트롤러
 */

import com.ssafy.api.request.ConfessionPostReq;
import com.ssafy.api.response.ConfessionRes;
import com.ssafy.api.response.MeetingListRes;
import com.ssafy.api.service.MeetingHistoryService;
import com.ssafy.api.service.MeetingService;
import com.ssafy.common.model.response.BaseResponseBody;

import com.ssafy.db.entity.Meeting;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "고해성사 API", tags = {"Confession"})
@RestController
@RequestMapping("/confession")
public class ConfessionController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    MeetingHistoryService meetingHistoryService;

    private Page<ConfessionRes> getPageConfessionRes(Page<Meeting> meetings) {
        List<ConfessionRes> temp = new ArrayList<>();
        Pageable tempPageable = meetings.getPageable();
        long total = meetings.getTotalElements();

        meetings.getContent().forEach( meeting -> {
            ConfessionRes confessionRes =
                    ConfessionRes.of(meeting,
                            meetingHistoryService
                                    .getCountMeetingHistoriesByMeetingIdAndActionCreateOrActionJoin(meeting.getId()));
            temp.add(confessionRes);
        });

        return new PageImpl<ConfessionRes>(temp, tempPageable, total);
    }

    @GetMapping()
    @ApiOperation(value = "넘겨 받을 값 없음", notes = "고해성사 모든 방 검색")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = MeetingListRes.class)
    })
    public ResponseEntity<Page<ConfessionRes>> findConfession(@PageableDefault(page = 0, size = 6) Pageable pageable) {

        Page<Meeting> meetings = meetingService.getAllMeetings(pageable);

        return ResponseEntity.status(200).body( getPageConfessionRes(meetings) );
    }

    @GetMapping("/{topicCategoryId}")
    @ApiOperation(value = "topicCategoryId : 주제별 id", notes = "주제별 고해성사 방 검색")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = MeetingListRes.class)
    })
    public ResponseEntity<Page<ConfessionRes>> findTopicCategoryConfession(@PageableDefault(page = 0, size = 6) Pageable pageable
                                                                            , @PathVariable Long topicCategoryId) {

        Page<Meeting> meetings = meetingService.getMeetingsByTopicCategory(pageable, topicCategoryId);

        return ResponseEntity.status(200).body( getPageConfessionRes(meetings) );
    }

    @GetMapping("/search/{key}/{value}")
    @ApiOperation(value = "key, value", notes = "원하는 검색을 통해 고해성사 방 검색")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = MeetingListRes.class)
    })
    public ResponseEntity<Page<ConfessionRes>> findSearchConfession(@PageableDefault(page = 0, size = 6) Pageable pageable
                                                                    , @PathVariable String key
                                                                    , @PathVariable String value) {

        // 방 설명, 방 제목, 방장 닉네임
        Page<Meeting> meetings = meetingService.searchMeetings(pageable, key , value);

        return ResponseEntity.status(200).body( getPageConfessionRes(meetings) );
    }

    @PostMapping()
    @ApiOperation(value = "방 제목, 방 설명, 주제, 제한인원 수", notes = "방 제목, 방 설명, 주제, 제한인원 수를 통해 고해성사 방을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> createConfession(@RequestBody @ApiParam(value="방 생성 정보", required = true) ConfessionPostReq confessionInfo) {

        // meeting Service 쪽이랑 연결하고 meetingPostRes? 이러한 응답 객체로 넘겨주면 될 듯 함.
        // 고민 상담 부분이랑 같이 처리해야 함.
        // 고해성사에서 필요한 부분이랑 고민 상담에 필요한 부분 다름

        // 테스트
        System.out.println(confessionInfo.getTitle());
        System.out.println(confessionInfo.getDescription());
        System.out.println(confessionInfo.getParticipants());
        System.out.println(confessionInfo.getTopicCategoryId());

        // 일단 넘겨주는 값 없음
        return ResponseEntity.status(200).body( BaseResponseBody.of(200, "Success"));
    }
}
