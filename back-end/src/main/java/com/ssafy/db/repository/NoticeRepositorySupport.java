package com.ssafy.db.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.Notice;
import com.ssafy.db.entity.QNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class NoticeRepositorySupport {

    @Autowired
    JPAQueryFactory jpaQueryFactory;
    QNotice qNotice = QNotice.notice;

    public Optional<Notice> findNoticeByIdAndDeletedIsFalse(Long id) {
        Notice notice = jpaQueryFactory
                .select(qNotice)
                .from(qNotice)
                .where(qNotice.id.eq(id).and(qNotice.isDeleted.eq(false)))
                .distinct()
                .fetchOne();

        if (notice == null) return Optional.empty();

        return Optional.ofNullable(notice);
    }

    public Page<Notice> findAllByDeletedIsFalse(Pageable pageable) {
        QueryResults<Notice> notices = jpaQueryFactory
                .select(qNotice)
                .from(qNotice)
                .where(qNotice.isDeleted.eq(false))
                .orderBy(qNotice.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        if (notices == null) return Page.empty();

        return new PageImpl<Notice>(notices.getResults(), pageable, notices.getTotal());
    }
}
