package com.example.wdpai2backend.repository;

import com.example.wdpai2backend.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority>  findByAuthority(String authority);

}
