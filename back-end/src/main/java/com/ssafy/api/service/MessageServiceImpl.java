package com.ssafy.api.service;

import com.ssafy.db.entity.Message;
import com.ssafy.db.repository.MessageRepository;
import com.ssafy.db.repository.MessageRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("messageService")
public class MessageServiceImpl implements MessageService{

    @Autowired
    MessageRepositorySupport messageRepositorySupport;

    @Autowired
    MessageRepository messageRepository;

    @Override
    public List<Message> getByChatRoomId(Long chatRoomId) {

        List<Message> message = messageRepositorySupport.findByChatRoomId(chatRoomId);

        return message;
    }

    @Override
    public Optional<Message> getLastMessageByChatRoomId(Long chatRoomId) {
        Optional<Message> message = messageRepositorySupport.findLastMessageByChatRoomId(chatRoomId);

        return message;
    }
}
