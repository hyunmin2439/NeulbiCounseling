package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Message;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
@ApiModel("MessageResponse")
public class MessageRes extends BaseResponseBody {
    private List<Message> chatMessage;
    private Long chatRoomId;
    private Long userId;
    private List<Long> userList;
    private boolean isActive;

    public static MessageRes of(Integer statusCode, String resMessage, List<Message> message, List<Long> userList, boolean isActive) {

        MessageRes res = new MessageRes();
        res.setStatusCode(statusCode);
        res.setMessage(resMessage);
        res.setChatMessage(message);
        res.setUserList(userList);
        res.isActive = isActive;
//        for (Message msg: message) {
//            res.setMessage(msg.getMessage());
//            res.setChatRoomId(msg.getChatRoomId());
//            res.setUserId(msg.getUserId());
//        }

        return res;
    }
}
