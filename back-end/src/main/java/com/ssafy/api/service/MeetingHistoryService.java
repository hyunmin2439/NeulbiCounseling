package com.ssafy.api.service;

import com.ssafy.api.request.MeetingRoomPostReq;
import com.ssafy.db.entity.Meeting;
import com.ssafy.db.entity.MeetingHistory;
import com.ssafy.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *	상담가 프로필 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface MeetingHistoryService {
    MeetingHistory createMeetingHistory(Meeting meeting);
    Long getMeetingHistoryJoinCountByMeetingRoomId(Long meetingRoomId);
    //MeetingHistory getMeetingHistoryByMeetingRoomId(Long userId, Long meetingRoomId);
    List<MeetingHistory> getMeetingHistoryByMeetingRoomId(Long meetingRoomId);
    long getCountMeetingHistoriesByMeetingIdAndActionCreateOrActionJoin(Long meetingId);
    void createMeetingHistoryByUserAndMeetingRoom(User user, Meeting meetingRoom);
    void updateMeetingHistoryExitByUserAndMeetingRoom(User user, Meeting meetingRoom);
    void updateMeetingHistoryExitWhenOwnerByUserAndMeetingRoom(User user, Meeting meetingRoom);

    Page<MeetingHistory> getAdviceByUserId(Long userId, Pageable pageable);
    Page<MeetingHistory> getConfessionByUserId(Long userId, Pageable pageable);
}
