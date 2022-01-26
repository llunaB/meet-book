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
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String book_name;
	
	@Column
	private String book_author;
	
	@Column
	private String book_contents;
	
	@Column
	private String book_publisher;
	
	@Column
	private String isbn;
	
	@Column
	private String book_pubYear;
	
	@ManyToOne
	@JoinColumn(name = "GENRE_ID")
	private Genre genre;
	
	@Column
	private String book_thumbnail_url;
	
	@Column
	private int loan_count;
}
