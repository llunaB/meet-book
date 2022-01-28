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
public class Conference {
	@Id
	@Column(name="confernece_id")
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

	@ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private Book book;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
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
