package com.ssafy.db.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.MeetingHistory;
import com.ssafy.db.entity.QMeeting;
import com.ssafy.db.entity.QMeetingHistory;
import com.ssafy.db.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MeetingHistoryRepositorySupport {

    @Autowired
    JPAQueryFactory jpaQueryFactory;
    QMeeting qMeeting = QMeeting.meeting;
    QMeetingHistory qMeetingHistory = QMeetingHistory.meetingHistory;

    public Long findMeetingHistoryJoinCountByMeetingRoomId(Long meetingRoomId) {
        Long joinCount = jpaQueryFactory.select(qMeetingHistory)
                                        .from(qMeetingHistory)
                                        .where(qMeetingHistory.meeting.id.eq(meetingRoomId).and(qMeetingHistory.action.eq("JOIN")))
                                        .fetchCount();

        return joinCount;
    }

    public MeetingHistory findMeetingHistoryByUserIdAndMeetingId(Long userId, Long meetingId) {
        MeetingHistory meetingHistory = jpaQueryFactory.select(qMeetingHistory)
                .from(qMeetingHistory)
                .where(qMeetingHistory.meeting.id.eq(meetingId).and(qMeetingHistory.user.id.eq(userId)))
                .fetchOne();

        return meetingHistory;
    }

    public MeetingHistory findMeetingHistoryJOINByUserIdAndMeetingId(Long userId, Long meetingId) {
        MeetingHistory meetingHistory = jpaQueryFactory.select(qMeetingHistory)
                .from(qMeetingHistory)
                .where(qMeetingHistory.meeting.id.eq(meetingId)
                        .and(qMeetingHistory.user.id.eq(userId))
                        .and(qMeetingHistory.action.eq("JOIN")))
                .fetchOne();

        return meetingHistory;
    }

    public MeetingHistory findMeetingHistoryCreateByUserIdAndMeetingId(Long userId, Long meetingId) {
        MeetingHistory meetingHistory = jpaQueryFactory.select(qMeetingHistory)
                .from(qMeetingHistory)
                .where(qMeetingHistory.meeting.id.eq(meetingId)
                        .and(qMeetingHistory.user.id.eq(userId))
                        .and(qMeetingHistory.action.eq("CREATE")))
                .fetchOne();

        return meetingHistory;
    }

    public List<MeetingHistory> findMeetingHistoriesByMeetingId(Long meetingId) {
        List<MeetingHistory> meetingHistory = jpaQueryFactory.select(qMeetingHistory)
                                            .from(qMeetingHistory)
                                            .where(qMeetingHistory.meeting.id.eq(meetingId))
                                            .fetch();

        return meetingHistory;
    }

    public long countMeetingHistoriesByMeetingIdAndActionCreateOrActionJoin(Long meetingId) {
        return jpaQueryFactory
                .select(qMeetingHistory.id)
                .from(qMeetingHistory, qMeeting)
                .where(
                        qMeetingHistory.meeting.id.eq(meetingId)
                                .and(qMeetingHistory.action.eq("CREATE")
                                        .or(qMeetingHistory.action.eq("JOIN")))
                )
                .distinct()
                .fetchCount();
    }

    public Page<MeetingHistory> findConfessionMeetingHistoriesByUserId(Long userId, Pageable pageable) {
        QueryResults<MeetingHistory> meetingHistory = jpaQueryFactory.select(qMeetingHistory)
                .from(qMeetingHistory)
                .where(qMeetingHistory.user.id.eq(userId)
                        .and(qMeetingHistory.meeting.meetingCategory.id.eq(Long.valueOf(1))))
                .orderBy(qMeetingHistory.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        if(meetingHistory == null) return Page.empty();
        return new PageImpl<>(meetingHistory.getResults(), pageable, meetingHistory.getTotal());
    }


    public Page<MeetingHistory> findAdviceMeetingHistoriesByUserId(Long userId, Pageable pageable) {
        QueryResults<MeetingHistory> meetingHistory = jpaQueryFactory.select(qMeetingHistory)
                .from(qMeetingHistory)
                .where(qMeetingHistory.user.id.eq(userId)
                        .and(qMeetingHistory.meeting.meetingCategory.id.eq(Long.valueOf(2))))
                .orderBy(qMeetingHistory.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        if(meetingHistory == null) return Page.empty();
        return new PageImpl<>(meetingHistory.getResults(), pageable, meetingHistory.getTotal());
    }
}
