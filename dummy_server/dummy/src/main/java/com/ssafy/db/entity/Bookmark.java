package com.ssafy.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Column
	private int alarm;

	@Override
	public String toString() {
		return "Bookmark{" +
				"id=" + id +
				", conference=" + conference +
				", user=" + user +
				", alarm=" + alarm +
				'}';
	}
}
