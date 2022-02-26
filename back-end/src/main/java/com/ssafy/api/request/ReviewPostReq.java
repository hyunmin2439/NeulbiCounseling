package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ReviewPostRequest")
public class ReviewPostReq {
    @ApiModelProperty(name="유저 아이디", example="10")
    Long userId;

    @ApiModelProperty(name="컨설턴트 아이디", example="2")
    Long consultantId;

    @ApiModelProperty(name="리뷰 평가 내용", example="친절해요!!")
    String description;

    @ApiModelProperty(name="리뷰 평가 포인트", example="100")
    double point;
}
