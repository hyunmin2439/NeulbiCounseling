package com.ssafy.db.repository;

import com.ssafy.db.entity.MeetingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingHistoryRepository extends JpaRepository<MeetingHistory, Long> {
}
