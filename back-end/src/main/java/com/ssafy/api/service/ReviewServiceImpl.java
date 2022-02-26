package com.ssafy.api.service;

import com.ssafy.api.request.ReviewPostReq;
import com.ssafy.db.entity.ConsultantProfile;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ReviewRepository;
import com.ssafy.db.repository.ReviewRepositorySupport;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *	리뷰 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("ReviewService")
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    UserService userService;

    @Autowired
    ProfileService profileService;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepositorySupport reviewRepositorySupport;

    @Override
    public Review createReview(ReviewPostReq reviewPostReq) {
        User user = userService.getUserById(reviewPostReq.getUserId());
        ConsultantProfile consultantProfile = profileService.getConsultantProfile(reviewPostReq.getConsultantId()).get();

        Review review = Review.builder()
                .user(user)
                .consultantProfile(consultantProfile)
                .description(reviewPostReq.getDescription())
                .point(reviewPostReq.getPoint())
                .build();

        // 받은 포인트만큼 User 테이블의 user point_tot에도 더해주기.
        User consultant = userService.getUserById(reviewPostReq.getConsultantId());
        consultant.addPoint(reviewPostReq.getPoint());
        userRepository.save(consultant);

        return reviewRepository.save(review);
    }

    @Override
    public Page<Review> getWrittenReviewList(Long userId, Pageable pageable) {
        Page<Review> reviews = reviewRepositorySupport.findAllByUserId(userId,pageable);
        return reviews;
    }

    @Override
    public Page<Review> getReceivedReviewList(Long consultantId, Pageable pageable) {
        Page<Review> reviews = reviewRepositorySupport.findAllByConsultantId(consultantId,pageable);
        return reviews;
    }
}
