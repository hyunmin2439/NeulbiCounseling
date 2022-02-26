package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CommentPutRequest")
public class CommentPutReq {
    @ApiModelProperty(name="현재 유저아이디")
    Long userId;
    @ApiModelProperty(name="댓글 번호")
    Long commentId;
    @ApiModelProperty(name="댓글 내용")
    String description;
}
