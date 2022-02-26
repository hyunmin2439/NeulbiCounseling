package com.ssafy.api.response;

import com.ssafy.db.entity.Qna;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("QnaListResponse")
public class QnaListRes {

    public static Page<QnaRes> of(Page<Qna> qnaList, Long userId) {
        List<QnaRes> list = new ArrayList<>();

        Pageable pageable = qnaList.getPageable();
        long total = qnaList.getTotalElements();

        qnaList.forEach( qna -> {
            // 지워진 QN&A가 아니면
            if( !qna.isDeleted() ) {
                // 잠겨있는 Q&A글인데 자신이 쓴 글이면 잠금 풀고 내리기
                if(qna.isRocked() && qna.getUser().getId().equals(userId)) qna.setRocked(false);
                list.add(QnaRes.OfList(qna));
            }
        });

        return new PageImpl<QnaRes>(list, pageable, total);
    }

}
