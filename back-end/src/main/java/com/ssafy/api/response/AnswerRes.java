package com.ssafy.api.response;

import com.ssafy.db.entity.Answer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("AnswerResponse")
public class AnswerRes {
    @ApiModelProperty(name = "Answer id")
    Long answerId;
    @ApiModelProperty(name = "User nickname")
    String userNickname;
    @ApiModelProperty(name = "Answer description")
    String description;
    @ApiModelProperty(name = "Answer date")
    LocalDateTime date;

    public static AnswerRes of(Answer answer) {
        AnswerRes res = new AnswerRes();

        res.setAnswerId(answer.getId());
        res.setUserNickname(answer.getUser().getNickname());
        res.setDescription(answer.getDescription());

        LocalDateTime modifiedDate = answer.getModifiedDate();
        res.setDate( modifiedDate != null ? modifiedDate.plusHours(9) : answer.getCreatedDate().plusHours(9) );

        return res;
    }
}
