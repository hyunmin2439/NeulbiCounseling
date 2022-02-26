package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Meeting;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 방 목록 ([POST] /) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("MeetingResResponse")
public class MeetingRes {

    @ApiModelProperty(name = "미팅 정보", example = "미팅 정보")
    Meeting meeting;
    @ApiModelProperty(name = "해당 미팅방 방장의 UserId", example = "10")
    Long ownerId;
    @ApiModelProperty(name = "해당 미팅방의 토픽 카테고리 Id", example = "2")
    Long topicCategoryId;
    @ApiModelProperty(name = "해당 미팅방 방장의 닉네임", example = "꼬부기")
    String ownerNickname;

    public static MeetingRes of(Meeting meeting) {
        MeetingRes res = new MeetingRes();

        res.setMeeting(meeting);
        res.setOwnerId(meeting.getUser().getId());
        res.setTopicCategoryId(meeting.getTopicCategory().getId());
        res.setOwnerNickname(meeting.getUser().getNickname());
        return res;
    }
}
