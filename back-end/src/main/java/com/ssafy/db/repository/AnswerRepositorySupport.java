package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Answer;
import com.ssafy.db.entity.QAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AnswerRepositorySupport {

    @Autowired
    JPAQueryFactory jpaQueryFactory;
    QAnswer qAnswer = QAnswer.answer;
    public Optional<Answer> findAnswerByIdAndDeletedIsFalse(Long id) {

        Answer answer = jpaQueryFactory
                .select(qAnswer)
                .from(qAnswer)
                .where(qAnswer.id.eq(id)
                        .and(qAnswer.isDeleted.eq(false))
                )
                .distinct()
                .fetchOne();

        if (answer == null) return Optional.empty();

        return Optional.ofNullable(answer);
    }
}
