package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Message;
import com.ssafy.db.entity.QMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QMessage qMessage = QMessage.message1;

    @Autowired
    private MessageRepository messageRepository;



    public List<Message> findByChatRoomId(Long chatRoomId) {

        List<Message> message = jpaQueryFactory.select(qMessage)
                .from(qMessage)
                .where(qMessage.chatRoomId.eq(chatRoomId))
                .orderBy(qMessage.createdDate.desc())
                .fetch();

        if(message == null) return null;
        return message;
    }

    public Optional<Message> findLastMessageByChatRoomId(Long chatRoomId) {
        Message message = jpaQueryFactory.select(qMessage)
                .from(qMessage)
                .where(qMessage.chatRoomId.eq(chatRoomId))
                .orderBy(qMessage.createdDate.desc())
                .limit(1)
                .fetchOne();

        if(message == null) return Optional.empty();
        else return Optional.ofNullable(message);
    }

}
