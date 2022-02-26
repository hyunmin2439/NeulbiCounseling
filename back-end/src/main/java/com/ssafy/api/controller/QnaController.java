package com.ssafy.api.controller;

import com.ssafy.api.request.QnaDeleteReq;
import com.ssafy.api.request.QnaPostReq;
import com.ssafy.api.request.QnaPutReq;
import com.ssafy.api.response.QnaDetailRes;
import com.ssafy.api.response.QnaListRes;
import com.ssafy.api.response.QnaRes;
import com.ssafy.api.service.QnaService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Qna;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Q & A 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "Q&A API", tags = {"Q&A"})
@RestController
@RequestMapping("/qna")
@Controller
public class QnaController {
    @Autowired
    QnaService qnaService;

    @GetMapping("/{user_id}")
    @ApiOperation(value = "Q&A 리스트 조회", notes = "<strong>Q&A</strong> 전체 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = QnaListRes.class),
            @ApiResponse(code = 500, message = "실패", response = QnaListRes.class)
    })
    public ResponseEntity<Page<QnaRes>> getQnaList(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                                   @PathVariable(name = "user_id") @ApiParam(value = "유저 번호", required = true) Long userId) {
        Page<Qna> qnas = qnaService.getQnaList(pageable);

        if (qnas != null) {
            return ResponseEntity.status(200).body(QnaListRes.of(qnas, userId));
        } else {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search/{search_word}/{user_id}")
    @ApiOperation(value = "Q&A 검색", notes = "<strong>Q&A</strong> searchWord가 들어간 Q&A를 검색한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = QnaListRes.class),
            @ApiResponse(code = 500, message = "실패", response = QnaListRes.class)
    })
    public ResponseEntity<Page<QnaRes>> searchQnaList(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                                      @PathVariable(name = "search_word") @ApiParam(value = "검색 단어", required = true) String searchWord,
                                                      @PathVariable(name = "user_id") @ApiParam(value = "유저 번호", required = true) Long userId) {

        Page<Qna> qnas = qnaService.searchQnaList(pageable, searchWord);

        if (qnas != null) {
            return ResponseEntity.status(200).body(QnaListRes.of(qnas, userId));
        } else {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{qna_id}/{user_id}")
    @ApiOperation(value = "Q&A 상세조회", notes = "<strong>Q&A</strong>의 자세한 내용을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = QnaRes.class),
            @ApiResponse(code = 401, message = "인증 실패", response = QnaRes.class),
            @ApiResponse(code = 500, message = "실패", response = QnaRes.class)
    })
    public ResponseEntity<QnaDetailRes> getQna(@PathVariable(name = "qna_id") @ApiParam(value = "Q&A 번호", required = true) Long qnaId,
                                         @PathVariable(name = "user_id") @ApiParam(value = "유저 번호", required = true) Long userId) {
        int statusCode = qnaService.authenticate(userId, qnaId);

        switch (statusCode) {
            case 200:
                return ResponseEntity.status(200).body(QnaDetailRes.of(qnaService.getQna(qnaId)));
            case 401:
                return ResponseEntity.status(401).body(null);
            default:
                return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping()
    @ApiOperation(value = "Qna 등록", notes = "<strong>Q&A</strong>를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> createQna(@RequestBody @ApiParam(value = "Q&A 정보", required = true) QnaPostReq qnaInfo) {
        int statusCode = qnaService.createQna(qnaInfo);

        switch (statusCode) {
            case 200:
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
            default:
                return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }

    }

    @PutMapping("/{qna_id}")
    @ApiOperation(value = "Qna 수정", notes = "<strong>Q&A</strong>를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 401, message = "ACCESS DENIED", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> modifyQna(@RequestBody @ApiParam(value = "Q&A 번호", required = true) QnaPutReq qnaInfo) {
        int statusCode = qnaService.modifyQna(qnaInfo);

        switch (statusCode) {
            case 200:
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
            case 401:
                return ResponseEntity.status(401).body(BaseResponseBody.of(401, "ACCESS DENIED"));
            default:
                return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }
    }

    @DeleteMapping("/{qna_id}")
    @ApiOperation(value = "Qna 삭제", notes = "<strong>Q&A</strong>를 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 401, message = "ACCESS DENIED", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> deleteQna(@RequestBody @ApiParam(value = "Q&A 정보", required = true) QnaDeleteReq qnaInfo) {
        int statusCode = qnaService.deleteQna(qnaInfo);

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
