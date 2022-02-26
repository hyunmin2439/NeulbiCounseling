package com.ssafy.db.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.request.MeetingRoomPostReq;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MeetingRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QMeeting qMeeting = QMeeting.meeting;
    Long categoryid = Long.valueOf(1);

    public Page<Meeting> findAll(Pageable pageable) {

        QueryResults<Meeting> meetings = jpaQueryFactory
                .select(qMeeting)
                .from(qMeeting)
                .where(qMeeting.isActive.eq(true).and(qMeeting.meetingCategory.id.eq(categoryid)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetchResults();

        if (meetings == null) return Page.empty();

        return new PageImpl<>(meetings.getResults(), pageable, meetings.getTotal());
    }

    public Page<Meeting> findAllByUserId(Long userId, Pageable pageable) {

        QueryResults<Meeting> meetings = jpaQueryFactory
                .select(qMeeting)
                .from(qMeeting)
                .where(qMeeting.isActive.eq(true).and(qMeeting.meetingCategory.id.eq(categoryid))
                        .and(qMeeting.user.id.eq(userId)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetchResults();

        if (meetings == null) return Page.empty();

        return new PageImpl<>(meetings.getResults(), pageable, meetings.getTotal());
    }

    public Page<Meeting> findByTopicCategoryId(Pageable pageable, Long topicCategoryId) {

        QueryResults<Meeting> meetings = jpaQueryFactory
                .select(qMeeting)
                .from(qMeeting)
                .where(qMeeting.topicCategory.id.eq(topicCategoryId)
                        .and(qMeeting.isActive.eq(true))
                        .and(qMeeting.meetingCategory.id.eq(categoryid)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetchResults();

        if (meetings == null) return Page.empty();

        return new PageImpl<>(meetings.getResults(), pageable, meetings.getTotal());
    }

    public Page<Meeting> findByTitle(Pageable pageable, String value) {
        QueryResults<Meeting> meetings = jpaQueryFactory
                .select(qMeeting)
                .from(qMeeting)
                .where(qMeeting.title.contains(value)
                        .and(qMeeting.isActive.eq(true))
                        .and(qMeeting.meetingCategory.id.eq(categoryid)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetchResults();

        if (meetings == null) return Page.empty();
        return new PageImpl<Meeting>(meetings.getResults(), pageable, meetings.getTotal());

    }

    public Page<Meeting> findByDescription(Pageable pageable, String value) {
        QueryResults<Meeting> meetings = jpaQueryFactory
                .select(qMeeting)
                .from(qMeeting)
                .where(qMeeting.description.contains(value)
                        .and(qMeeting.isActive.eq(true))
                        .and(qMeeting.meetingCategory.id.eq(categoryid)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetchResults();

        if (meetings == null) return Page.empty();
        return new PageImpl<Meeting>(meetings.getResults(), pageable, meetings.getTotal());

    }

    public Page<Meeting> findByUserNickname(Pageable pageable, String value) {
        QueryResults<Meeting> meetings = jpaQueryFactory
                .select(qMeeting)
                .from(qMeeting)
                .where(qMeeting.user.nickname.contains(value)
                        .and(qMeeting.isActive.eq(true))
                        .and(qMeeting.meetingCategory.id.eq(categoryid)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetchResults();

        if (meetings == null) return Page.empty();
        return new PageImpl<Meeting>(meetings.getResults(), pageable, meetings.getTotal());

    }

    public Optional<Meeting> findMeetingByOwnerIdAndChatRoomId(Long ownerId, Long chatRoomId) {
        Meeting meeting = jpaQueryFactory.select(qMeeting)
                .from(qMeeting)
                .where(qMeeting.user.id.eq(ownerId)
                        .and(qMeeting.chatRoomId.eq(chatRoomId)))
                .fetchOne();

        if(meeting == null) return Optional.empty();
        else return Optional.ofNullable(meeting);
    }

    public boolean findMeetingRoomIsActiveByChatRoomId(Long chatRoomId) {
        boolean isActive = Boolean.TRUE.equals(jpaQueryFactory.select(qMeeting.isActive)
                .from(qMeeting)
                .where(qMeeting.chatRoomId.eq(chatRoomId))
                .fetchOne());

        return isActive;
    }

    public Optional<Meeting> findMeetingRoomByMeetingRoomId(Long meetingRoomId) {
        Meeting meeting = jpaQueryFactory.select(qMeeting)
                .from(qMeeting)
                .where(qMeeting.id.eq(meetingRoomId))
                .fetchOne();

        if (meeting == null) return Optional.empty();
        else return Optional.ofNullable(meeting);
    }

    public Optional<Meeting> findMeetingRoomByChatRoomId(Long chatRoomId) {
        Meeting meeting = jpaQueryFactory.select(qMeeting)
                .from(qMeeting)
                .where(qMeeting.chatRoomId.eq(chatRoomId))
                .fetchOne();

        if(meeting == null) return Optional.empty();
        else return Optional.ofNullable(meeting);
    }
}
