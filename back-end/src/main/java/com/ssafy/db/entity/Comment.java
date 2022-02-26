package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

/**
 * 댓글 모델 정의.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Comment extends BaseEntity implements Comparable<Comment> {
    String description; // 댓글 내용
    int groupNum; // 댓글 그룹
    int layer; // 계층
    boolean isDeleted; // 댓글 삭제여부

    // 외래키
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="community_id")
    Community community;

    public void modifyDescription(String description) { this.description = description; }

    public void setDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public void setGroupNum(int groupNum) { this.groupNum = groupNum; };

    @Override
    public int compareTo(Comment other) {

        if(this.groupNum != other.groupNum)
            return this.groupNum - other.groupNum;

        if(this.layer != other.layer)
            return this.layer - other.layer;

        int diffId = (int)(this.id - other.id);

        return diffId;
    }
}
