package com.ssafy.api.response;

import com.ssafy.common.util.ProjectDirectoryPathUtil;
import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.net.MalformedURLException;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes{
	@ApiModelProperty(name="User email")
	String email;
	@ApiModelProperty(name="User nickname")
	String nickname;
	@ApiModelProperty(name="User ProfileImg")
	String profileImg;
	@ApiModelProperty(name="User pointTot")
	double pointTot;
	@ApiModelProperty(name="User ReportCnt")
	int reportCnt;
	@ApiModelProperty(name="User userID 숫자 번호임")
	long id;

	@ApiModelProperty(name="User 현재 마스크")
	int maskId;

	@ApiModelProperty(name="User 현재 마스크 배경")
	int backId;

	@ApiModelProperty(name="접근 권한")
	String role;

	@ApiModelProperty(name="User isPenalty")
	boolean isPenalty;
	@ApiModelProperty(name="User isConsultant")
	boolean isConsultant;

	public static UserRes of(User user) throws MalformedURLException {
		UserRes res = new UserRes();
		res.setId(user.getId());
		res.setEmail(user.getEmail());
		res.setNickname(user.getNickname());
		res.setProfileImg(user.getProfileImg());
		res.setPointTot(user.getPointTot());
		res.setReportCnt(user.getReportCnt());
		res.setPenalty(user.isPenalty());
		res.setConsultant(user.isConsultant());
		res.setMaskId(user.getMaskId());
		res.setBackId(user.getMaskBack());
		res.setRole(user.getRole());

		return res;
	}
}