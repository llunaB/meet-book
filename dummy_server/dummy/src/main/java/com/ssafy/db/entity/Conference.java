package com.ssafy.db.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Conference {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date call_start_time;	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date call_end_time;

	@Column
	private String thumbnail_url;

	@Column
	private String title;

	@Column
	private String description;

	@ManyToOne
	@JoinColumn(name = "BOOK_ID")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column
	private String question;

	@Column
	private String password;

	@Column
	private int max_members;

	@Column
	private String tags;
}
