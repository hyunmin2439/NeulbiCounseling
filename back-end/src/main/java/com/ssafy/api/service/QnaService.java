package com.ssafy.api.service;

import com.ssafy.api.request.QnaDeleteReq;
import com.ssafy.api.request.QnaPostReq;
import com.ssafy.api.request.QnaPutReq;
import com.ssafy.db.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaService {
    Page<Qna> getQnaList(Pageable pageable);
    Page<Qna> searchQnaList(Pageable pageable, String searchWord);
    Qna getQna(Long qnaId);
    int authenticate(Long userId, Long qnaId);
    int createQna(QnaPostReq qnaInfo);
    int modifyQna(QnaPutReq qnaInfo);
    int deleteQna(QnaDeleteReq qnaInfo);
    void modifyQnaIsAnswered(Long qnaId);
}
