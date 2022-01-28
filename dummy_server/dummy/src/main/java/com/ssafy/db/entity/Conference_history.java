package com.ssafy.db.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Conference_history {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(targetEntity = Conference.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "conference_id")
	private Conference conference;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column
	private int action;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date inserted_time;
	
}
