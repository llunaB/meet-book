package com.ssafy.db.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Book;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {
	Page<Conference> findByTitleContaining(String title, Pageable pageable);
	@Query(value = "select * from conference where book_id in :types", nativeQuery = true)
	Page<Conference> findConferencesByBook(@Param("types") List<Book> books, Pageable pageable);
	@Query(value = "select * from conference where user_id in :types", nativeQuery = true)
	Page<Conference> findConferencesByUser(@Param("types") List<User> users, Pageable pageable);
	Page<Conference> findByTagsContaining(String tags, Pageable pageable);
}
