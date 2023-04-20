package com.example.wdpai2backend.repository;

import com.example.wdpai2backend.entity.Dormitory;
import com.example.wdpai2backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.appUser.dormitory = ?1")
    List<Message> findAllMessagesByDorm(Dormitory dormitory);
}
