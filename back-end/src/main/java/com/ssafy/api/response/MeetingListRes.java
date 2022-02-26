package com.ssafy.api.response;

import com.ssafy.api.service.MeetingHistoryService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 방 목록 ([POST] /) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("MeetingResList")
public class MeetingListRes extends BaseResponseBody {

    @ApiModelProperty(name="title", example = "방 제목")
    String title;
    @ApiModelProperty(name="description", example = "방 설명")
    String description;
    @ApiModelProperty(name="is_active", example = "방 활성화 여부")
    boolean isActive;
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
    @ApiModelProperty(name="profileImg", example = "방장의 프로필 이미지")
    String profileImg;

    public static Page<MeetingListRes> of(Page<Meeting> meetingList) {
        List<MeetingListRes> temp = new ArrayList<>();

        Pageable pageable = meetingList.getPageable();
        long total = meetingList.getTotalElements();

        for (Meeting m: meetingList.getContent()) {
            MeetingListRes mlr = new MeetingListRes();

            User user = m.getUser();
            MeetingCategory meetingCategory = m.getMeetingCategory();
            TopicCategory topicCategory = m.getTopicCategory();

            mlr.setTitle(m.getTitle());
            mlr.setDescription(m.getDescription());
            mlr.setActive(m.isActive());
            mlr.setParticipants(m.getParticipants());
            mlr.setOwnerId(user.getId());
            mlr.setOwnerNickname(user.getNickname());
            mlr.setMaskId(user.getMaskId());
            mlr.setProfileImg(user.getProfileImg());
            mlr.setMeetingCategoryId(meetingCategory.getId());
            mlr.setMeetingCategoryName(meetingCategory.getName());
            mlr.setTopicCategoryId(topicCategory.getId());
            mlr.setTopicCategoryName(topicCategory.getName());
            mlr.setMeetingId(m.getId());
            temp.add(mlr);

        }

        Page<MeetingListRes> res = new PageImpl<>(temp, pageable, total);

        return res;

    }

}
