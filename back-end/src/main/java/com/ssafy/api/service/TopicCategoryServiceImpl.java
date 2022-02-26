package com.ssafy.api.service;

import com.ssafy.db.entity.MeetingCategory;
import com.ssafy.db.entity.TopicCategory;
import com.ssafy.db.repository.TopicCategoryRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("topicCategoryService")
public class TopicCategoryServiceImpl implements TopicCategoryService{

    @Autowired
    TopicCategoryRepositorySupport topicCategoryRepositorySupport;

    @Override
    public Optional<TopicCategory> findTopicCategoryByTopicId(Long topicCategoryId) {
        Optional<TopicCategory> topicCategory = topicCategoryRepositorySupport.findByTopicCategoryId(topicCategoryId);
        return topicCategory;
    }
}
