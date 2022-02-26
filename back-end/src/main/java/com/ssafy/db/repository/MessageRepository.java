package com.ssafy.db.repository;

import com.ssafy.db.entity.Message;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByChatRoomId(Long chatRoomId);
    Optional<Message> findFirstByOrderByCreatedDateDesc();
}
