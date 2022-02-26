package com.ssafy.db.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 모델 간 공통 사항 정의.
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임
    Long id = null; // null, DB Auto Increment

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private LocalDateTime modifiedDate;

    public void setModifiedDate() {
        this.modifiedDate = LocalDateTime.now();
    }
}
