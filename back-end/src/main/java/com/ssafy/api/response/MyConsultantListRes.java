package com.ssafy.api.response;

import com.ssafy.db.entity.MyConsultant;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("MyConsultantListResponse")
public class MyConsultantListRes {

    @ApiModelProperty(name="선호하는 컨설턴트 리스트", example = "선호하는 컨설턴트 리스트")
    List<MyConsultant> myConsultantList;

    public static MyConsultantListRes of (List<MyConsultant> myConsultant) {
        MyConsultantListRes res = new MyConsultantListRes();
        res.setMyConsultantList(myConsultant);
        return res;
    }

}
