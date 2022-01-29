package com.ssafy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Book;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {
	List<Conference> findByTitleContaining(String title);
	@Query(value = "select * from conference where book_id in :types", nativeQuery = true)
	List<Conference> findConferencesByBook(@Param("types") List<Book> books);
	@Query(value = "select * from conference where user_id in :types", nativeQuery = true)
	List<Conference> findConferencesByUser(@Param("types") List<User> users);
	List<Conference> findByTagsContaining(String tags);
}
