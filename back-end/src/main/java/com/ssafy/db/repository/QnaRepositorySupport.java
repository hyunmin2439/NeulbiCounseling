package com.ssafy.db.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QQna;
import com.ssafy.db.entity.Qna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class QnaRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QQna qQna = QQna.qna;

    public Optional<Qna> findQnaByIdAndDeletedIsFalse(Long id) {

        Qna qna = jpaQueryFactory
                .select(qQna)
                .from(qQna)
                .where(qQna.id.eq(id)
                        .and(qQna.isDeleted.eq(false))
                )
                .distinct()
                .fetchOne();

        if (qna == null) return Optional.empty();

        return Optional.ofNullable(qna);
    }

    public Page<Qna> findAllByDeletedIsFalse(Pageable pageable) {
        QueryResults<Qna> qna = jpaQueryFactory
                .select(qQna)
                .from(qQna)
                .where(qQna.isDeleted.eq(false))
                .orderBy(qQna.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetchResults();

        if (qna == null) return Page.empty();

        return new PageImpl<Qna>(qna.getResults(), pageable, qna.getTotal());
    }

    public Page<Qna> findAllByDeletedIsFalseAndSearchWord(Pageable pageable, String searchWord) {
        QueryResults<Qna> qna = jpaQueryFactory
                .select(qQna)
                .from(qQna)
                .where(qQna.isDeleted.eq(false)
                        .and(
                                qQna.title.contains(searchWord)
                                .or(qQna.description.contains(searchWord)
                                        .or(qQna.user.nickname.contains(searchWord))
                                )
                        )
                )
                .orderBy(qQna.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetchResults();

        if (qna == null) return Page.empty();

        return new PageImpl<Qna>(qna.getResults(), pageable, qna.getTotal());
    }
}
