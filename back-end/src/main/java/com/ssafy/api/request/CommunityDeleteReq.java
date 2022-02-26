package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CommunityDeleteRequest")
public class CommunityDeleteReq {
    @ApiModelProperty(name="유저 id", example="user_id")
    Long userId;
    @ApiModelProperty(name="게시글 번호", example="community_id")
    Long communityId;
}
