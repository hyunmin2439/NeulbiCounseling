package com.ssafy.api.controller;

import com.ssafy.api.request.ReportPostReq;
import com.ssafy.api.service.ReportService;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 신고 기능 정의 API
 */
@Api(value = "신고 API", tags = {"report"})
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @PostMapping("/{category}")
    @ApiOperation(value = "게시글, 화상채팅 등 악성 유저를 신고하는 기능", notes = "<strong>선택한 유형으로 유저를 </strong>을 신고한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class),
    })
    public ResponseEntity<? extends BaseResponseBody> createReport(@RequestBody @ApiParam(value = "신고 정보", required = true) ReportPostReq reportInfo) {

        reportService.createReport(reportInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

}
