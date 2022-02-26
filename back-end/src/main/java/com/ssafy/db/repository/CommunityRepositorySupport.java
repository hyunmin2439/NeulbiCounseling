package com.ssafy.db.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.QComment;
import com.ssafy.db.entity.QCommunity;
import com.ssafy.db.entity.QLikeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class CommunityRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QCommunity qCommunity = QCommunity.community;
    QComment qComment = QComment.comment;
    QLikeCheck qLikeCheck = QLikeCheck.likeCheck;

    // 게시글 전체 목록 불러오기
    // is_delete = false인 community를 가져온다.
    public Page<Community> findAllByDeletedIsFalse(Pageable pageable) {
        QueryResults<Community> community = jpaQueryFactory
                .select(qCommunity)
                .from(qCommunity)
                .where(qCommunity.isDeleted.eq(false))
                .orderBy(qCommunity.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetchResults();


        if (community == null) return Page.empty();

        return new PageImpl<Community>(community.getResults(), pageable, community.getTotal());
    }

    // 게시글 좋아요 수 순으로 정렬된 전체 목록 불러오기
    public Page<Community> findAllByDeletedIsFalseAndSortedLike(Pageable pageable) {
        QueryResults<Community> community = jpaQueryFactory
                .select(qCommunity)
                .from(qCommunity)
                .where(qCommunity.isDeleted.eq(false))
                .orderBy(qCommunity.likeCnt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        if (community == null) return Page.empty();

        return new PageImpl<Community>(community.getResults(), pageable, community.getTotal());
    }

    // 게시글 조회수 수 순으로 정렬된 전체 목록 불러오기
    public Page<Community> findAllByDeletedIsFalseAndSortedView(Pageable pageable) {
        QueryResults<Community> community = jpaQueryFactory
                .select(qCommunity)
                .from(qCommunity)
                .where(qCommunity.isDeleted.eq(false))
                .orderBy(qCommunity.viewCnt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        if (community == null) return Page.empty();

        return new PageImpl<Community>(community.getResults(), pageable, community.getTotal());
    }


    //  게시글 내용으로 검색
    public Page<Community> findAllByDeletedIsFalseAndDescriptionContains(Pageable pageable, String value) {
        QueryResults<Community> community = jpaQueryFactory
                .select(qCommunity)
                .from(qCommunity)
                .where(qCommunity.isDeleted.eq(false).and(qCommunity.description.contains(value)))
                .orderBy(qCommunity.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        if (community == null) return Page.empty();

        return new PageImpl<Community>(community.getResults(), pageable, community.getTotal());
    }

    // 게시글 제목으로 검색
    public Page<Community> findAllByDeletedIsFalseAndTitleContains(Pageable pageable, String value) {
        QueryResults<Community> community = jpaQueryFactory
                .select(qCommunity)
                .from(qCommunity)
                .where(qCommunity.isDeleted.eq(false).and(qCommunity.title.contains(value)))
                .orderBy(qCommunity.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();


        if (community == null) return Page.empty();

        return new PageImpl<Community>(community.getResults(), pageable, community.getTotal());
    }

    // 사용자 닉네임으로 검색
    public Page<Community> findAllByDeletedIsFalseAndUserNicknameContains(Pageable pageable, String value) {
        QueryResults<Community> community = jpaQueryFactory
                .select(qCommunity)
                .from(qCommunity)
                .where(qCommunity.isDeleted.eq(false).and(qCommunity.user.nickname.contains(value)))
                .orderBy(qCommunity.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();


        if (community == null) return Page.empty();

        return new PageImpl<Community>(community.getResults(), pageable, community.getTotal());
    }



    // 게시글 상세내용 불러오기
    public Optional<Community> findByIdAndCommentListFalse(Long id) {

        Community community = jpaQueryFactory
                .select(qCommunity)
                .from(qCommunity,qComment)
                .where(qCommunity.id.eq(id)).distinct().fetchOne();

        if (community == null) return Optional.empty();

        return Optional.ofNullable(community);

    }

    public Page<Community> findAllByUserId(Pageable pageable, Long userId) {
        QueryResults<Community> community = jpaQueryFactory
                .select(qCommunity)
                .from(qCommunity)
                .where(qCommunity.user.id.eq(userId))
                .orderBy(qCommunity.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        if (community == null) return Page.empty();

        return new PageImpl<Community>(community.getResults(), pageable, community.getTotal());
    }

}
