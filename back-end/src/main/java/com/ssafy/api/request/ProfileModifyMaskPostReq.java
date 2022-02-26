package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API ([POST] /user/check) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ProfileModifyMaskPostRequest")
public class ProfileModifyMaskPostReq {
	@ApiModelProperty(name="값", example="mask_id")
	int mask_id;
}
