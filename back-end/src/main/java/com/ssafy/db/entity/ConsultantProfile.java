package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 상담가 프로필 모델 정의.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultantProfile extends BaseEntity{
    String description; // 한줄 소개(50자 제한)
    int consultingCnt; // 상담한 횟수

    // 외래키
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topic_category_id")
    TopicCategory topicCategory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "consultantProfile") //참조를 당하는 쪽에서 읽기만 가능!
    private List<Review> reviewList = new LinkedList<>();


    @Builder
    public ConsultantProfile(String description, int consultingCnt, TopicCategory topicCategory, User user) {
        this.description = description;
        this.consultingCnt = consultingCnt;
        this.topicCategory = topicCategory;
        this.user = user;
    }

    public void modifyDescription(String description){
        this.description = description;
    }

    public void modifyTopicCategory(TopicCategory topicCategory){
        this.topicCategory = topicCategory;
    }


}
