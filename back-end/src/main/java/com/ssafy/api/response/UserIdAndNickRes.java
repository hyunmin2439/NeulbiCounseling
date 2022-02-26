package com.ssafy.api.response;

import com.ssafy.db.entity.MeetingHistory;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("UserIdAndNickResResponse")
public class UserIdAndNickRes {
    @ApiModelProperty(name="User userID 숫자 번호임")
    Long userId;

    @ApiModelProperty(name="User userID 숫자 번호임")
    String nickName;

    public static List<UserIdAndNickRes> of(List<MeetingHistory> historyList) {

        List<UserIdAndNickRes> userIdAndNickResList = new ArrayList<>();

        for(MeetingHistory meetingHistory : historyList) {
            UserIdAndNickRes res = new UserIdAndNickRes();
            res.setUserId(meetingHistory.getUser().getId());
            res.setNickName(meetingHistory.getUser().getNickname());

            userIdAndNickResList.add(res);

        }
        return userIdAndNickResList;
    }
}
