package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingCategory extends BaseEntity{

    // 고민상담, 고해성사 구분
    String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "meetingCategory") //참조를 당하는 쪽에서 읽기만 가능!
    private List<Meeting> meetingList = new LinkedList<>();

}
