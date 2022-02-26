package com.ssafy.api.service;

import com.ssafy.api.request.AnswerDeleteReq;
import com.ssafy.api.request.AnswerPostReq;
import com.ssafy.api.request.AnswerPutReq;
import com.ssafy.db.entity.Answer;

public interface AnswerService {
    Answer getAnswer(Long answerId);
    int createAnswer(AnswerPostReq answerInfo);
    int modifyAnswer(AnswerPutReq answerInfo);
    int deleteAnswer(AnswerDeleteReq answerInfo);
}
