package com.ssafy.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@Column(name = "call_start_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date callStartTime;	
	
	@Column(name = "call_end_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date callEndTime;

	@Column(name = "thumbnail_url")
	private String thumbnailUrl;

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

	@Column(name = "max_members")
	private int maxMembers;

	@Column
	private String tags;
	
//	@OneToMany
//	@JoinColumn(name = "conference_id", insertable = false, updatable = false)
//	private List<Bookmark> bookmarks = new ArrayList<Bookmark>();
}
