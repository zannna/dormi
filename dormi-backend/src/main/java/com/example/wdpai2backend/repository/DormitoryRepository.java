package com.example.wdpai2backend.repository;

import com.example.wdpai2backend.entity.Dormitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DormitoryRepository extends JpaRepository<Dormitory, Long> {
    @Query("SELECT d FROM Dormitory d WHERE d.dorm_name = ?1")
    Optional<Dormitory> findByDorm_name(String dorm_name);
}
