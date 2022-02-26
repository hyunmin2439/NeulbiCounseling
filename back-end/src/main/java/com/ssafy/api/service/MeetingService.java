package com.ssafy.api.service;

import com.ssafy.api.request.MeetingRoomPostReq;
import com.ssafy.db.entity.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *	상담가 프로필 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface MeetingService {

    Meeting createMeetingRoom(MeetingRoomPostReq meetingRoomRegisterInfo);
    Meeting getMeetingRoomByOwnerIdAndChatRoomId(Long ownerId, Long chatRoom);
    boolean getMeetingRoomIsActiveByChatRoomId(Long chatRoomId);
    Meeting getMeetingRoomByChatRoomId(Long chatRoomId);
    Meeting getMeetingRoomByMeetingRoomId(Long meetingRoomId);
    void updateMeetingRoomIsActiveFalseByMeetingRoomId(Long meetingRoomId);
    void updateMeetingRoomIsActiveTrueByMeetingRoomId(Long meetingRoomId);

    Page<Meeting> getAllMeetings(Pageable pageable);
    Page<Meeting> getAllMeetingsByUserId(Long userId, Pageable pageable);

    Page<Meeting> getMeetingsByTopicCategory(Pageable pageable, Long topicCategoryId);

    Page<Meeting> searchMeetings(Pageable pageable, String key, String value);
}
