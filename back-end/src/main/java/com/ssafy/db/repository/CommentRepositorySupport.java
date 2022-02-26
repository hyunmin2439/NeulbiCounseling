package com.ssafy.db.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.QComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QComment qComment = QComment.comment;

    // 게시글에 해당하는 댓글 전체 목록 불러오기
    public List<Comment> findAllByCommunityIdAndDeletedIsFalse(Long communityId) {
        QueryResults<Comment> cons = jpaQueryFactory
                .select(qComment)
                .from(qComment)
                .where(qComment.community.id.eq(communityId).and(qComment.isDeleted.eq(false)))
                .orderBy(qComment.groupNum.asc(), qComment.layer.asc(), qComment.id.asc())
                .fetchResults();

        if (cons == null) return null;

        return cons.getResults();
    }

    // 댓글 이후의 대댓글 전부 불러오기
    public List<Comment> findAllByEqualsGroupNum(int groupNum) {
        QueryResults<Comment> cons = jpaQueryFactory
                .select(qComment)
                .from(qComment)
                .where(qComment.groupNum.eq(groupNum))
                .fetchResults();

        if (cons == null) return null;

        return cons.getResults();
    }

    public Page<Comment> findAllByUserId(Pageable pageable, Long userId) {
        QueryResults<Comment> comment = jpaQueryFactory
                .select(qComment)
                .from(qComment)
                .where(qComment.user.id.eq(userId)
                        .and(qComment.isDeleted.eq(false)))
                .orderBy(qComment.id.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        if (comment == null) return Page.empty();

        return new PageImpl<Comment>(comment.getResults(), pageable, comment.getTotal());
    }

}
