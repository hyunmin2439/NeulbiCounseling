package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import com.ssafy.common.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserRegisterPostReq userRegisterInfo) {

        String nickname = userRegisterInfo.getNickname();
        String email = userRegisterInfo.getEmail();
        String password = passwordEncoder.encode(userRegisterInfo.getPassword());

        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .role("USER")
                .maskId(1)
                .maskBack(1)
                .socialId("0")
                .profileImg("default-profile-image.jpg")
                .build();
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String userEmail) {
        // 디비에 유저 정보 조회 (userEmail 를 통한 조회).
        User user = userRepositorySupport.findByEmail(userEmail).orElse(null);

        System.out.println(user); // 추가
        return user;
    }

    @Override
    public User getUserByNickname(String userNickname) {

        User user = userRepositorySupport.findByNickname(userNickname).orElse(null);
        return user;
    }

    @Override
    public User getUserById(Long userId) {
        User user = userRepositorySupport.findUserById(userId).orElse(null);
        return user;
    }

    @Override
    public boolean sendMail(String email) {
        // 아이디로 메일, 비밀번호 가져오기
        User user = this.getUserByEmail(email);

        System.out.println(user);

        // 메일 보내기
        if (user != null) {
            // 메세지 내용이 너무 심플함. 좀 더 제목이랑 내용 추가 시키면 좋을 듯
            SimpleMailMessage simpleMessage = new SimpleMailMessage();
            simpleMessage.setTo(user.getEmail());
            simpleMessage.setSubject(user.getNickname() + "님의 임시 비밀번호");
            String tmpPassword = PasswordUtil.getRandomPassword(); // 임시비밀번호
            String tmpJwtToken = passwordEncoder.encode(tmpPassword); // 임시비밀번호의 jwtToken
            // password update 구문
            user.resetPassword(tmpJwtToken);
            userRepository.save(user);
            simpleMessage.setText(user.getNickname() + "님의 임시비밀번호는 " + tmpPassword + " 입니다.");
            javaMailSender.send(simpleMessage);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        User user = getUserById(userId);

        userRepository.delete(user);
    }
}


