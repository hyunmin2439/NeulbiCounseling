package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("CommunityRegisterPostRequest")
public class CommunityRegisterPostReq {

	@ApiModelProperty(name="유저 id", example="user_id")
	Long userId;
	@ApiModelProperty(name="게시글 내용", example="title")
	String title; // 게시글 제목(50자 제한)
	@ApiModelProperty(name="게시글 내용", example="description")
	String description; // 게시글 내용 5000자


}
