package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 공지사항 API ([Post] /noticle/{notice_id}) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("NoticePostReq")
public class NoticePostReq {
    @ApiModelProperty(name="유저 id")
    Long userId;
    @ApiModelProperty(name="공지사항 제목")
    String title;
    @ApiModelProperty(name="공지사항 내용")
    String description;
}