package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.MeetingCategory;
import com.ssafy.db.entity.QMeeting;
import com.ssafy.db.entity.QMeetingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MeetingCategoryRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QMeetingCategory qMeetingCategory = QMeetingCategory.meetingCategory;

    public Optional<MeetingCategory> findMeetingCategoryByCategoryId(Long categoryId) {
        MeetingCategory meetingCategory = jpaQueryFactory.select(qMeetingCategory)
                .from(qMeetingCategory)
                .where(qMeetingCategory.id.eq(categoryId))
                .fetchOne();

        if(meetingCategory == null) return Optional.empty(); // topicCategory 비어있음
        return Optional.ofNullable(meetingCategory); // 비어있지 않음
    }
}
