package com.ssafy.api.service;

import com.ssafy.api.request.QnaDeleteReq;
import com.ssafy.api.request.QnaPostReq;
import com.ssafy.api.request.QnaPutReq;
import com.ssafy.db.entity.Qna;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.QnaRepository;
import com.ssafy.db.repository.QnaRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("QnaService")
public class QnaServiceImpl implements QnaService{
    @Autowired
    UserService userService;

    @Autowired
    QnaRepository qnaRepository;

    @Autowired
    QnaRepositorySupport qnaRepositorySupport;

    @Override
    public Page<Qna> getQnaList(Pageable pageable) {
        Page<Qna> qnas = qnaRepositorySupport.findAllByDeletedIsFalse(pageable);

        if(qnas.isEmpty()) return null;

        return qnas;
    }

    @Override
    public Page<Qna> searchQnaList(Pageable pageable, String searchWord) {
        Page<Qna> qnas = qnaRepositorySupport.findAllByDeletedIsFalseAndSearchWord(pageable, searchWord);

        if(qnas.isEmpty()) return null;

        return qnas;
    }

    @Override
    public Qna getQna(Long qnaId) {
        return qnaRepositorySupport.findQnaByIdAndDeletedIsFalse(qnaId).orElse(null);
    }

    @Override
    public int authenticate(Long userId, Long qnaId) {
        User user = userService.getUserById(userId);
        Qna qna = getQna(qnaId);

        // qna 정보가 없으면 실패
        if( user == null || qna == null )
            return 500;

        // 운영자가 아니고 qna가 잠겨있고 등록한 유저가 아니면 인증 에러
        if( !"ADMIN".equals(user.getRole()) && qna.isRocked() && !qna.getUser().getId().equals(userId) )
            return 401;

        return 200;
    }

    @Override
    public int createQna(QnaPostReq qnaInfo) {
        User user = userService.getUserById(qnaInfo.getUserId());

        // 유저 정보가 없으면 에러
        if( user == null) return 500;

        Qna qna = Qna.builder()
                .user(user)
                .title(qnaInfo.getTitle())
                .description(qnaInfo.getDescription())
                .isRocked(qnaInfo.isRocked())
                .build();

        if(qna == null) return 500;

        qnaRepository.save(qna);

        return 200;
    }

    @Override
    public int modifyQna(QnaPutReq qnaInfo) {
        User user = userService.getUserById(qnaInfo.getUserId());
        Qna qna = getQna(qnaInfo.getQnaId());

        // 둘 중 하나라도 없으면 에러
        if( user == null || qna == null )
            return 500;

        // 운영자가 또는 qna가 등록한 유저가 아니면 인증 에러
        if( !("ADMIN".equals(user.getRole()) || qna.getUser().getId().equals(qnaInfo.getUserId())) )
            return 401;

        qna.modify(qnaInfo.getTitle(), qnaInfo.getDescription());
        qna.setRocked(qnaInfo.isRocked());
        qna.setModifiedDate();
        qnaRepository.save(qna);

        return 200;
    }

    @Override
    public int deleteQna(QnaDeleteReq qnaInfo) {
        User user = userService.getUserById(qnaInfo.getUserId());
        Qna qna = getQna(qnaInfo.getQnaId());

        // 둘 중 하나라도 없으면 에러
        if( user == null || qna == null )
            return 500;

        // 운영자가 또는 qna가 등록한 유저가 아니면 인증 에러
        if( !("ADMIN".equals(user.getRole()) || qna.getUser().getId().equals(qnaInfo.getUserId())) )
            return 401;

        qna.setDeleted( true );
        qnaRepository.save(qna);

        return 200;
    }

    @Override
    public void modifyQnaIsAnswered(Long qnaId) {
        Qna qna = getQna(qnaId);
        qna.setIsAnswered();
        qnaRepository.save(qna);
    }
}
