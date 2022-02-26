package com.ssafy.api.service;

import com.ssafy.api.request.CommentPostReq;
import com.ssafy.api.request.CommentPutReq;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.CommentRepository;
import com.ssafy.db.repository.CommentRepositorySupport;

import com.ssafy.db.repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommunityService communityService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentRepositorySupport commentRepositorySupport;

    // 댓글 등록
    @Override
    public Comment createComment(CommentPostReq commentInfo) {

        User user = userRepository.findUserById(commentInfo.getUserId()).orElse(null);
        Community community = (Community) communityService.getCommunity(commentInfo.getCommunityId());

        int groupNum = commentInfo.getGroupNum();
        Comment comment = Comment.builder()
                .description(commentInfo.getDescription())
                .groupNum(groupNum)
                .layer(groupNum == 0 ? 0 : 1)
                .isDeleted(false)
                .user(user)
                .community(community)
                .build();

        comment = commentRepository.save(comment);

        if(groupNum == 0) {
            int groupId = (int)((long)comment.getId());
            comment.setGroupNum(groupId);
            comment = commentRepository.save(comment);
        }

        return comment;
    }

    // 댓글 수정
    @Override
    public Comment modifyComment(CommentPutReq commentInfo) {
        long userId = commentInfo.getUserId();
        Comment comment = commentRepository.findCommentById(commentInfo.getCommentId()).orElse(null);

        // 댓글 쓴 사람이랑 같지 않거나 or 댓글이 없을 경우 or 이미 삭제된 경우
        if(userId != comment.getUser().getId() || comment == null || comment.isDeleted()) return null;

        comment.modifyDescription(commentInfo.getDescription());
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    @Override
    public boolean deleteComment(CommentPutReq commentInfo) {
        long userId = commentInfo.getUserId();
        Comment comment = commentRepository.findCommentById(commentInfo.getCommentId()).orElse(null);

        // 댓글 쓴 사람이랑 같지 않거나 or 댓글이 없을 경우 or 이미 삭제된 경우
        if(userId != comment.getUser().getId() || comment == null || comment.isDeleted()) return false;

        int groupNum = comment.getGroupNum();
        int layer = comment.getLayer();

        // 현재 댓글 지우기
        comment.setDeleted(true);
        commentRepository.save(comment);

        // 부모 댓글이면 자식 댓글들도 다 지우기
        if(layer == 0) {
            List<Comment> childList = commentRepositorySupport.findAllByEqualsGroupNum(groupNum);

            childList.forEach(childComment -> {
                childComment.setDeleted(true);
                commentRepository.save(childComment);
            });
        }

        return true;
    }
}
