package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Qna extends BaseEntity {
    String title; //  게시글 제목
    String description; // 게시글 내용
    boolean isRocked; // 잠김 여부
    boolean isDeleted; // 글 삭제여부
    boolean isAnswered; // 글 답변완료 여부

    // 외래키
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "qna") //참조를 당하는 쪽에서 읽기만 가능!
    @Builder.Default
    private List<Answer> answerList = new LinkedList<>();

    public void setDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public void setRocked(boolean isRocked) { this.isRocked = isRocked; }

    public void modify(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setIsAnswered() {
        this.isAnswered = true;
    }
}
