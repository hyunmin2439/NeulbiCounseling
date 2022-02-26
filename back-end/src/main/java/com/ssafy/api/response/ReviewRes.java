//package com.ssafy.api.response;
//
//import com.ssafy.db.entity.Meeting;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Getter;
//import lombok.Setter;
//
///**
// * 방 목록 ([POST] /) 요청에 대한 응답값 정의.
// */
//@Getter
//@Setter
//@ApiModel("MeetingResResponse")
//public class ReviewRes {
//    @ApiModelProperty(name="Review id")
//    Long id;
//    @ApiModelProperty(name="User id")
//    String userId;
//    @ApiModelProperty(name="Consultant profile id")
//    String title;
//    @ApiModelProperty(name="Community likeCnt")
//    String description;
//    @ApiModelProperty(name="Community viewCnt")
//    double point;
//
//    public static ReviewRes of(Meeting meeting) {
//
//        return res;
//    }
//}
