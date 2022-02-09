package com.ssafy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Bookmark;
import com.ssafy.db.entity.ConferenceHistory;

@Repository
public interface ConferenceHistoryRepository extends JpaRepository<ConferenceHistory, Integer> {
	ConferenceHistory findTopByConferenceIdOrderByInsertedTime(int id);
}
