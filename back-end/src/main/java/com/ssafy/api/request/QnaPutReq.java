package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("QnaPutRequest")
public class QnaPutReq {
    @ApiModelProperty(name="유저 id")
    Long qnaId;
    @ApiModelProperty(name="유저 id")
    Long userId;
    @ApiModelProperty(name="Q&A 제목")
    String title;
    @ApiModelProperty(name="Q&A 내용")
    String description;
    @ApiModelProperty(name="잠금 여부 - 본인과 운영자만 볼 수 있게")
    boolean isRocked;
}
