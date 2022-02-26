package com.ssafy.api.response;

import com.ssafy.db.entity.Qna;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("QnaDetailResponse")
public class QnaDetailRes {
    @ApiModelProperty(name = "Qna")
    QnaRes qnaRes;
    @ApiModelProperty(name = "Answer List")
    List<AnswerRes> answerResList;

    public static QnaDetailRes of(Qna qna) {
        QnaDetailRes res = new QnaDetailRes();
        res.setQnaRes(QnaRes.ofDetail(qna));

        List<AnswerRes> answerList = new ArrayList<>();
        qna.getAnswerList().forEach( answer -> {
            if(!answer.isDeleted())
                answerList.add(AnswerRes.of(answer));
        });

        res.setAnswerResList(answerList);

        return res;
    }
}
