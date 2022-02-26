package com.ssafy.db.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class LikeCheckRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QLikeCheck qLikeCheck = QLikeCheck.likeCheck;

    // 좋아요 했는지 확인
    public Optional<LikeCheck> findLikeCheckByUserIdAndCommunityId(Long communityId, Long userId) {
        LikeCheck likeCheck = jpaQueryFactory
                .select(qLikeCheck)
                .from(qLikeCheck)
                .where(qLikeCheck.user.id.eq(userId).and(qLikeCheck.community.id.eq(communityId)))
                .fetchOne();
        if (likeCheck == null) return Optional.empty();

        return Optional.ofNullable(likeCheck);


    }

}
