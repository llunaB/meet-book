package com.ssafy.db.repository;

import com.ssafy.db.entity.JwtUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtUserRepository extends JpaRepository<JwtUser,Long> {
    Optional<JwtUser> findByEmail(String email);
}
