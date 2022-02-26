package com.ssafy.db.entity;

import lombok.*;
import javax.persistence.*;
import com.ssafy.db.converter.ReportAttributeConverter;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Report extends BaseEntity{

    Long reportUserId;
    String reportMsg;

    @Convert(converter = ReportAttributeConverter.class)
    String category;

    // 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    User user;

}