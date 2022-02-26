package com.ssafy.api.service;

import com.ssafy.api.request.ProfileModifyPasswordPutReq;
import com.ssafy.common.util.ProjectDirectoryPathUtil;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.PageImpl;

/**
 * 유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("profileService")
public class ProfileServiceImpl implements ProfileService {
	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ConsultantRepository consultantRepository;

	@Autowired
	ConsultantRepositorySupport consultantRepositorySupport;

	@Autowired
	ProfileRepositorySupport profileRepositorySupport;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	MyConsultantRepository myConsultantRepository;

	@Autowired
	MyConsultantRepositorySupport myConsultantRepositorySupport;

	@Autowired
	CommunityRepositorySupport communityRepositorySupport;

	@Autowired
	CommentRepositorySupport commentRepositorySupport;

	@Autowired
	S3FileUploadService s3FileUploadService;

	@Override
	public Integer getUserByNickname(String nickname) {

		// User profile = profileRepositorySupport.findByNickname(nickname);
		int cnt = profileRepositorySupport.findByNickname(nickname);
		return cnt;
	}

	@Override
	public Optional<User> findByUserId(Long UserId) {
		Optional<User> user = profileRepositorySupport.findById(UserId);
		return user;
	}

	@Override
	public int modifyProfileImg(Long userId, MultipartFile profileImgInfo) throws IOException {
		User user = userRepository.findUserById(userId).orElse(null);

		// 사용자 없음
		if (user == null)
			return 500;

		// 사용자 이미지가 존재하면
		if (user.getProfileImg() != null && !user.getProfileImg().equals("")) {
			if (!user.getProfileImg().equals("default-profile-image.jpg")) {
				s3FileUploadService.deleteFile(user.getProfileImg());
			}
		}

		String savingFileName = s3FileUploadService.upload(profileImgInfo);

		// 유저 이미지 변경 저장
		user.modifyProfileImg(savingFileName);
		userRepository.save(user);

		return 200;
	}

	@Override
	public void modifyPasswordByUserId(ProfileModifyPasswordPutReq profileModifyPassword, Long id) {
		Optional<User> userInfo = this.findByUserId(id);

		String modifyPassword = passwordEncoder.encode(profileModifyPassword.getPassword());

		userInfo.ifPresent(user1 -> {
			user1.resetPassword(modifyPassword);
			user1.setModifiedDate(LocalDateTime.now());
			userRepository.save(user1);
		});
	}

	@Override
	public Optional<ConsultantProfile> getConsultantProfile(Long userId) {

		Optional<ConsultantProfile> con = consultantRepositorySupport.findByUserId(userId);

		return con;
	}

	@Override
	public Page<ConsultantProfile> getMyConsultantList(Long userId, Pageable pageable) {

		Page<MyConsultant> myConsultantList = myConsultantRepositorySupport.findMyConsultantListByUserId(userId,
				pageable);

		List<ConsultantProfile> temp = new ArrayList<>();

		for (MyConsultant myConsultant : myConsultantList.getContent()) {
			temp.add(consultantRepositorySupport.findByUserIdOne(myConsultant.getConsultant().getId()));
		}

		return new PageImpl<ConsultantProfile>(temp, myConsultantList.getPageable(),
				myConsultantList.getTotalElements());
	}

	@Override
	@Transactional
	public void createMyConsultant(Long userId, Long consultantId) {
		User user = userRepository.findUserById(userId).get();
		User consultant = userRepository.findUserById(consultantId).get();

		boolean hasConsultant;
		// =
		// myConsultantRepositorySupport.findMyConsultantByUserIdAndConsultantId(userId,consultantId);;
		MyConsultant myConsultantCheck = myConsultantRepositorySupport
				.findMyConsultantByUserIdAndConsultantId(userId, consultantId).orElse(null);
		if (myConsultantCheck == null)
			hasConsultant = false;
		else
			hasConsultant = true;
		// 해당 컨설턴트가 목록에 없음 hasConsultant 가 false 이면 추가
		if (!hasConsultant) {
			MyConsultant myConsultant = MyConsultant.builder()
					.user(user)
					.consultant(consultant)
					.build();
			myConsultantRepository.save(myConsultant);
			// 생성하고 저장에 성공했다 true
		}
		// hasConsultant가 true이면 삭제.
		else {
			myConsultantRepositorySupport.deleteMyConsultantByUserIdAndConsultantId(userId, consultantId);
		}
	}

	@Override
	public void deleteMyConsultant(Long userId, Long consultantId) {
		myConsultantRepositorySupport.deleteMyConsultantByUserIdAndConsultantId(userId, consultantId);
	}

	@Override
	public void modifyUserNickname(Long userId, String nickname) {
		Optional<User> user = findByUserId(userId);

		user.ifPresent(user1 -> {
			user1.modifyNickname(nickname);
			userRepository.save(user1);
		});
	}

	@Override
	public void modifyMask(Long userId, int maskId) {
		Optional<User> user = findByUserId(userId);

		user.ifPresent(user1 -> {
			user1.modifyMask(maskId == 0 ? user1.getMaskId() : maskId);
			userRepository.save(user1);
		});
	}

	@Override
	public void modifyMaskBack(Long userId, int maskBackId) {
		Optional<User> user = findByUserId(userId);

		user.ifPresent(user1 -> {
			user1.modifyMaskBack(maskBackId == 0 ? user1.getMaskBack() : maskBackId);
			userRepository.save(user1);
		});
	}

	@Override
	public Page<Community> getCommunityList(Pageable pageable, Long userId) {
		Page<Community> communityList = communityRepositorySupport.findAllByUserId(pageable, userId);

		return communityList;
	}

	@Override
	public Page<Comment> getCommentList(Pageable pageable, Long userId) {
		Page<Comment> comments = commentRepositorySupport.findAllByUserId(pageable, userId);

		return comments;
	}
}
