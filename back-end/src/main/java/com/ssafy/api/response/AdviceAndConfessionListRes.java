package com.ssafy.api.response;


import com.ssafy.db.entity.Meeting;
import com.ssafy.db.entity.MeetingHistory;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("AdviceResponse")
@NoArgsConstructor
@AllArgsConstructor
public class AdviceAndConfessionListRes {
    MeetingHistory meetingHistory;
    Meeting meeting;
    // 방장의 프로필 이미지
    String userProfileImg;
    // 방장의 마스크 이미지 아이디
    int userMaskId;
    // 방장의 닉네임
    String userNickname;
    // 방장 ID번호
    Long userId;
    Long topicCategoryId;

    public AdviceAndConfessionListRes(Meeting meetings, MeetingHistory history) {
        this.meetingHistory = history;
        this.meeting = meetings;

        this.userProfileImg = meetings.getUser().getProfileImg();
        this.userMaskId = meetings.getUser().getMaskId();
        this.topicCategoryId = meetings.getTopicCategory().getId();

    }

    public static Page<AdviceAndConfessionListRes> of (Page<MeetingHistory> meetingHistories) {
        List<AdviceAndConfessionListRes> temp = new ArrayList<>();

        Pageable pageable = meetingHistories.getPageable();
        long total = meetingHistories.getTotalElements();

        for(MeetingHistory meetingHistory : meetingHistories) {
            AdviceAndConfessionListRes aclr = new AdviceAndConfessionListRes();
            aclr.setMeeting(meetingHistory.getMeeting());
            aclr.setMeetingHistory(meetingHistory);
            aclr.setUserId(meetingHistory.getMeeting().getUser().getId());
            aclr.setUserNickname(meetingHistory.getMeeting().getUser().getNickname());
            aclr.setUserMaskId(meetingHistory.getMeeting().getUser().getMaskId());
            aclr.setUserProfileImg(meetingHistory.getMeeting().getUser().getProfileImg());
            aclr.setTopicCategoryId(meetingHistory.getMeeting().getTopicCategory().getId());

            temp.add(aclr);
        }
        Page<AdviceAndConfessionListRes> res = new PageImpl<>(temp, pageable, total);
        return res;
    }
}
