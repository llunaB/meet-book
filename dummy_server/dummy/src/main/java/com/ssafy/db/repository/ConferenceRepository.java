package com.ssafy.db.repository;

import java.util.Date;
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
	Page<Conference> findConferencesByBookBookNameContaining(String bookName, Pageable pageable);
	Page<Conference> findConferencesByUserNicknameContaining(String nickname, Pageable pageable);
	Page<Conference> findConferencesByUserNickname(String nickname, Pageable pageable);
	Page<Conference> findByTagsContaining(String tags, Pageable pageable);
	Page<Conference> findConferenceByBookGenreGenreOrderByCallStartTime(String genre, Pageable pageable);
	Page<Conference> findConferenceByBookGenreGenreAndCallEndTimeAfterOrderByCallStartTime(String genre, Date current, Pageable pageable);
	Page<Conference> findConferenceByBookGenreGenreAndCallEndTimeBeforeOrderByCallStartTime(String genre, Date current, Pageable pageable);
	Long countByBookGenreGenre(String genre);


	@Query(value = "select * from conference c where c.book_id = :id AND c.call_end_time < CURRENT_TIMESTAMP", nativeQuery = true)
	Page<Conference> findFinishedConferencesByBookId(@Param("id") Integer id, Pageable pageable);

	@Query(value = "select * from conference c where c.book_id = :id AND c.call_start_time > CURRENT_TIMESTAMP", nativeQuery = true)
	Page<Conference> findExpectingConferencesByBookId(@Param("id") Integer id, Pageable pageable);

	@Query(value = "select * from conference c where c.user_id = :id AND c.call_start_time > CURRENT_TIMESTAMP", nativeQuery = true)
    Page<Conference> findById(@Param("id") Integer id, Pageable pageable);
}
