package com.ssafy.api.response;

import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Getter
@Setter
@ApiModel("CommentListRes")
public class CommentListRes {

    Long commentId;
    Long userId;
    Long communityId;
    String description;
    int groupNum;
    int layer;
    String userNickname;
    double userPointTot;
    String communityTitle;
    @ApiModelProperty(name = "User mask_id")
    int maskId;
    @ApiModelProperty(name = "User profile_img")
    String profileImg;


    public static List<CommentListRes> of(List<Comment> commentList) {
        List<CommentListRes> res = new LinkedList<>();

        for (Comment c : commentList) {
            if(c.isDeleted())continue;
            CommentListRes cr = new CommentListRes();
            User user = c.getUser();

            cr.setProfileImg(user.getProfileImg());
            cr.setMaskId(user.getMaskId());
            cr.setCommentId(c.getId());
            cr.setUserId(c.getUser().getId());
            cr.setDescription(c.getDescription());
            cr.setGroupNum(c.getGroupNum());
            cr.setLayer(c.getLayer());
            cr.setUserNickname(user.getNickname());
            cr.setUserPointTot(user.getPointTot());
            cr.setCommunityId(c.getCommunity().getId());
            cr.setCommunityTitle(c.getCommunity().getTitle());
            res.add(cr);
        }

        return res;
    }

    public static Page<CommentListRes> of(Page<Comment> commentList) {
        List<CommentListRes> temp = new ArrayList<>();

        Pageable pageable = commentList.getPageable();
        long total = commentList.getTotalElements();

        for (Comment c : commentList) {

            if(c.isDeleted())continue;

            CommentListRes cr = new CommentListRes();

            User user = c.getUser();
            cr.setCommentId(c.getId());
            cr.setUserId(c.getUser().getId());
            cr.setDescription(c.getDescription());
            cr.setGroupNum(c.getGroupNum());
            cr.setLayer(c.getLayer());
            cr.setUserNickname(user.getNickname());
            cr.setUserPointTot(user.getPointTot());
            cr.setCommunityId(c.getCommunity().getId());
            cr.setCommunityTitle(c.getCommunity().getTitle());
            temp.add(cr);
        }
        Page<CommentListRes> res = new PageImpl<>(temp,pageable,total);

        return res;
    }

}
