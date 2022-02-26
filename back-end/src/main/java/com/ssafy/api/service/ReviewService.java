package com.ssafy.api.service;

import com.ssafy.api.request.ReviewPostReq;
import com.ssafy.db.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *	리뷰 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface ReviewService {
    Review createReview(ReviewPostReq reviewPostReq);
    Page<Review> getWrittenReviewList(Long userId, Pageable pageable);
    Page<Review> getReceivedReviewList(Long consultantId, Pageable pageable);
}
