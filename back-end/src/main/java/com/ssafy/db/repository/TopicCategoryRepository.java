package com.ssafy.db.repository;

import com.ssafy.api.response.ConsultantListRes;
import com.ssafy.db.entity.ConsultantProfile;
import com.ssafy.db.entity.TopicCategory;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface TopicCategoryRepository extends JpaRepository<ConsultantProfile, Long> {

    Optional<TopicCategory> findByTopicCategoryId(Long id);


}