package com.ssafy.api.response;

import com.ssafy.api.service.ProfileService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.ChatRoom;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ChatRoomRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("ChatRoomResponse")
public class ChatRoomRes extends BaseResponseBody {

    @Autowired
    UserService userService;

    Long id;
    // 유저 아이디 따로 받아옴
    Long userId;
    // 유저 닉네임
    String userNickName;

    //마스크 id
    int maskId;

    // 해당 컨설턴트 아이디 따로 받아옴
    Long consultantId;
    // 해당 컨설턴트의 닉네임
    String consultantNickName;
    // 해당 컨설턴트 프로필 사진
    String consultantProfileImg;

    // 해당 컨설턴트와의 가장 최근 메시지
    String lastMessage;

    LocalDateTime createdDate;
//    List<ChatRoom> chatRooms;


    public List<ChatRoomRes> of(Integer stausCode, String message, List<ChatRoom> chatRoomList) {

        List<ChatRoomRes> chatRoomResList = new ArrayList<>();

        for(ChatRoom chatRoom : chatRoomList) {
            ChatRoomRes res = new ChatRoomRes();
            res.setUserId(chatRoom.getUserId());
            res.setConsultantId(chatRoom.getConsultantId());
            User user = userService.getUserById(chatRoom.getUserId());
            res.setConsultantNickName(user.getNickname());
            res.setConsultantProfileImg(user.getProfileImg());
            res.setStatusCode(stausCode);
            res.setMessage(message);

            chatRoomResList.add(res);
        }

//        res.setChatRooms(chatRoomList);
        return chatRoomResList;
    }


}
