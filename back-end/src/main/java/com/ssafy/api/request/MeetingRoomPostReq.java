package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 화상채팅방 생성 API ([POST] /meeting/create) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("ConsultantRegisterPostRequest")
public class MeetingRoomPostReq {

    @ApiModelProperty(name="화상채팅 끝난 시간", example="")
    LocalDateTime callEndTime;

    @ApiModelProperty(name="화상채팅방 제목", example="Advice")
    String title;

    @ApiModelProperty(name="화상채팅방 내용", example="Advice Desc")
    String description;

    @ApiModelProperty(name="화상채팅방 카테고리 아이디", example="1: 고해성사, 2: 고민상담")
    Long meetingCategoryId;

    @ApiModelProperty(name="화상채팅방 주제 아이디", example="1: 학업, 2: 취업, 3: 이직")
    Long topicCategoryId;

    @ApiModelProperty(name="화상채팅방 방장 아이디 번호(User 테이블의 id)", example="true")
    Long ownerId;

    @ApiModelProperty(name="화상채팅방에 저장할 해당 컨설턴트와의 채팅방 번호(고해성사는 0 고정, 그외는 고민상담)", example="5")
    Long chatRoomId;

    @ApiModelProperty(name="최대 참가 인원수", example="2")
    int participants;



}
