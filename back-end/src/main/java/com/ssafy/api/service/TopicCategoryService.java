package com.ssafy.api.service;

import com.ssafy.db.entity.MeetingCategory;
import com.ssafy.db.entity.TopicCategory;

import java.util.Optional;

public interface TopicCategoryService {

    Optional<TopicCategory> findTopicCategoryByTopicId(Long topicCategoryId);
}
