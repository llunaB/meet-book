package com.ssafy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Book;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {
	List<Conference> findByTitleContaining(String title);
	List<Conference> findByBook(Book book);
	List<Conference> findByUser(User user);
	List<Conference> findByTagsContaining(String tags);
}
