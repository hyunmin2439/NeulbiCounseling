package com.ssafy.api.controller;

import com.ssafy.api.request.CommentPostReq;
import com.ssafy.api.request.CommentPutReq;
import com.ssafy.api.response.CommentListRes;
import com.ssafy.api.service.CommentService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Comment;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 댓글 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "댓글 API", tags = {"Comment"})
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/{community_id}")
    @ApiOperation(value = "댓글 등록", notes = "<strong>댓글</strong>을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> createComment(@RequestBody @ApiParam(value = "댓글 정보", required = true) CommentPostReq commentInfo) {

        Comment comment = commentService.createComment(commentInfo);

        if (comment != null) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        } else {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }
    }

    @PutMapping("/{community_id}")
    @ApiOperation(value = "댓글 수정", notes = "<strong>댓글</strong>을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> modifyComment(@RequestBody @ApiParam(value = "게시글 번호", required = true) CommentPutReq commentInfo) {

        Comment comment = commentService.modifyComment(commentInfo);

        if (comment != null) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        } else {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }
    }

    @DeleteMapping("/{community_id}")
    @ApiOperation(value = "댓글 삭제", notes = "<strong>댓글</strong>을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> deleteComment(@RequestBody @ApiParam(value = "게시글 번호", required = true) CommentPutReq commentInfo) {

        boolean isSuccess = commentService.deleteComment(commentInfo);

        if (isSuccess) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        } else {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }
    }

}