package com.ssafy.db.repository;

import com.ssafy.db.entity.Email;
import com.ssafy.db.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {

    Optional<Email> findByEmailAndKey(String email, String key);

    Optional<Email> findByEmail(String email);
}
