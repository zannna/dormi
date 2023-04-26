package com.example.wdpai2backend.repository;

import com.example.wdpai2backend.entity.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<AppUser, Long> {
    @Query("SELECT a FROM AppUser a  join fetch a.authorities  WHERE a.email = ?1")
    Optional<AppUser> findByEmail(String email);
    @Query( "SELECT a.dormitory.id_dorm FROM AppUser a WHERE a.email = ?1" )
    Long findIdDormitoryByEmail(String email);

}
