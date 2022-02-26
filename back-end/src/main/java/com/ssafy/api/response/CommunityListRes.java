package com.ssafy.api.response;

import com.ssafy.db.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 상담가 목록 조회 API ([GET] /advice) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ConsultantListRes")
public class CommunityListRes {

	@ApiModelProperty(name="Community id")
	Long id;
	@ApiModelProperty(name="User nickname")
	String userNickname;
	@ApiModelProperty(name="Community title")
	String title;
	@ApiModelProperty(name="Community likeCnt")
	int likeCnt;
	@ApiModelProperty(name="Community viewCnt")
	int viewCnt;
	@ApiModelProperty(name="Community userProfileImg")
	String userProfileImg; // 프로필 이미지 주소
	@ApiModelProperty(name="Community userMaskImg")
	int maskId; // 마스크 번호
	@ApiModelProperty(name="Community createdDate")
	LocalDateTime createdDate;
	@ApiModelProperty(name="Community commentCnt")
	int commentCnt; // 댓글 수
	public static Page<CommunityListRes> of(Page<Community> coms) {
		List<CommunityListRes> temp = new ArrayList<>();

		Pageable pageable = coms.getPageable();
		long total = coms.getTotalElements();

		for (Community c: coms.getContent()) {
			CommunityListRes clr = new CommunityListRes();
			User user = c.getUser();

			int cnt = 0;
			for(Comment comment : c.getCommentList()) {
				if(!comment.isDeleted())
					cnt++;
			}
			clr.setUserNickname(user.getNickname());
			clr.setUserProfileImg(user.getProfileImg());
			clr.setMaskId(user.getMaskId());
			clr.setId(c.getId());
			clr.setTitle(c.getTitle());
			clr.setLikeCnt(c.getLikeCnt());
			clr.setViewCnt(c.getViewCnt());
			clr.setCreatedDate(c.getCreatedDate());
			clr.setCommentCnt(cnt);
			temp.add(clr);
		}

		Page<CommunityListRes> res = new PageImpl<CommunityListRes>(temp,pageable,total);

		return res;
	}


}