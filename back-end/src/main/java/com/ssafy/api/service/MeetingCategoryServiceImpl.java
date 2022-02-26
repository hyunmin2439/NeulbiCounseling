package com.ssafy.api.service;

import com.ssafy.db.entity.Meeting;
import com.ssafy.db.entity.MeetingCategory;
import com.ssafy.db.entity.TopicCategory;
import com.ssafy.db.repository.MeetingCategoryRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("meetingCategoryService")
public class MeetingCategoryServiceImpl implements MeetingCategoryService{

    @Autowired
    MeetingCategoryRepositorySupport meetingCategoryRepositorySupport;

    @Override
    public Optional<MeetingCategory> findMeetingCategoryByCategoryId(Long meetingCategoryId) {
        Optional<MeetingCategory> meetingCategory = meetingCategoryRepositorySupport.findMeetingCategoryByCategoryId(meetingCategoryId);
        return meetingCategory;
    }
}
