package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.ChatRoom;
import com.ssafy.db.entity.QChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ChatRoomRepositorySupport {


    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QChatRoom qChatRoom = QChatRoom.chatRoom;

    public Optional<ChatRoom> findChatRoomByUserIdAndConsultantId(Long userId, Long consultantId) {

        ChatRoom chatRoom = jpaQueryFactory.select(qChatRoom)
                .from(qChatRoom)
                .where(qChatRoom.userId.eq(userId).and(qChatRoom.consultantId.eq(consultantId))).fetchOne();

        if(chatRoom == null) {
            System.out.println(userId+"가 유저인 방은 없네요");
            ChatRoom chatRoom1 = jpaQueryFactory.select(qChatRoom)
                    .from(qChatRoom)
                    .where(qChatRoom.consultantId.eq(userId).and(qChatRoom.userId.eq(consultantId))).fetchOne();
            if(chatRoom1 == null)
            {
                System.out.println(userId+"가 컨설턴트인 방도 없어요.");
                return Optional.empty();
            }
            return Optional.ofNullable(chatRoom1);
        }
        return Optional.ofNullable(chatRoom);
    }

    public List<ChatRoom> findChatRoomByUserId(Long userId) {
        System.out.println("유저 아이디>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(userId);
        List<ChatRoom> chatRooms = jpaQueryFactory.select(qChatRoom)
                .from(qChatRoom)
                .where(qChatRoom.userId.eq(userId)
                        .or(qChatRoom.consultantId.eq(userId)))
                .orderBy(qChatRoom.modifiedDate.desc(),qChatRoom.createdDate.desc())
                .fetch();

        if(chatRooms == null) return null;
        return chatRooms;
    }

    public ChatRoom findChatRoomById(Long id) {
        ChatRoom chatRoom = jpaQueryFactory.select(qChatRoom).from(qChatRoom).where(qChatRoom.id.eq(id)).fetchOne();

        return chatRoom;
    }

    public void updateChatRoomById(Long id) {
        jpaQueryFactory.update(qChatRoom)
                .where(qChatRoom.id.eq(id))
                .set(qChatRoom.modifiedDate,LocalDateTime.now())
                .execute();
    }
}
