package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API ([POST] /confession) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserCheckPostRequest")
public class ConfessionPostReq {
    @ApiModelProperty(name="tile : 방 제목", example="진중한 고해성사 방")
    String title;
    @ApiModelProperty(name="description : 방 설명", example="빌런 강퇴")
    String description;
    @ApiModelProperty(name="topicCategoryId : 주제별 id값", example="1")
    Long topicCategoryId;
    @ApiModelProperty(name="participants : 제한 인원 수", example="5")
    int participants;
}
