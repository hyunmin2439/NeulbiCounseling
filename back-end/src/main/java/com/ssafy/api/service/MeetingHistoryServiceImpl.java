package com.ssafy.api.service;

import com.ssafy.api.request.MeetingRoomPostReq;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.MeetingHistoryRepository;
import com.ssafy.db.repository.MeetingHistoryRepositorySupport;
import com.ssafy.db.repository.MeetingRepository;
import com.ssafy.db.repository.MeetingRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 상담가 프로필 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("meetingHistoryService")
public class MeetingHistoryServiceImpl implements MeetingHistoryService {

    @Autowired
    MeetingHistoryRepository meetingHistoryRepository;

    @Autowired
    MeetingHistoryRepositorySupport meetingHistoryRepositorySupport;

    @Override
    public MeetingHistory createMeetingHistory(Meeting meeting) {

        MeetingHistory meetingHistory = MeetingHistory.builder()
                .action("CREATE")
                .user(meeting.getUser())
                .meeting(meeting)
                .insertedTime(LocalDateTime.now())
                .build();
        return meetingHistoryRepository.save(meetingHistory);
    }

    @Override
    public Long getMeetingHistoryJoinCountByMeetingRoomId(Long meetingRoomId) {
        Long joinedCount = meetingHistoryRepositorySupport.findMeetingHistoryJoinCountByMeetingRoomId(meetingRoomId);
        return joinedCount;
    }

    @Override
    public List<MeetingHistory> getMeetingHistoryByMeetingRoomId(Long meetingRoomId) {
        List<MeetingHistory> meetingHistoryList = meetingHistoryRepositorySupport.findMeetingHistoriesByMeetingId(meetingRoomId);
        return meetingHistoryList;
    }

//    @Override
//    public MeetingHistory getMeetingHistoryByMeetingRoomId(Long userId, Long meetingRoomId) {
//        MeetingHistory meetingHistory = meetingHistoryRepositorySupport.findMeetingHistoryByUserIdAndMeetingId(userId, meetingRoomId);
//        return meetingHistory;
//    }

    @Override
    public long getCountMeetingHistoriesByMeetingIdAndActionCreateOrActionJoin(Long meetingId) {
        System.out.println("---------------------------------------------------");
        System.out.println(meetingId);

        return meetingHistoryRepositorySupport.countMeetingHistoriesByMeetingIdAndActionCreateOrActionJoin(meetingId);
    }

    @Override
    public void createMeetingHistoryByUserAndMeetingRoom(User user, Meeting meetingRoom) {

        MeetingHistory meetingHistory = MeetingHistory.builder()
                .action("JOIN")
                .user(user)
                .meeting(meetingRoom)
                .insertedTime(LocalDateTime.now())
                .build();

        meetingHistoryRepository.save(meetingHistory);
    }

    @Override
    public void updateMeetingHistoryExitByUserAndMeetingRoom(User user, Meeting meetingRoom) {
        Long userId = user.getId();
        Long meetingId = meetingRoom.getId();

        MeetingHistory meetingHistory = meetingHistoryRepositorySupport.findMeetingHistoryJOINByUserIdAndMeetingId(userId, meetingId);

        // 수정 시간 변경
        meetingHistory.setModifiedDate(LocalDateTime.now());
        // EXIT으로 변경
        meetingHistory.modifyAction("EXIT");

        meetingHistoryRepository.save(meetingHistory);
    }

    @Override
    public void updateMeetingHistoryExitWhenOwnerByUserAndMeetingRoom(User user, Meeting meetingRoom) {
        Long userId = user.getId();
        Long meetingId = meetingRoom.getId();

        MeetingHistory meetingHistory = meetingHistoryRepositorySupport.findMeetingHistoryCreateByUserIdAndMeetingId(userId, meetingId);

        // 수정 시간 변경
        meetingHistory.setModifiedDate(LocalDateTime.now());
        // EXIT으로 변경
        meetingHistory.modifyAction("EXIT");

        meetingHistoryRepository.save(meetingHistory);
    }

    @Override
    public Page<MeetingHistory> getAdviceByUserId(Long userId, Pageable pageable) {
        Page<MeetingHistory> meetingHistories = meetingHistoryRepositorySupport.findAdviceMeetingHistoriesByUserId(userId, pageable);
        return meetingHistories;
    }

    @Override
    public Page<MeetingHistory> getConfessionByUserId(Long userId, Pageable pageable) {
        Page<MeetingHistory> meetingHistories = meetingHistoryRepositorySupport.findConfessionMeetingHistoriesByUserId(userId, pageable);
        return meetingHistories;
    }
}
