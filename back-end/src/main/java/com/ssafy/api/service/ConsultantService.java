package com.ssafy.api.service;

import com.ssafy.api.request.ConsultantRegisterPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.ConsultantListRes;
import com.ssafy.db.entity.ConsultantProfile;
import com.ssafy.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 *	상담가 프로필 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface ConsultantService {
	ConsultantProfile createConsultantProfile(ConsultantRegisterPostReq consultantInfo);
	ConsultantProfile getConsultant(Long userId);
	Page<ConsultantProfile> getAllConsultantByUserIdNotEqual(Pageable pageable, Long userId);
	Page<ConsultantProfile> getConsultantByValue(String key, String value, Pageable pageable, Long userId);
	Page<ConsultantProfile> getUserByTopicCategory(Long topicCategoryId, Pageable pageable, Long userId);
	Optional<ConsultantProfile> getConsultantByUserId(Long userId);
	Page<ConsultantListRes> getInfoMyFavoriteConsultant(Page<ConsultantProfile> consultantProfiles, Long userId);
	List<ConsultantProfile> getUserByRank();
	void modifyConsultantDescription(Long userId, String description);

	void modifyConsultantTopicCategory(Long id, Long topicCategoryId);
}
