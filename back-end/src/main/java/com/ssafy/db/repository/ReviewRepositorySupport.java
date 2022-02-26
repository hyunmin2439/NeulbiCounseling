package com.ssafy.db.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QReview;
import com.ssafy.db.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositorySupport {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    QReview qReview = QReview.review;

    public Page<Review> findAllByUserId(Long userId, Pageable pageable) {
        QueryResults<Review> review = jpaQueryFactory.select(qReview)
                .from(qReview)
                .where(qReview.user.id.eq(userId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        if(review == null) return Page.empty();
        return new PageImpl<>(review.getResults(), pageable, review.getTotal());
    }

    public Page<Review> findAllByConsultantId(Long consultantId, Pageable pageable) {
        QueryResults<Review> review = jpaQueryFactory.select(qReview)
                .from(qReview)
                .where(qReview.consultantProfile.id.eq(consultantId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        if(review == null) return Page.empty();
        return new PageImpl<>(review.getResults(), pageable, review.getTotal());
    }

}
