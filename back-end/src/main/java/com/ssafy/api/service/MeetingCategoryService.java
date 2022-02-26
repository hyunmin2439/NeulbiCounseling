package com.ssafy.api.service;

import com.ssafy.db.entity.MeetingCategory;
import com.ssafy.db.entity.TopicCategory;

import java.util.Optional;

public interface MeetingCategoryService {

    Optional<MeetingCategory> findMeetingCategoryByCategoryId(Long meetingCategoryId);

}
