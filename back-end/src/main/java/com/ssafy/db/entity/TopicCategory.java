package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

/**
 * 주제 범주 모델 정의.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TopicCategory extends BaseEntity {
    // 취업, 진로, 학업
    String name; // 카테고리 이름

    @JsonManagedReference
    @OneToMany(mappedBy = "topicCategory") //참조를 당하는 쪽에서 읽기만 가능!
    @Builder.Default
    private List<ConsultantProfile> consultantProfileList = new LinkedList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "topicCategory") //참조를 당하는 쪽에서 읽기만 가능!
    @Builder.Default
    private List<Meeting> meetingList = new LinkedList<>();



}
