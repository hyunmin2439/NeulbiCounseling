package com.ssafy.api.controller;

import com.ssafy.api.request.ChatRoomJoinUserPostReq;
import com.ssafy.api.response.ChatRoomRes;
import com.ssafy.api.response.MessageRes;
import com.ssafy.api.service.ChatRoomService;
import com.ssafy.api.service.ConsultantService;
import com.ssafy.api.service.MeetingService;
import com.ssafy.api.service.MessageService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.ChatRoom;
import com.ssafy.db.entity.ConsultantProfile;
import com.ssafy.db.entity.Message;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Controller
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ConsultantService consultantService;

    @Autowired
    private MeetingService meetingService;

    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/receive")
    // /send로 메시지를 반환합니다.
    @SendTo("/send")
    public Message chattingHandler(Message message) {

        return chatRoomService.chattingHandler(message);
    }

    @PostMapping("/chatroom/create")
    @ApiOperation(value = "채팅방 생성", notes = "<strong>유저아이디와 컨설턴트 아이디</strong>를 통해 채팅방을 생성 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "strRoomId", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> createChatRooom(@RequestBody ChatRoomJoinUserPostReq joiner) {

        Long userId = joiner.getUserId();
        Long consultantId = joiner.getConsultantId();

        ChatRoom chatRoom = chatRoomService.findChatRoomByUserIdAndConsultantId(userId,consultantId);

        if (chatRoom == null) {
            chatRoomService.createChatRoom(joiner);
            ChatRoom newChatRoom = chatRoomService.findChatRoomByUserIdAndConsultantId(userId,consultantId);
            Long chatRoomId = newChatRoom.getId();
            String strRoomId = chatRoomId.toString();
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, strRoomId));
        }

        else {
            // 해당 채팅방 정보 받아오기 위에서
            // 완료
            Long chatRoomId = chatRoom.getId();
            String strRoomId = chatRoomId.toString();
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, strRoomId));


            // 해당 채팅방의 메시지 정보 받아오기
            //List<Message> message = messageService.getByChatRoomId(chatRoom.getId());

            //return ResponseEntity.status(200).body(MessageRes.of(200,"Success",message));
            //return ResponseEntity.ok().body(MessageRes.of(message));
            //return (ResponseEntity<? extends BaseResponseBody>) ResponseEntity.ok(message);
        }
    }

    @GetMapping("/chatroom/{user_id}")
    @ApiOperation(value = "내 채팅방 정보", notes = "<strong>내 채팅방 정보</strong>불러오기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ChatRoomRes.class)
    })
    public ResponseEntity<List<ChatRoomRes>> getChatRoom(@PathVariable("user_id") Long userId) {

        // 유저 아이디를 통해 해당 유저의 채팅방 정보를 얻어온다.
        List<ChatRoom> chatRooms = chatRoomService.getChatRoomByUserId(userId);
        List<ChatRoomRes> chatRoomResList = chatRoomService.getChatRoomInfoByChatRooms(chatRooms);
        return ResponseEntity.status(200).body(chatRoomResList);
    }

    @GetMapping("/chatroom/{user_id}/{chatRoom_id}")
    @ApiOperation(value = "해당 컨설턴트와의 채팅방 정보", notes = "<strong>해당 컨설턴트와의 채팅방 정보</strong>불러오기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = MessageRes.class)
    })
    public ResponseEntity<? extends BaseResponseBody> getChatRoomDetail(@PathVariable("user_id") Long userId
                                                                        ,@PathVariable("chatRoom_id") Long chatRoomId) {

        System.out.println(userId);
        // 메시지 테이블에서 채팅방 아이디에 해당하는 메시지들을 받아온다.
        List<Message> messages = messageService.getByChatRoomId(chatRoomId);
        // 0번째가 유저아이디, 1번째가 컨설턴트아이디
        List<Long> users = chatRoomService.getUsersByChatRoomId(chatRoomId);

        boolean isActive = meetingService.getMeetingRoomIsActiveByChatRoomId(chatRoomId);
        // users의 2번째에 토픽 카테고리 아이디 추가
        Optional<ConsultantProfile> consultantProfile = consultantService.getConsultantByUserId(users.get(1));
        users.add(consultantProfile.get().getTopicCategory().getId());

        return ResponseEntity.status(200).body(MessageRes.of(200,"Success",messages,users, isActive));
    }
}


//    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
//    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.
//    public ChatRoom SocketHandler(ChatRoom chatRoom) {
//        // vo에서 getter로 userName을 가져옵니다.
//        String userName = chatRoom.getUserName();
//        // vo에서 setter로 content를 가져옵니다.
//        String content = chatRoom.getContent();
//
//        // 생성자로 반환값을 생성합니다.
//        ChatRoom result = new ChatRoom(userName, content);
//        // 반환
//        return result;
//    }
//}
