package com.example.wdpai2backend.repository;

import com.example.wdpai2backend.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
