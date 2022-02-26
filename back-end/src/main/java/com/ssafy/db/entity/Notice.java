package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * 공지사항 모델 정의.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Notice extends BaseEntity {
    String title; //  게시글 제목
    String description; // 게시글 내용
    int viewCnt; // 조회수
    boolean isDeleted; // 글 삭제여부

    // 외래키
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    User user;

    public void increaseViewCnt() { this.viewCnt++; }

    public void setDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public void modify(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
