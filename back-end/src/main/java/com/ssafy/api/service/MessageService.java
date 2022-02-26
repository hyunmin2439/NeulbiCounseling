package com.ssafy.api.service;

import com.ssafy.db.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<Message> getByChatRoomId(Long chatRoomId);
    Optional<Message> getLastMessageByChatRoomId(Long chatRoomId);
}
