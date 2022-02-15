package com.ssafy.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.ConferenceHistory;
import com.ssafy.db.mapping.ConferenceOnly;

@Repository
public interface ConferenceHistoryRepository extends JpaRepository<ConferenceHistory, Integer> {
	ConferenceHistory findTopByConferenceIdOrderByInsertedTime(int id);
	Page<ConferenceOnly> findDistinctConferenceByUserIdAndActionOrderByConferenceCallStartTimeDesc(int id, String action, Pageable pageable );
}
