package com.ssafy.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
	Optional<User> findByNickname(String nickname);
	Page<User> findByNicknameContaining(String nickname, Pageable pageable);
	List<User> findByNicknameContaining(String nickname);

//	@Query(value="select u.email from user u where u.id in (Select b.user_id from bookmark b, conference c where c.call_start_time Like concat('2022-02-09','%') and c.id = b.conference_id)", nativeQuery = true)
//	Optional<String> findByDate(@Param("reserve") String date);

}
