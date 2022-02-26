package com.ssafy.api.response;

import com.ssafy.db.entity.Notice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("NoticeResponse")
public class NoticeRes {
    @ApiModelProperty(name = "Notice id")
    Long noticeId;
    @ApiModelProperty(name = "User nickname")
    String userNickname;
    @ApiModelProperty(name = "Notice title")
    String title;
    @ApiModelProperty(name = "Notice description")
    String description;
    @ApiModelProperty(name = "Notice date")
    LocalDateTime date;

    public static NoticeRes of(Notice notice) {
        NoticeRes res = new NoticeRes();

        res.setNoticeId(notice.getId());
        res.setUserNickname(notice.getUser().getNickname());
        res.setTitle(notice.getTitle());
        res.setDescription(notice.getDescription());

        LocalDateTime modifiedDate = notice.getModifiedDate();
        res.setDate( modifiedDate != null ? modifiedDate.plusHours(9) : notice.getCreatedDate().plusHours(9) );

        return res;
    }
}
