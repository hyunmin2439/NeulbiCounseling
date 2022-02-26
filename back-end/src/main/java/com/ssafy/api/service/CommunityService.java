package com.ssafy.api.service;

import com.ssafy.api.request.CommunityDeleteReq;
import com.ssafy.api.request.CommunityRegisterPostReq;
import com.ssafy.api.request.CommunityUserReq;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.LikeCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityService {

    Page<Community> getAllCommunity(Pageable pageable);
    Page<Community> getAllSortedCommunity(Pageable pageable, String key);
    Page<Community> searchCommunity(Pageable pageable, String key, String value);

    Community createCommunity(CommunityRegisterPostReq registerInfo);
    Community getCommunity(Long communityId);
    Community deleteCommunity(CommunityDeleteReq req);
    Community modifyCommunity(CommunityUserReq req);
    boolean getLikeCheck(Long communityId, Long userId);
    void setLikeCheck(Long communityId, Long userId);
}
