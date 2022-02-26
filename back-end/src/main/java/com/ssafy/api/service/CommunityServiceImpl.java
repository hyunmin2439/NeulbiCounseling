package com.ssafy.api.service;

import com.ssafy.api.request.CommunityDeleteReq;
import com.ssafy.api.request.CommunityRegisterPostReq;
import com.ssafy.api.request.CommunityUserReq;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.LikeCheck;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.CommunityRepository;
import com.ssafy.db.repository.CommunityRepositorySupport;
import com.ssafy.db.repository.LikeCheckRepository;
import com.ssafy.db.repository.LikeCheckRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Service("communityService")
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    LikeCheckRepository likeCheckRepository;

    @Autowired
    LikeCheckRepositorySupport likeCheckRepositorySupport;

    @Autowired
    CommunityRepositorySupport communityRepositorySupport;

    @Autowired
    UserService userService;

    @Override
    public Page<Community> getAllCommunity(Pageable pageable) {

        return communityRepositorySupport.findAllByDeletedIsFalse(pageable);
    }

    @Override
    public Page<Community> getAllSortedCommunity(Pageable pageable, String key) {

        if ("like".equals(key))
            return communityRepositorySupport.findAllByDeletedIsFalseAndSortedLike(pageable);
        else if ("view".equals(key))
            return communityRepositorySupport.findAllByDeletedIsFalseAndSortedView(pageable);

        return null;
    }

    @Override
    public Page<Community> searchCommunity(Pageable pageable, String key, String value) {

        if ("description".equals(key))
            return communityRepositorySupport.findAllByDeletedIsFalseAndDescriptionContains(pageable, value);
        else if ("title".equals(key))
            return communityRepositorySupport.findAllByDeletedIsFalseAndTitleContains(pageable, value);
        else if ("nickname".equals(key))
            return communityRepositorySupport.findAllByDeletedIsFalseAndUserNicknameContains(pageable, value);

        return null;
    }

    @Override
    public Community createCommunity(CommunityRegisterPostReq registerInfo) {

        User user = userService.getUserById(registerInfo.getUserId());

        Community community = Community.builder()
                .user(user)
                .title(registerInfo.getTitle())
                .description(registerInfo.getDescription())
                .likeCnt(0)
                .viewCnt(0)
                .isDeleted(false)
                .build();

        return communityRepository.save(community);
    }

    @Override
    public Community getCommunity(Long communityId) {
        Community community = communityRepositorySupport.findByIdAndCommentListFalse(communityId).orElse(null);

        // 정렬
        if (community.getCommentList() != null && !community.getCommentList().isEmpty())
            Collections.sort(community.getCommentList());

        // 한번 상세보기 할 때마다
        community.increaseViewCnt();
        communityRepository.save(community);

        return community;
    }

    @Override
    public boolean getLikeCheck(Long communityId, Long userId) {
        LikeCheck likeCheck = likeCheckRepositorySupport.findLikeCheckByUserIdAndCommunityId(communityId, userId).orElse(null);
        if (likeCheck != null) return true;
        else return false;
    }

    @Transactional
    @Override
    public void setLikeCheck(Long communityId, Long userId) {
        Community community = getCommunity(communityId);
        int cnt = community.getLikeCnt();

        LikeCheck likeCheck = likeCheckRepositorySupport.findLikeCheckByUserIdAndCommunityId(communityId, userId).orElse(null);
        if (likeCheck != null) {
            community.likeCntChange(cnt - 1);
            communityRepository.save(community);

            likeCheckRepository.delete(likeCheck);

        } else {
            User user = userService.getUserById(userId);
            community.likeCntChange(cnt + 1);
            communityRepository.save(community);

            likeCheck = LikeCheck.builder().community(community).user(user).build();
            likeCheckRepository.save(likeCheck);
        }
    }

    @Override
    public Community deleteCommunity(CommunityDeleteReq req) {

        Community community = communityRepositorySupport.findByIdAndCommentListFalse(req.getCommunityId()).orElse(null);


        Long userId = community.getUser().getId();
        Long reqId = req.getUserId();

        if (userId == reqId) {
            community.setDeleted(true);
            return communityRepository.save(community);

        } else {

            return null;
        }

    }

    @Override
    public Community modifyCommunity(CommunityUserReq req) {
        Community community = communityRepositorySupport.findByIdAndCommentListFalse(req.getCommunityId()).orElse(null);

        Long userId = community.getUser().getId();
        Long reqId = req.getUserId();

        if (userId == reqId) {
            community.modifyCommunity(req.getDescription(), req.getTitle());
            return communityRepository.save(community);

        } else {
            return null;
        }
    }
}
