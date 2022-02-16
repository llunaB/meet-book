package com.ssafy.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Bookmark;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
	List<Bookmark> findByUser(User user);
	@Query(value="select b.* from bookmark b, conference c where b.alarm =1 and c.call_start_time Like concat(:reserve,'%') and c.id = b.conference_id", nativeQuery = true)
	List<Bookmark> findByDate(@Param("reserve") String date);
	List<Bookmark> findByUserAndConference(User user, Conference conference);
	List<Bookmark> findByConferenceId(int conferenceId);

}
