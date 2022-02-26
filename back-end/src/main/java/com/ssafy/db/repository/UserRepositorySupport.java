package com.ssafy.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.ConsultantListRes;
import com.ssafy.db.entity.ConsultantProfile;
import com.ssafy.db.entity.QConsultantProfile;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;
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
public class UserRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;
    QConsultantProfile qConsultantProfile = QConsultantProfile.consultantProfile;

    public Optional<User> findByEmail(String userEmail) {
        // Querydsl
        // JPQL과 Querydsl에서 동일한 작업(특정 회원 1명 조회)를 하는 코드이다.
        // 두 개의 큰 차이점으로 쿼리 문법 오류를 JPQL은 실행 시점에 발견할 수 있으며, Querydsl은 컴파일 시점에 발견 가능
        User user = jpaQueryFactory.select(qUser).from(qUser)   // 2차 오류 원인 가능
                .where(qUser.email.eq(userEmail)).fetchOne(); // fetchOne() : 단건 조회시 사용

        if (user == null) return Optional.empty(); // user 비어있음
        return Optional.ofNullable(user); // 비어있지 않음
    }

    public Optional<User> findByNickname(String userNickname) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.nickname.eq(userNickname)).fetchOne();
        if (user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }

    public Optional<User> findUserById(Long userId) {
        System.out.println(">>>>>>>>>>>><<<<<<<<<<<<<<>>>>>>>>>>>>>><<<<<<<<<<<<<<");
        System.out.println(userId);
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.id.eq(userId)).fetchOne();

        if (user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }

    public List<User> findFirst10ByOrderByPointTotDesc() {
        List<User> users = jpaQueryFactory
                .select(qUser)
                .from(qUser)
                .orderBy(qUser.pointTot.desc()).limit(5).where(qUser.isConsultant.eq(true)).fetch();

        if (users == null) return Collections.emptyList();
        return users;
    }


}
