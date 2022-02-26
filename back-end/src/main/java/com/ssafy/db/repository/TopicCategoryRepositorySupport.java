package com.ssafy.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.ConsultantListRes;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class TopicCategoryRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QTopicCategory qTopicCategory = QTopicCategory.topicCategory;

    public Optional<TopicCategory> findByTopicCategoryId(Long id){

        TopicCategory topicCategory = jpaQueryFactory.select(qTopicCategory).from(qTopicCategory)   // 2차 오류 원인 가능
                .where(qTopicCategory.id.eq(id)).fetchOne(); // fetchOne() : 단건 조회시 사용
        if(topicCategory == null) return Optional.empty(); // topicCategory 비어있음
        return Optional.ofNullable(topicCategory); // 비어있지 않음

    }




}
