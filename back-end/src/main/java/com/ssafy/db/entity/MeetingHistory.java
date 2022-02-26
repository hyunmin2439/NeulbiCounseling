package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MeetingHistory extends BaseEntity {

    String action; // CREATE, JOIN, EXIT
    LocalDateTime insertedTime; // 접속 시간

    // 외래키 관계 설정 아직 안함

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    Meeting meeting;

    public LocalDateTime getInsertedTime() { return insertedTime.plusHours(9); }

    public void modifyAction(String action) {
        this.action = action;
    }

}
