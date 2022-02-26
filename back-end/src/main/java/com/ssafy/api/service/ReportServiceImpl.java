package com.ssafy.api.service;

import com.ssafy.api.request.ReportPostReq;
import com.ssafy.db.entity.Report;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ReportRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *	신고 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReportRepository reportRepository;

    @Override
    public Report createReport(ReportPostReq reportInfo) {

        User user = userRepository.findUserById(reportInfo.getUserId()).orElse(null);

        Report report = Report.builder()
                .category(reportInfo.getCategory())
                .user(user)
                .reportUserId(reportInfo.getReportUserId())
                .reportMsg(reportInfo.getReportMsg())
                .build();

        // 신고 당한 횟수 증가
        User reportUser = userRepository.findUserById(reportInfo.getReportUserId()).orElse(null);
        reportUser.increaseReportCnt();
        userRepository.save(reportUser);

        return reportRepository.save(report);
    }

}
