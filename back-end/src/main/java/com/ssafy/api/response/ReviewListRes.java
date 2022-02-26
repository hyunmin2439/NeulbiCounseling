package com.ssafy.api.response;

import com.ssafy.db.entity.MeetingHistory;
import com.ssafy.db.entity.Review;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("ReviewResponse")
public class ReviewListRes {

    Review review;
    // 받은 리뷰에서 누가 나한테 썻는지에 사용되는 닉네임(상담자 닉네임)
    String userNickname;
    // 내가 쓴 리뷰에서 누구한테 썻는지에 사용되는 닉네임(상담가 닉네임)
    String consultantNickname;

    public static Page<ReviewListRes> of (Page<Review> reviews) {
        List<ReviewListRes> temp = new ArrayList<>();

        Pageable pageable = reviews.getPageable();
        long total = reviews.getTotalElements();

        for(Review review : reviews) {
            ReviewListRes res = new ReviewListRes();
            res.setReview(review);
            res.setUserNickname(review.getUser().getNickname());
            res.setConsultantNickname(review.getConsultantProfile().getUser().getNickname());
            temp.add(res);
        }
        Page<ReviewListRes> res = new PageImpl<>(temp, pageable, total);
        return res;

    }
}
