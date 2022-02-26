package com.ssafy.api.service;

import com.ssafy.api.request.MeetingRoomPostReq;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 상담가 프로필 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("meetingService")
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    MeetingRepositorySupport meetingRepositorySupport;

    @Autowired
    MeetingHistoryRepositorySupport meetingHistoryRepositorySupport;

    @Autowired
    UserService userService;

    @Autowired
    MeetingCategoryService meetingCategoryService;

    @Autowired
    TopicCategoryService topicCategoryService;

    @Autowired
    ChatRoomService chatRoomService;

    @Override
    public Meeting createMeetingRoom(MeetingRoomPostReq meetingRoomRegisterInfo) {
        Long meetingCategoryId = meetingRoomRegisterInfo.getMeetingCategoryId();
        Long ownerUserId = meetingRoomRegisterInfo.getOwnerId();
        Long topicCategoryId =meetingRoomRegisterInfo.getTopicCategoryId();
        Long chatRoomId = meetingRoomRegisterInfo.getChatRoomId();

        User user = userService.getUserById(ownerUserId);
        Optional<MeetingCategory> meetingCategory = meetingCategoryService.findMeetingCategoryByCategoryId(meetingCategoryId);
        Optional<TopicCategory> topicCategory = topicCategoryService.findTopicCategoryByTopicId(topicCategoryId);
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        // 고해성사 생성
        Meeting meeting;
        if(meetingCategoryId == 1) {
            meeting = Meeting.builder()
                    .user(user)
                    .meetingCategory(meetingCategory.get())
                    .topicCategory(topicCategory.get())
                    .title(meetingRoomRegisterInfo.getTitle())
                    .description(meetingRoomRegisterInfo.getDescription())
                    .participants(meetingRoomRegisterInfo.getParticipants())
                    .chatRoomId(Long.valueOf(0))
                    .isActive(true)
                    .build();

        }

        // 고민상담 생성
        else {
            meeting = Meeting.builder()
                    .user(user)
                    .meetingCategory(meetingCategory.get())
                    .topicCategory(topicCategory.get())
                    .title(meetingRoomRegisterInfo.getTitle())
                    .description(meetingRoomRegisterInfo.getDescription())
                    .participants(meetingRoomRegisterInfo.getParticipants())
                    .chatRoomId(chatRoom.getId())
                    .isActive(true)
                    .build();
        }
        return meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public void updateMeetingRoomIsActiveTrueByMeetingRoomId(Long meetingRoomId) {
        Meeting meeting = meetingRepositorySupport.findMeetingRoomByMeetingRoomId(meetingRoomId).get();
        // false 일 경우
        if(!meeting.isActive()) {
            // true로 변환
            meeting.modifyIsActive(true);
        }
        meeting.setModifiedDate(LocalDateTime.now());
        meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public void updateMeetingRoomIsActiveFalseByMeetingRoomId(Long meetingRoomId) {
        Meeting meeting = meetingRepositorySupport.findMeetingRoomByMeetingRoomId(meetingRoomId).get();
        // true 일 경우
        if(meeting.isActive()) {
            // false로 변환
            meeting.modifyIsActive(false);
            // endTime 입력
            meeting.modifyCallEndTime(LocalDateTime.now());
        }
        meeting.setModifiedDate(LocalDateTime.now());
        meetingRepository.save(meeting);
    }

    @Override
    public Meeting getMeetingRoomByChatRoomId(Long chatRoomId) {
        Meeting meeting = meetingRepositorySupport.findMeetingRoomByChatRoomId(chatRoomId).get();
        return meeting;
    }
    @Override
    public Meeting getMeetingRoomByOwnerIdAndChatRoomId(Long ownerId, Long chatRoomId) {
        Meeting meeting = meetingRepositorySupport.findMeetingByOwnerIdAndChatRoomId(ownerId, chatRoomId).orElse(null);

        return meeting;
    }

    @Override
    public Meeting getMeetingRoomByMeetingRoomId(Long meetingRoomId) {
        Meeting meeting = meetingRepositorySupport.findMeetingRoomByMeetingRoomId(meetingRoomId).get();
        return meeting;
    }

    @Override
    public boolean getMeetingRoomIsActiveByChatRoomId(Long chatRoomId) {
        boolean isActive = meetingRepositorySupport.findMeetingRoomIsActiveByChatRoomId(chatRoomId);
        return isActive;
    }

    @Override
    public Page<Meeting> getAllMeetings(Pageable pageable) {
        Page<Meeting> meetings = meetingRepositorySupport.findAll(pageable);
        return meetings;
    }

    @Override
    public Page<Meeting> getAllMeetingsByUserId(Long userId, Pageable pageable) {
        Page<Meeting> meetings = meetingRepositorySupport.findAllByUserId(userId, pageable);
        return meetings;
    }

    @Override
    public Page<Meeting> getMeetingsByTopicCategory(Pageable pageable, Long topicCategoryId) {

        Page<Meeting> meetings = meetingRepositorySupport.findByTopicCategoryId(pageable,topicCategoryId);
        return meetings;
    }

    @Override
    public Page<Meeting> searchMeetings(Pageable pageable, String key, String value) {
        Page<Meeting> meetings = null;

        if(key.equals("title")){
            meetings = meetingRepositorySupport.findByTitle(pageable,value);
        }else if(key.equals("description")){
            meetings = meetingRepositorySupport.findByDescription(pageable,value);
        }else if(key.equals("ownerNickname")){
            meetings = meetingRepositorySupport.findByUserNickname(pageable,value);
        }

        return meetings;
    }


}
