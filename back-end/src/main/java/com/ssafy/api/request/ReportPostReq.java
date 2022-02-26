package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ReportPostRequest")
public class ReportPostReq {
    @ApiModelProperty(name="신고 유형")
    String category;
    @ApiModelProperty(name="신고자 아이디")
    Long userId;
    @ApiModelProperty(name="신고 받은 유저")
    Long reportUserId;
    @ApiModelProperty(name="신고 내용")
    String reportMsg;
}
