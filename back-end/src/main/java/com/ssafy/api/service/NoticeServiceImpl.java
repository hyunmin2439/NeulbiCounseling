package com.ssafy.api.service;

import com.ssafy.api.request.NoticeDeleteReq;
import com.ssafy.api.request.NoticePostReq;
import com.ssafy.api.request.NoticePutReq;
import com.ssafy.db.entity.Notice;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.NoticeRepository;
import com.ssafy.db.repository.NoticeRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("NoticeService")
public class NoticeServiceImpl implements NoticeService{
    @Autowired
    UserService userService;

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    NoticeRepositorySupport noticeRepositorySupport;

    @Override
    public Page<Notice> getNoticeList(Pageable pageable) {
        Page<Notice> notices = noticeRepositorySupport.findAllByDeletedIsFalse(pageable);

        if(notices.isEmpty()) return null;

        return notices;
    }

    @Override
    public Notice getNotice(Long noticeId) {
        return noticeRepositorySupport.findNoticeByIdAndDeletedIsFalse(noticeId).orElse(null);
    }

    @Override
    public Notice getNoticeDetail(Long noticeId) {
        Notice notice = getNotice(noticeId);

        if (notice == null) return null;

        notice.increaseViewCnt();
        noticeRepository.save(notice);

        return notice;
    }

    @Override
    public int createNotice(NoticePostReq noticeInfo) {
        User user = userService.getUserById(noticeInfo.getUserId());

        // 유저 정보 없거나 운영자가 아닐 경우
        if( user == null || !"ADMIN".equals(user.getRole()) )
            return 401;

        Notice notice = Notice.builder()
                .user(user)
                .title(noticeInfo.getTitle())
                .description(noticeInfo.getDescription())
                .build();

        if(notice == null) return 500;

        noticeRepository.save(notice);

        return 200;
    }

    @Override
    public int modifyNotice(NoticePutReq noticeInfo) {
        User user = userService.getUserById(noticeInfo.getUserId());

        // 유저 정보 없거나 운영자가 아닐 경우
        if( user == null || !"ADMIN".equals(user.getRole()) )
            return 401;

        Notice notice = noticeRepositorySupport.findNoticeByIdAndDeletedIsFalse(noticeInfo.getNoticeId()).orElse(null);

        if(notice == null) return 500;

        notice.modify(noticeInfo.getTitle(), noticeInfo.getDescription());
        notice.setModifiedDate();
        noticeRepository.save(notice);

        return 200;
    }

    @Override
    public int deleteNotice(NoticeDeleteReq noticeInfo) {
        User user = userService.getUserById(noticeInfo.getUserId());

        // 유저 정보 없거나 운영자가 아닐 경우
        if( user == null || !"ADMIN".equals(user.getRole()) )
            return 401;

        Notice notice = noticeRepositorySupport.findNoticeByIdAndDeletedIsFalse(noticeInfo.getNoticeId()).orElse(null);

        if(notice == null) return 500;

        notice.setDeleted( true );
        noticeRepository.save(notice);

        return 200;
    }
}
