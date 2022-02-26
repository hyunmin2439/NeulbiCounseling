package com.ssafy.api.service;

import com.ssafy.api.request.CommentPutReq;
import com.ssafy.db.entity.Comment;
import com.ssafy.api.request.CommentPostReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    public Comment createComment(CommentPostReq commentInfo);
    public Comment modifyComment(CommentPutReq commentInfo);
    public boolean deleteComment(CommentPutReq commentInfo);
}
