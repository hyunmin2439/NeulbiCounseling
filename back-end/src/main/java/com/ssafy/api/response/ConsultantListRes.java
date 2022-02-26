package com.ssafy.api.response;

import com.ssafy.db.entity.ConsultantProfile;
import com.ssafy.db.entity.TopicCategory;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.MyConsultantRepositorySupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 상담가 목록 조회 API ([GET] /advice) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ConsultantListRes")
public class ConsultantListRes {

	@Autowired
	static
	MyConsultantRepositorySupport myConsultantRepositorySupport;

	@ApiModelProperty(name="User id")
	Long id;
	@ApiModelProperty(name="User nickname")
	String nickname;
	@ApiModelProperty(name="User maskId")
	int maskId;
	@ApiModelProperty(name="User maskBack")
	int backId;
	@ApiModelProperty(name="User ProfileImg")
	String profileImg;
	@ApiModelProperty(name="User pointTot")
	double pointTot;
	@ApiModelProperty(name="ConsultantProfile topicCategoryName")
	String topicCategoryName;
	@ApiModelProperty(name="ConsultantProfile description")
	String description;
	@ApiModelProperty(name="ConsultantProfile consultingCnt")
	int consultingCnt;
	@ApiModelProperty(name="ConsultantProfile myFavoriteConsultant")
	boolean isFavConsultant;

	// 여기 받은 아이디값 넣어줘서
	// cons안에 있는 user.getId()와 받은 아이디값 비교해서
	// 그게 존재하는지 체크
	// myConsultantRepositorySupport.findMyConsultantByUserIdAndConsultantId();
	// 존재하면 True, 없으면 False 값 같이 res에 담아서 반환.

	public static Page<ConsultantListRes> of(Page<ConsultantProfile> cons) {
		List<ConsultantListRes> temp = new ArrayList<>();

		Pageable pageable = cons.getPageable();
		long total = cons.getTotalElements();

		for (ConsultantProfile c: cons.getContent()) {
			ConsultantListRes r = new ConsultantListRes();
			User user = c.getUser();
			TopicCategory topicCategory = c.getTopicCategory();

			r.setId(user.getId());
			r.setNickname(user.getNickname());
			r.setMaskId(user.getMaskId());
			r.setBackId(user.getMaskBack());
			r.setProfileImg(user.getProfileImg());
			r.setPointTot(user.getPointTot());
			r.setTopicCategoryName(topicCategory.getName());
			r.setDescription(c.getDescription());
			r.setConsultingCnt(c.getConsultingCnt());

			// 해당 컨설턴트가 목록에 없으면 False, 있으면 True값 반환
			//MyConsultant myConsultant = myConsultantRepositorySupport.findMyConsultantByUserIdAndConsultantId(userId,user.getId()).orElse(null);
//			System.out.println("-=============================================");
//			System.out.println(myConsultant);
//
//			boolean isMyFavConsultant;
//			if(myConsultant == null) isMyFavConsultant = false;
//			else isMyFavConsultant = true;
//			r.isFavConsultant= isMyFavConsultant;
			temp.add(r);
		}

		Page<ConsultantListRes> res = new PageImpl<ConsultantListRes>(temp,pageable,total);

		return res;
	}

	public static List<ConsultantListRes> of(List<ConsultantProfile> consultantProfileList) {
		List<ConsultantListRes> res = new LinkedList<>();

		for (ConsultantProfile c : consultantProfileList) {

			ConsultantListRes r = new ConsultantListRes();

			User user = c.getUser();
			TopicCategory topicCategory = c.getTopicCategory();

			r.setId(user.getId());
			r.setNickname(user.getNickname());
			r.setMaskId(user.getMaskId());
			r.setBackId(user.getMaskBack());
			r.setProfileImg(user.getProfileImg());
			r.setPointTot(user.getPointTot());
			r.setTopicCategoryName(topicCategory.getName());
			r.setDescription(c.getDescription());
			r.setConsultingCnt(c.getConsultingCnt());
			res.add(r);
		}

		return res;
	}


}