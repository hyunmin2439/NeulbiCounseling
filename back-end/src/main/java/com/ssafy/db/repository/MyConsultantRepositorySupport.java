package com.ssafy.db.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.MyConsultant;
import com.ssafy.db.entity.QMyConsultant;
import com.ssafy.db.entity.QUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public class MyConsultantRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QUser qUser = QUser.user;
    QMyConsultant qMyConsultant = QMyConsultant.myConsultant;

    public Page<MyConsultant> findMyConsultantListByUserId(Long userId, Pageable pageable) {

        QueryResults<MyConsultant> myConsultantList = jpaQueryFactory.select(qMyConsultant)
                .from(qMyConsultant)
                .where(qMyConsultant.user.id.eq(userId))
                .orderBy(qMyConsultant.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetchResults();

        return new PageImpl<MyConsultant>(myConsultantList.getResults(), pageable, myConsultantList.getTotal());
    }

    public void deleteMyConsultantByUserIdAndConsultantId(Long userId, Long consultantId){
        jpaQueryFactory.delete(qMyConsultant)
                .where(qMyConsultant.user.id.eq(userId)
                .and(qMyConsultant.consultant.id.eq(consultantId))).execute();
    }

    public Optional<MyConsultant> findMyConsultantByUserIdAndConsultantId(Long userId, Long consultantId) {

        MyConsultant myConsultant= jpaQueryFactory.select(qMyConsultant)
                .from(qMyConsultant)
                .where(qMyConsultant.user.id.eq(userId)
                        .and(qMyConsultant.consultant.id.eq(consultantId)))
                .fetchOne();
        // 해당 컨설턴트가 없으면 false
        if (myConsultant ==null) return Optional.empty();
        // 있으면 true
        else return Optional.ofNullable(myConsultant);
    }
}
