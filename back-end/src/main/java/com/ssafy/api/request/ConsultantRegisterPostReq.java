package com.ssafy.api.request;

import com.ssafy.db.entity.TopicCategory;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ConsultantRegisterPostRequest")
public class ConsultantRegisterPostReq {

	@ApiModelProperty(name="한줄 소개", example="description")
	String description; // 한줄 소개(50자 제한)
	@ApiModelProperty(name="전문 분야", example="topic_category_id")
	Long topicCategoryId;
	@ApiModelProperty(name="유저 id", example="user_id")
	Long userId;

}
