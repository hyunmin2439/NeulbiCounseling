package com.ssafy.api.controller;

import com.ssafy.api.request.AnswerDeleteReq;
import com.ssafy.api.request.AnswerPostReq;
import com.ssafy.api.request.AnswerPutReq;
import com.ssafy.api.service.AnswerService;
import com.ssafy.api.service.QnaService;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Q&A 답글 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "Q&A 답글 API", tags = {"Q&A 답글"})
@RestController
@RequestMapping("/answer")
@Controller
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @Autowired
    QnaService qnaService;

    @PostMapping()
    @ApiOperation(value = "Answer 등록", notes = "<strong>Q&A 답글</strong>을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 401, message = "ACCESS DENIED", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> createAnswer(@RequestBody @ApiParam(value = "Q&A 답글 정보", required = true) AnswerPostReq answerInfo) {

        // QnA 답변 완료 등록
        qnaService.modifyQnaIsAnswered(answerInfo.getQnaId());
        int statusCode = answerService.createAnswer(answerInfo);

        switch (statusCode) {
            case 200:
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
            case 401:
                return ResponseEntity.status(401).body(BaseResponseBody.of(401, "ACCESS DENIED"));
            default:
                return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }
    }

    @PutMapping("/{answer_id}")
    @ApiOperation(value = "Answer 수정", notes = "<strong>Q&A 답글</strong>을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 401, message = "ACCESS DENIED", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> modifyAnswer(@RequestBody @ApiParam(value = "Q&A 답글 번호", required = true) AnswerPutReq answerInfo) {
        int statusCode = answerService.modifyAnswer(answerInfo);

        switch (statusCode) {
            case 200:
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
            case 401:
                return ResponseEntity.status(401).body(BaseResponseBody.of(401, "ACCESS DENIED"));
            default:
                return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }
    }

    @DeleteMapping("/{answer_id}")
    @ApiOperation(value = "Answer 삭제", notes = "<strong>Q&A 답글</strong>을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 401, message = "ACCESS DENIED", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> deleteAnswer(@RequestBody @ApiParam(value = "Q&A 답글 정보", required = true) AnswerDeleteReq answerInfo) {
        int statusCode = answerService.deleteAnswer(answerInfo);

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
