package com.ssafy.api.service;

import com.ssafy.api.request.ChatRoomJoinUserPostReq;
import com.ssafy.api.response.ChatRoomRes;
import com.ssafy.db.entity.ChatRoom;
import com.ssafy.db.entity.Message;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ChatRoomRepository;
import com.ssafy.db.repository.ChatRoomRepositorySupport;
import com.ssafy.db.repository.MessageRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service("chatRoomService")
public class ChatRoomServiceImpl implements ChatRoomService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProfileService profileService;

    @Autowired
    MessageService messageService;

    @Autowired
    ChatRoomRepositorySupport chatRoomRepositorySupport;

    public Message chattingHandler(Message message) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(message.toString());
        System.out.println(userRepository.findById(message.getUserId()));
        User user = userRepository.findById(message.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자입니다."));
        message.setUserId(user.getId());

        System.out.println("채팅방 아이디"+message.getChatRoomId());
        // 메시지가 생성될때마다 해당 채팅방 수정일 업데이트
        chatRoomRepositorySupport.updateChatRoomById(message.getChatRoomId());

        return messageRepository.save(message);
    }

    public ChatRoom createChatRoom(ChatRoomJoinUserPostReq joinUserInfo) {

        Long userId = joinUserInfo.getUserId();
        Long consultantId = joinUserInfo.getConsultantId();

        ChatRoom chatRoom = ChatRoom.builder().userId(userId).consultantId(consultantId).build();

        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom findChatRoomByUserIdAndConsultantId(Long userId, Long consultantId) {

        System.out.println("서비스에서 챗룸 생성 확인 하나?");
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByUserIdAndConsultantId(userId,consultantId).orElse(null);

        return chatRoom;
    }

    @Override
    public ChatRoom getChatRoomByChatRoomId(Long id) {

        ChatRoom chatRoom = chatRoomRepository.findChatRoomById(id);

        return chatRoom;
    }

    public List<ChatRoom> getChatRoomByUserId(Long userId) {
        List<ChatRoom> chatRooms = chatRoomRepositorySupport.findChatRoomByUserId(userId);

        return chatRooms;
    }

    @Override
    public List<Long> getUsersByChatRoomId(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepositorySupport.findChatRoomById(chatRoomId);

        List<Long> userList = new ArrayList<>();
        userList.add(chatRoom.getUserId());
        userList.add(chatRoom.getConsultantId());

        return userList;
    }

    @Override
    public List<ChatRoomRes> getChatRoomInfoByChatRooms(List<ChatRoom> chatRooms) {
        List<ChatRoomRes> chatRoomResList = new ArrayList<>();

        for(ChatRoom chatRoom : chatRooms) {
            ChatRoomRes res = new ChatRoomRes();

            Optional<Message> message = messageService.getLastMessageByChatRoomId(chatRoom.getId());
            String msg;
            LocalDateTime localDateTime;
            if(message.isPresent()) {
                msg = message.get().getMessage();
                localDateTime = message.get().getCreatedDate();
            }
            else {
                msg = "아직 메시지가 없습니다.";
                localDateTime = null;
            }

            // 채팅방 관련 정보
            res.setLastMessage(msg);
            res.setId(chatRoom.getId());
            res.setCreatedDate(localDateTime);

            // 유저
            User user = userService.getUserById(chatRoom.getUserId());
            res.setUserId(chatRoom.getUserId());
            res.setUserNickName(user.getNickname());
            res.setMaskId(user.getMaskId());

            // 컨설턴트
            User consultant = userService.getUserById(chatRoom.getConsultantId());
            res.setConsultantId(chatRoom.getConsultantId());
            res.setConsultantNickName(consultant.getNickname());
            res.setConsultantProfileImg(consultant.getProfileImg());

            res.setStatusCode(200);
            res.setMessage("Success");

            chatRoomResList.add(res);
        }
        return chatRoomResList;
    }

}