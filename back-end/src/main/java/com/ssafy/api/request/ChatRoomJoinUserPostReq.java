package com.ssafy.api.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ChatRoomJoinUserPostRequest")
public class ChatRoomJoinUserPostReq {

    @ApiModelProperty(name="유저 id", example="user_id")
    Long userId;

    @ApiModelProperty(name="컨설턴트 id", example="consultant_id")
    Long consultantId;
}
