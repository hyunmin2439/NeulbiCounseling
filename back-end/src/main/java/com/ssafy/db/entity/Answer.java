package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Answer extends BaseEntity{
    String description; // 게시글 내용
    boolean isDeleted; // 글 삭제여부

    // 외래키
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    User user;

    // 외래키
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="qna_id")
    Qna qna;

    public void setDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public void modify(String description) { this.description = description; }
}
