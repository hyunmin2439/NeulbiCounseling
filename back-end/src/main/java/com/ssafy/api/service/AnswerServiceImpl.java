package com.ssafy.api.service;

import com.ssafy.api.request.AnswerDeleteReq;
import com.ssafy.api.request.AnswerPostReq;
import com.ssafy.api.request.AnswerPutReq;
import com.ssafy.db.entity.Answer;
import com.ssafy.db.entity.Qna;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AnswerService")
public class AnswerServiceImpl implements AnswerService{
    @Autowired
    UserService userService;

    @Autowired
    QnaService qnaService;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    AnswerRepositorySupport answerRepositorySupport;

    @Override
    public Answer getAnswer(Long answerId) {
        return answerRepositorySupport.findAnswerByIdAndDeletedIsFalse(answerId).orElse(null);
    }

    @Override
    public int createAnswer(AnswerPostReq answerInfo) {
        User user = userService.getUserById(answerInfo.getUserId());
        Qna qna = qnaService.getQna(answerInfo.getQnaId());

        // 유저 정보나 Q&A가 없으면
        if(user == null || qna == null) return 500;

        // Q&A를 작성한 유저가 아니면
        if( !("ADMIN".equals(user.getRole()) || qna.getUser().getId().equals(answerInfo.getUserId()) ) )
            return 401;

        Answer answer = Answer.builder()
                .user(user)
                .qna(qna)
                .description(answerInfo.getDescription())
                .build();

        // 제대로 build 되지 않았으면
        if (answer == null) return 500;

        answerRepository.save(answer);

        return 200;
    }

    @Override
    public int modifyAnswer(AnswerPutReq answerInfo) {
        User user = userService.getUserById(answerInfo.getUserId());
        Answer answer = getAnswer(answerInfo.getAnswerId());

        // 둘 중 하나라도 없으면 에러
        if( user == null || answer == null )
            return 500;

        // 운영자가 또는 answer가 등록한 유저가 아니면 인증 에러
        if( !("ADMIN".equals(user.getRole()) || answer.getUser().getId().equals(answerInfo.getUserId())) )
            return 401;

        answer.modify(answerInfo.getDescription());
        answer.setModifiedDate();
        answerRepository.save(answer);

        return 200;
    }

    @Override
    public int deleteAnswer(AnswerDeleteReq answerInfo) {
        User user = userService.getUserById(answerInfo.getUserId());
        Answer answer = getAnswer(answerInfo.getAnswerId());

        // 둘 중 하나라도 없으면 에러
        if( user == null || answer == null )
            return 500;

        // 운영자가 또는 answer가 등록한 유저가 아니면 인증 에러
        if( !("ADMIN".equals(user.getRole()) || answer.getUser().getId().equals(answerInfo.getUserId())) )
            return 401;

        answer.setDeleted( true );
        answerRepository.save(answer);

        return 200;
    }
}
