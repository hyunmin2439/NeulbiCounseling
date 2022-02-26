package com.ssafy.db.entity;

import lombok.*;

import javax.persistence.Entity;


// AllArgsConstructor 어노테이션은 생성자를 자동 생성합니다.
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom extends BaseEntity{

    // 유저 아이디 따로 받아옴
    Long userId;

    // 해당 컨설턴트 아이디 따로 받아옴
    Long consultantId;

}