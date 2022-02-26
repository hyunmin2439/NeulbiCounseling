package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("QnaDeleteRequest")
public class QnaDeleteReq {
    @ApiModelProperty(name="유저 id")
    Long qnaId;
    @ApiModelProperty(name="유저 id")
    Long userId;
}
