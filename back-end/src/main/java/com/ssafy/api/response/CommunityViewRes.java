package com.ssafy.api.response;

import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 상담가 목록 조회 API ([GET] /advice) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ConsultantListRes")
public class CommunityViewRes {

    @ApiModelProperty(name = "Community id")
    Long id;
    @ApiModelProperty(name = "User id")
    Long userId;
    @ApiModelProperty(name = "User nickname")
    String userNickname;
    @ApiModelProperty(name = "Community title")
    String title;
    @ApiModelProperty(name = "Community description")
    String description;
    @ApiModelProperty(name = "Community likeCnt")
    int likeCnt;
    @ApiModelProperty(name = "Community viewCnt")
    int viewCnt;
    @ApiModelProperty(name = "Community viewCnt")
    boolean isLike;
    @ApiModelProperty(name = "Community createdDate")
    LocalDateTime createdDate;
    @ApiModelProperty(name = "Comment List")
    List<CommentListRes> commentListRes;


    public static CommunityViewRes of(Community c, boolean isLike) {
        CommunityViewRes clr = new CommunityViewRes();
        User user = c.getUser();
        List<CommentListRes> commentListRes = CommentListRes.of(c.getCommentList());

        clr.setCommentListRes(commentListRes);
        clr.setUserId(user.getId());
        clr.setUserNickname(user.getNickname());
        clr.setId(c.getId());
        clr.setTitle(c.getTitle());
        clr.setDescription(c.getDescription());
        clr.setLikeCnt(c.getLikeCnt());
        clr.setViewCnt(c.getViewCnt());
        clr.setLike(isLike);
        clr.setCreatedDate(c.getCreatedDate().plusHours(9));

        return clr;
    }


}