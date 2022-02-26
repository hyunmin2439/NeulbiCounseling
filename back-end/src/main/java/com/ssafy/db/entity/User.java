package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.ssafy.db.converter.RoleAttributeConverter;
import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Convert;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
// @DynamicInsert // insert 시 null 인필드 제외
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User extends BaseEntity{
    String nickname; // 별명
    String socialId; // 소셜 로그인 유저 구분자
    int maskId; // 마스크 번호
    int maskBack; // 마스크 배경
    String email; // 이메일 == 아이디
    String profileImg; // 프로필 이미지 주소
    boolean isConsultant; // 상담가 신청 여부
    boolean isPenalty; // 현재 패널티 여부
    double pointTot; // 등급 포인트
    int reportCnt; // 신고 받은 횟수

    @Column(name="role_id")
    @Convert(converter = RoleAttributeConverter.class)
    String role;

    @JsonManagedReference
    @OneToMany(mappedBy = "user") //참조를 당하는 쪽에서 읽기만 가능!
    @Builder.Default
    private List<Comment> commentList = new LinkedList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user") //참조를 당하는 쪽에서 읽기만 가능!
    @Builder.Default
    private List<Community> communityList = new LinkedList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user") //참조를 당하는 쪽에서 읽기만 가능!
    @Builder.Default
    private List<LikeCheck> LikeCheckList = new LinkedList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user") //참조를 당하는 쪽에서 읽기만 가능!
    @Builder.Default
    private List<Meeting> meetingList = new LinkedList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user") //참조를 당하는 쪽에서 읽기만 가능!
    @Builder.Default
    private List<Review> reviewList = new LinkedList<>();


    @JsonManagedReference
    @OneToMany(mappedBy = "user") //참조를 당하는 쪽에서 읽기만 가능!
    @Builder.Default
    private List<MeetingHistory> meetingHistoryList = new LinkedList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user") //참조를 당하는 쪽에서 읽기만 가능!
    @Builder.Default
    private List<UserMeeting> userMeetingList = new LinkedList<>();

    // Jackson 라이브러리 Annotation
    @JsonIgnoreProperties// 직렬화 시 제외 필드
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // 쓰기 전용
            String password;

    // 패스워드 랜덤 비밀번호로 초기화
    public void resetPassword(String password) {
        this.password = password;
    }

    // 컨설턴트로 등록
    public void registerConsultant(boolean isConsultant){
        this.isConsultant = isConsultant;
    }

    // 닉네임 변경
    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }

    // 신고 당한 횟수 증가
    public void increaseReportCnt() { this.reportCnt++; }

    // 포인트 더하기
    public void addPoint(double pointTot) {
        this.pointTot += pointTot;
    }

    // 접근 권한
    public String getRoleKey() { return "ROLE_" + this.role; }

    // --- 수정한 부분
    // 소셜 로그인, 소셜 아이디 바꾸기
    public User modifySocialId(String socialId) {
        this.socialId = socialId;
        return this;
    }

    // 프로필 이미지 변경
    public void modifyProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public void modifyMask(int maskId) {
        this.maskId = maskId;
    }

    public void modifyMaskBack(int maskBack) {
        this.maskBack = maskBack;
    }
}
