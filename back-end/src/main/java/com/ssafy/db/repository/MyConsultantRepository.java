package com.ssafy.db.repository;

import com.ssafy.db.entity.LikeCheck;
import com.ssafy.db.entity.MyConsultant;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyConsultantRepository extends JpaRepository<MyConsultant, Long> {

}
