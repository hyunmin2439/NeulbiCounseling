package com.ssafy.api.controller;

import com.ssafy.api.request.CommunityDeleteReq;
import com.ssafy.api.request.CommunityRegisterPostReq;
import com.ssafy.api.request.CommunityUserReq;
import com.ssafy.api.response.CommunityListRes;
import com.ssafy.api.response.CommunityViewRes;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.CommunityService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.LikeCheck;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 * <p>
 * 1.
 * 2.
 * 3.
 * 4.
 * 5.
 */
@Api(value = "커뮤니티 API", tags = {"community"})
@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityService communityService;

    @GetMapping()
    @ApiOperation(value = "게시글 목록 정보", notes = "<strong>전체 게시글 목록</strong>을 넘겨준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = CommunityListRes.class),
    })
    public ResponseEntity<Page<CommunityListRes>> communityList(@PageableDefault(page = 0, size = 8) Pageable pageable) {
        Page<Community> list = communityService.getAllCommunity(pageable);
        return ResponseEntity.status(200).body(CommunityListRes.of(list));
    }

    @GetMapping("/{key}")
    @ApiOperation(value = "게시글 정렬된 목록 정보", notes = "key(like, view) 기준으로 정렬된 <strong>전체 게시글 목록</strong>을 넘겨준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = CommunityListRes.class)
    })
    public ResponseEntity<Page<CommunityListRes>> sortedCommunityList(@PageableDefault(page = 0, size = 8) Pageable pageable,
                                                                      @ApiParam(value = "정렬 기준", required = true) @PathVariable(name = "key") String key) {

        Page<Community> list = communityService.getAllSortedCommunity(pageable, key);

        if (list == null) list = communityService.getAllCommunity(pageable); // 없으면 기존으로 요청이 들어오면 등록 번호 순으로

        return ResponseEntity.status(200).body(CommunityListRes.of(list));
    }

    @GetMapping("/search/{key}/{value}")
    @ApiOperation(value = "게시글 검색", notes = "key(description, title, user_nickname)에서 <strong> value를 포함한 글 목록</strong>을 넘겨준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = CommunityListRes.class)
    })
    public ResponseEntity<Page<CommunityListRes>> searchCommunityList(@PageableDefault(page = 0, size = 8) Pageable pageable,
                                                                      @ApiParam(value = "검색 기준", required = true) @PathVariable(name = "key") String key,
                                                                      @ApiParam(value = "검색 내용", required = true) @PathVariable(name = "value") String value) {

        Page<Community> list = communityService.searchCommunity(pageable, key, value);

        if (list == null) list = communityService.getAllCommunity(pageable); // 없으면 기존으로 요청이 들어오면 등록 번호 순으로

        return ResponseEntity.status(200).body(CommunityListRes.of(list));
    }

    @GetMapping("/{community_id}/{user_id}")
    @ApiOperation(value = "게시글 상세보기", notes = "<strong>선택한 게시글</strong>을 본다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = CommunityViewRes.class)
    })
    public ResponseEntity<CommunityViewRes> communityView(@ApiParam(value = "게시글 번호", required = true) @PathVariable(name = "community_id") Long communityId,
                                                          @ApiParam(value = "사용자 번호", required = true) @PathVariable(name = "user_id") Long userId) {

        Community community = communityService.getCommunity(communityId);
        boolean isCheck = communityService.getLikeCheck(communityId, userId);

        return ResponseEntity.status(200).body(CommunityViewRes.of(community, isCheck));
    }

    @PostMapping()
    @ApiOperation(value = "게시글 등록", notes = "<strong>게시글</strong>을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class),
    })
    public ResponseEntity<? extends BaseResponseBody> communityRegister(@RequestBody @ApiParam(value = "게시글 정보", required = true) CommunityRegisterPostReq registerInfo) {
        Community community = communityService.createCommunity(registerInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/{community_id}/like")
    @ApiOperation(value = "게시글 좋아요", notes = "<strong>선택한 게시글</strong>을 좋아요한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class),
    })
    public ResponseEntity<? extends BaseResponseBody> communityLike(@RequestBody @ApiParam(value = "게시글 작성자 정보", required = true) CommunityUserReq req) {

        communityService.setLikeCheck(req.getCommunityId(), req.getUserId());

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));

    }

    @PutMapping("/{community_id}")
    @ApiOperation(value = "게시글 수정", notes = "<strong>선택한 게시글</strong>을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "Fail", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> communityModify(@RequestBody @ApiParam(value = "게시글 작성자 정보", required = true) CommunityUserReq req) {
        Community community = communityService.modifyCommunity(req);

        if (community != null) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        } else {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Fail"));
        }
    }

    @DeleteMapping("/{community_id}/delete")
    @ApiOperation(value = "게시글 삭제", notes = "<strong>선택한 게시글</strong>을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "Fail", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> communityDelete(@RequestBody @ApiParam(value = "게시글 작성자 정보", required = true) CommunityDeleteReq req) {
        Community community = communityService.deleteCommunity(req);

        if (community != null) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        } else {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Fail"));
        }

    }


}
