package com.ssafy.api.service;

import com.ssafy.api.request.ConsultantRegisterPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.ConsultantListRes;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 상담가 프로필 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("consultantService")
public class ConsultantServiceImpl implements ConsultantService {
    @Autowired
    ConsultantRepository consultantRepository;

    @Autowired
    ConsultantRepositorySupport consultantRepositorySupport;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    TopicCategoryRepository topicCategoryRepository;

    @Autowired
    TopicCategoryRepositorySupport topicCategoryRepositorySupport;

    @Autowired
    UserService userService;

    @Autowired
    MyConsultantRepositorySupport myConsultantRepositorySupport;

    @Override
    public ConsultantProfile createConsultantProfile(ConsultantRegisterPostReq consultantInfo) {
        User user = userService.getUserById(consultantInfo.getUserId());
        user.registerConsultant(true);

        TopicCategory topicCategory = topicCategoryRepositorySupport.findByTopicCategoryId(consultantInfo.getTopicCategoryId()).orElse(null);

        ConsultantProfile consultantProfile = ConsultantProfile.builder()
                .consultingCnt(0)
                .description(consultantInfo.getDescription())
                .user(user)
                .topicCategory(topicCategory)
                .build();

        userRepository.save(user);

        return consultantRepository.save(consultantProfile);
    }

    @Override
    public ConsultantProfile getConsultant(Long userId) {
        ConsultantProfile cons = consultantRepositorySupport.findByUserId(userId).orElse(null);
        return cons;
    }

    @Override
    public Page<ConsultantProfile> getAllConsultantByUserIdNotEqual(Pageable pageable, Long userId) {
        Page<ConsultantProfile> cons = consultantRepositorySupport.findAllByUserIdNotEqual(pageable, userId);
        return cons;
    }

    @Override
    public Page<ConsultantProfile> getConsultantByValue(String key, String value, Pageable pageable, Long userId) {
        Page<ConsultantProfile> cons = null;
        if (key.equals("nickname")) {
            cons = consultantRepositorySupport.findConsultantProfileByUserNicknameContains(value, userId, pageable);
        }else if(key.equals("description")){
            cons = consultantRepositorySupport.findConsultantProfileByDescriptionContains(value, userId, pageable);
        }
        return cons;
    }

    @Override
    public Page<ConsultantProfile> getUserByTopicCategory(Long topicCategoryId, Pageable pageable, Long userId) {
        Page<ConsultantProfile> cons = consultantRepositorySupport.findAllByTopicCategoryId(topicCategoryId, pageable, userId);
        return cons;
    }

    @Override
    public Optional<ConsultantProfile> getConsultantByUserId(Long userId) {
        return consultantRepository.findConsultantProfileByUserId(userId);
    }

    @Override
    public List<ConsultantProfile> getUserByRank( ) {
        List<User> users = userRepositorySupport.findFirst10ByOrderByPointTotDesc();

        List<ConsultantProfile> consultantProfileList = new ArrayList<>();

        for (User u : users) {
            ConsultantProfile consultantProfile = consultantRepositorySupport.findByUserId(u.getId()).orElse(null);
            consultantProfileList.add(consultantProfile);
        }
        return consultantProfileList;
    }

    @Override
    public void modifyConsultantDescription(Long userId, String description) {
        Optional<ConsultantProfile> consultantProfile = getConsultantByUserId(userId);

        consultantProfile.ifPresent(con -> {
            con.modifyDescription(description);
            consultantRepository.save(con);
        });
    }

    @Override
    public void modifyConsultantTopicCategory(Long id, Long topicCategoryId) {
        ConsultantProfile consultantProfile = getConsultantByUserId(id).orElse(null);

        TopicCategory topicCategory = topicCategoryRepositorySupport.findByTopicCategoryId(topicCategoryId).orElse(null);
        consultantProfile.modifyTopicCategory(topicCategory);
        consultantRepository.save(consultantProfile);

    }

    @Override
    public Page<ConsultantListRes> getInfoMyFavoriteConsultant(Page<ConsultantProfile> consultantProfiles, Long userId) {
        List<ConsultantListRes> temp = new ArrayList<>();

        Pageable pageable = consultantProfiles.getPageable();
        long total = consultantProfiles.getTotalElements();

        for (ConsultantProfile c: consultantProfiles.getContent()) {
            ConsultantListRes r = new ConsultantListRes();
            User user = c.getUser();
            TopicCategory topicCategory = c.getTopicCategory();

            r.setId(user.getId());
            r.setProfileImg(user.getProfileImg());
            r.setPointTot(user.getPointTot());
            r.setNickname(user.getNickname());
            r.setMaskId(user.getMaskId());
            r.setBackId(user.getMaskBack());
            r.setTopicCategoryName(topicCategory.getName());
            r.setDescription(c.getDescription());
            r.setConsultingCnt(c.getConsultingCnt());

            // 해당 컨설턴트가 목록에 없으면 False, 있으면 True값 반환
            MyConsultant myConsultant = myConsultantRepositorySupport.findMyConsultantByUserIdAndConsultantId(userId,user.getId()).orElse(null);

            boolean isMyFavConsultant;
            if(myConsultant == null) isMyFavConsultant = false;
            else isMyFavConsultant = true;
            r.setFavConsultant(isMyFavConsultant);
            temp.add(r);
        }



        Page<ConsultantListRes> res = new PageImpl<ConsultantListRes>(temp,pageable,total);

        return res;
    }

}
