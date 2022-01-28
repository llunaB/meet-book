package com.ssafy.db.entity;

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
public class Bookmark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Key")
	private int id;

	@ManyToOne
	@JoinColumn(name = "CONFERENCE_ID")
	private Conference conference;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Column
	private int alarm;
}
