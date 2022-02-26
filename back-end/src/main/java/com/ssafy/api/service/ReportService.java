package com.ssafy.api.service;

import com.ssafy.api.request.ReportPostReq;
import com.ssafy.db.entity.Report;

/**
 *	신고 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface ReportService {
    Report createReport(ReportPostReq reportInfo);
}
