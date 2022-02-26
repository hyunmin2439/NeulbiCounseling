package com.ssafy.db.repository;

import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {



}
