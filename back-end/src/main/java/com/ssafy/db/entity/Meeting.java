package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Meeting extends BaseEntity{

    LocalDateTime callEndTime;
    String title;
    String description;
    boolean isActive;
    int participants;
    Long chatRoomId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id")
    User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="meeting_category_id")
    MeetingCategory meetingCategory;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topic_category_id")
    TopicCategory topicCategory;

//    @JsonBackReference
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="chatroom_id")
//    ChatRoom chatRoom;

    @JsonManagedReference
    @OneToMany(mappedBy = "meeting") //참조를 당하는 쪽에서 읽기만 가능!
    @Builder.Default
    private List<MeetingHistory> meetingHistoryList = new LinkedList<>();

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "meeting") //참조를 당하는 쪽에서 읽기만 가능!
    private List<UserMeeting> userMeetingList = new LinkedList<>();

    public void modifyIsActive(boolean isActive){
        this.isActive = isActive;
    }

    public void modifyCallEndTime (LocalDateTime callEndTime) {
        this.callEndTime = callEndTime;
    }
}
