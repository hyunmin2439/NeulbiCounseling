package com.ssafy.api.service;

import com.ssafy.api.request.NoticeDeleteReq;
import com.ssafy.api.request.NoticePostReq;
import com.ssafy.api.request.NoticePutReq;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
    Page<Notice> getNoticeList(Pageable pageable);
    Notice getNotice(Long noticeId);
    Notice getNoticeDetail(Long noticeId);
    int createNotice(NoticePostReq noticeInfo);
    int modifyNotice(NoticePutReq noticeInfo);
    int deleteNotice(NoticeDeleteReq noticeInfo);
}
