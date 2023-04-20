package com.example.wdpai2backend.repository;

import com.example.wdpai2backend.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

//    @Query(nativeQuery = true, value = "SELECT * FROM problems p WHERE p.date = :dateOfLog")
//    LoggerEntity findLogByDate(@Param("dateOfLog") Date dateOfLog);

    @Query("SELECT p FROM Problem p WHERE p.appUser.email = ?1 ORDER BY p.problem_date DESC")
    List<Problem> findUserSortedProblems(String email);

    @Query("SELECT p FROM Problem p WHERE p.dormitory.id_dorm = ?1  AND p.status = 1 ORDER BY p.problem_date DESC")
    List<Problem> findUnresolvedProblems(Long id_dorm);

    @Query(nativeQuery = true, value="UPDATE problems p SET status = ?2 WHERE p.id_problem = ?1 returning p.id_problem")
    Optional<Integer> updateProblemStatus(Long id_problem, Integer status);


}
