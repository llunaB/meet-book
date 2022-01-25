package com.ssafy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Bookmark;
import com.ssafy.db.entity.User;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
	List<Bookmark> findByUser(User user);
}
