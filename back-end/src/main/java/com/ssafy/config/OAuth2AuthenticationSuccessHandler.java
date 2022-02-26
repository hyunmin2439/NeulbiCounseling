package com.ssafy.config;

import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // socialId
        String userSocialId = authentication.getName();

        User user = userRepository.findUserBySocialId(userSocialId).orElse(null);

        // 유저가 존재하면
        if(user != null) {
            // google에서 사용하는 userId를 비밀번호로
            user.resetPassword(new BCryptPasswordEncoder().encode(userSocialId));
            userRepository.save(user);
        }

        String targetUrl = determineTargetUrl(request, response, authentication);

        String url = makeRedirectUrl(JwtTokenUtil.getToken(user.getEmail()));

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String makeRedirectUrl(String token) {
        return UriComponentsBuilder.fromUriString("https://i6e202.p.ssafy.io:443/oauth/success")
                .queryParam("accessToken", token)
                .build().toUriString();
    }
}
