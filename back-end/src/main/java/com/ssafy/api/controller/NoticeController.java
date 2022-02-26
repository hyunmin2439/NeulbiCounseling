package com.ssafy.api.controller;

import com.ssafy.api.request.*;
import com.ssafy.api.response.NoticeListRes;
import com.ssafy.api.response.NoticeRes;
import com.ssafy.api.service.NoticeService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Notice;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 공지사항 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "공지사항 API", tags = {"Notice"})
@RestController
@RequestMapping("/notice")
@Controller
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @GetMapping()
    @ApiOperation(value = "공지사항 리스트 조회", notes = "<strong>공지사항</strong> 전체 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = NoticeListRes.class),
            @ApiResponse(code = 500, message = "실패", response = NoticeListRes.class)
    })
    public ResponseEntity<Page<NoticeRes>> getNoticeList(@PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<Notice> notices = noticeService.getNoticeList(pageable);

        if (notices != null) {
            return ResponseEntity.status(200).body(NoticeListRes.of(notices));
        } else {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{notice_id}")
    @ApiOperation(value = "공지사항 상세조회", notes = "<strong>공지사항</strong>의 자세한 내용을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = NoticeRes.class),
            @ApiResponse(code = 500, message = "실패", response = NoticeRes.class)
    })
    public ResponseEntity<NoticeRes> getNotice(@PathVariable(name = "notice_id") @ApiParam(value = "공지사항 번호", required = true) Long noticeId) {

        Notice notice = noticeService.getNoticeDetail(noticeId);

        if (notice != null) {
            return ResponseEntity.status(200).body(NoticeRes.of(notice));
        } else {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping()
    @ApiOperation(value = "공지사항 등록", notes = "<strong>공지사항</strong>을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 401, message = "ACCESS DENIED", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> createNotice(@RequestBody @ApiParam(value = "공지사항 정보", required = true) NoticePostReq noticeInfo) {

        int statusCode = noticeService.createNotice(noticeInfo);

        switch (statusCode) {
            case 200:
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
            case 401:
                return ResponseEntity.status(401).body(BaseResponseBody.of(401, "ACCESS DENIED"));
            default:
                return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }
    }

    @PutMapping("/{notice_id}")
    @ApiOperation(value = "공지사항 수정", notes = "<strong>공지사항</strong>을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 401, message = "ACCESS DENIED", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> modifyNotice(@RequestBody @ApiParam(value = "공지사항 번호", required = true) NoticePutReq noticeInfo) {
        int statusCode = noticeService.modifyNotice(noticeInfo);

        switch (statusCode) {
            case 200:
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
            case 401:
                return ResponseEntity.status(401).body(BaseResponseBody.of(401, "ACCESS DENIED"));
            default:
                return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }
    }

    @DeleteMapping("/{notice_id}")
    @ApiOperation(value = "공지사항 삭제", notes = "<strong>공지사항</strong>을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 401, message = "ACCESS DENIED", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> deleteNotice(@RequestBody @ApiParam(value = "공지사항 번호", required = true) NoticeDeleteReq noticeInfo) {
        int statusCode = noticeService.deleteNotice(noticeInfo);

        switch (statusCode) {
            case 200:
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
            case 401:
                return ResponseEntity.status(401).body(BaseResponseBody.of(401, "ACCESS DENIED"));
            default:
                return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }
    }

}
