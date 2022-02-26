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
@ApiModel("CommunityUserReq")
public class CommunityUserReq {

	@ApiModelProperty(name="유저 id", example="user_id")
	Long userId;
	@ApiModelProperty(name="게시글 번호", example="community_id")
	Long communityId;
	@ApiModelProperty(name="게시글 내용", example="description")
	String description;
	@ApiModelProperty(name="게시글 제목", example="title")
	String title;





}
