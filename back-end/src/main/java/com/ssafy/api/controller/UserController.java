package com.ssafy.api.controller;

import com.ssafy.api.request.UserCheckPostReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

import java.net.MalformedURLException;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    @ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = UserLoginPostRes.class),
            @ApiResponse(code = 401, message = "Invalid Password", response = UserLoginPostRes.class),
    })
    public ResponseEntity<UserLoginPostRes> login(@RequestBody @ApiParam(value = "로그인 정보", required = true) UserLoginPostReq loginInfo) {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();

        User user = userService.getUserByEmail(email);
        // 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
        if (passwordEncoder.matches(password, user.getPassword())) {
            // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
            return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(email)));
        }
        // 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
        return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Invalid Password", null));
    }

    @PostMapping("/signup")
    @ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = BaseResponseBody.class),
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value = "회원가입 정보", required = true) UserRegisterPostReq registerInfo) {

        //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
        User user = userService.createUser(registerInfo);

        System.out.println(user);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/check")
    @ApiOperation(value = "이메일 또는 닉네임 중복 확인", notes = "<strong>이메일 또는 닉네임</strong>이 이미 존재하는지 확인한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Duplicated Email", response = BaseResponseBody.class),
            @ApiResponse(code = 200, message = "Success Email", response = BaseResponseBody.class),
            @ApiResponse(code = 200, message = "Duplicated Nickname", response = BaseResponseBody.class),
            @ApiResponse(code = 200, message = "Success Nickname", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> check(
            @RequestBody @ApiParam(value = "닉네임 중복확인 체크", required = true) UserCheckPostReq checkInfo) {

//		키와 밸류값
        String key = checkInfo.getKey();

        System.out.println(key);
        String value = checkInfo.getValue();
        System.out.println(value);
        User user = null;
        //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
        if (key.equals("email")) {
            user = userService.getUserByEmail(value);
            if(user != null) return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Duplicated Email"));
            else return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success Email"));
        } else if (key.equals("nickname")) {
            user = userService.getUserByNickname(value);
            if (user != null) return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Duplicated Nickname"));
            else return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success Nickname"));
        }

        return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
    }

    @GetMapping("/info")
    @ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserRes.class)
    })
    public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) throws MalformedURLException {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();

        String userId = userDetails.getUsername();

        User user = userService.getUserByEmail(userId);

        return ResponseEntity.status(200).body(UserRes.of(user));
    }

    @PostMapping("/resetPassword")
    @ApiOperation(value = "이메일 입력 받음", notes = "회원의 이메일로 임시 비밀번호 전송")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "FAIL", response = BaseResponseBody.class)
    })
    public ResponseEntity<BaseResponseBody> resetPassword(@RequestBody @ApiParam(value = "이메일 정보", required = true) UserLoginPostReq email) {
        String tempEmail = email.getEmail();

        System.out.println(tempEmail);
        if (userService.sendMail(tempEmail)) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
        } else {
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "FAIL"));
        }
    }
}
