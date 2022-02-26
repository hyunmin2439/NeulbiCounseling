package com.ssafy.db.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message extends BaseEntity{

    private String message;
    private Long chatRoomId;
    private Long userId;
}
