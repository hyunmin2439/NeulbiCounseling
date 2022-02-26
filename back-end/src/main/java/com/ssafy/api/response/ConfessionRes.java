package com.ssafy.api.response;

import com.ssafy.db.entity.Meeting;
import com.ssafy.db.entity.MeetingCategory;
import com.ssafy.db.entity.TopicCategory;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 방 목록 ([POST] /) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ConfessionResList")
public class ConfessionRes {
    @ApiModelProperty(name="title", example = "방 제목")
    String title;
    @ApiModelProperty(name="description", example = "방 설명")
    String description;
    @ApiModelProperty(name="is_active", example = "방 활성화 여부")
    boolean isActive;
    @ApiModelProperty(name="participants", example = "현재 방 참가자 수")
    long currJoinParticipants;
    @ApiModelProperty(name="participants", example = "방 최대 참가자 수")
    int participants;
    @ApiModelProperty(name="meeting_id", example = "화상회의 방 Id")
    Long meetingId;
    @ApiModelProperty(name="owner_id", example = "방장 아이디")
    Long ownerId;
    @ApiModelProperty(name="owner_nickname", example = "방장 닉네임")
    String ownerNickname;
    @ApiModelProperty(name="meeting_category_id", example = "화상회의 종류")
    Long meetingCategoryId;
    @ApiModelProperty(name="topic_category_id", example = "화상회의 주제")
    Long topicCategoryId;
    @ApiModelProperty(name="meeting_category_name", example = "화상회의 종류 이름")
    String meetingCategoryName;
    @ApiModelProperty(name="topic_category_name", example = "화상회의 주제 이름")
    String topicCategoryName;
    @ApiModelProperty(name="mask_name", example = "방장의 마스크 이미지")
    int maskId;
    @ApiModelProperty(name="mask_name", example = "방장의 마스크 배경 이미지")
    int backId;
    @ApiModelProperty(name="profileImg", example = "방장의 프로필 이미지")
    String profileImg;

    public static ConfessionRes of(Meeting m, long currJoinParticipants) {
        ConfessionRes conRes = new ConfessionRes();

        User user = m.getUser();
        MeetingCategory meetingCategory = m.getMeetingCategory();
        TopicCategory topicCategory = m.getTopicCategory();

        conRes.setTitle(m.getTitle());
        conRes.setDescription(m.getDescription());
        conRes.setActive(m.isActive());
        conRes.setCurrJoinParticipants(currJoinParticipants);
        conRes.setParticipants(m.getParticipants());
        conRes.setOwnerId(user.getId());
        conRes.setOwnerNickname(user.getNickname());
        conRes.setMaskId(user.getMaskId());
        conRes.setBackId(user.getMaskBack());
        conRes.setProfileImg(user.getProfileImg());
        conRes.setMeetingCategoryId(meetingCategory.getId());
        conRes.setMeetingCategoryName(meetingCategory.getName());
        conRes.setTopicCategoryId(topicCategory.getId());
        conRes.setTopicCategoryName(topicCategory.getName());
        conRes.setMeetingId(m.getId());

        return conRes;

    }
}
