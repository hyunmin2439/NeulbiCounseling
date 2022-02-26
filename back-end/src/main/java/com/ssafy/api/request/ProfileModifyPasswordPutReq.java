package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API ([PUT] /user/check) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ProfileModifyPasswordPutRequest")
public class ProfileModifyPasswordPutReq {
    @ApiModelProperty(name="비밀번호", example="abcd")
    String password;

    @ApiModelProperty(name="아이디", example = "user_id")
    String userId;
}
