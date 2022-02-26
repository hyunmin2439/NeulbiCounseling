package com.ssafy.api.response;

import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("ConsultantRankRes")
public class ConsultantRankRes {

    @ApiModelProperty(name = "User nickname")
    String nickname;
    @ApiModelProperty(name = "User pointTot")
    double pointTot;


    public static List<ConsultantRankRes> of(List<User> users) {

        List<ConsultantRankRes> res = new ArrayList<>();
        for (User u : users) {
            ConsultantRankRes c = new ConsultantRankRes();

            c.setNickname(u.getNickname());
            c.setPointTot(u.getPointTot());

            res.add(c);


        }

        return res;
    }

}
