package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Qna답글 API ([Post] /answer/{qna_id}) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("AnswerPostRequest")
public class AnswerPostReq {
    @ApiModelProperty(name="유저 id")
    Long userId;
    @ApiModelProperty(name="Q&A id")
    Long qnaId;
    @ApiModelProperty(name="답글 내용")
    String description;
}
