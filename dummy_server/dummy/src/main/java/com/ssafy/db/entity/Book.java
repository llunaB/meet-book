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

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "book_name")
	private String bookname;
	
	@Column
	private String book_author;
	
	@Column
	private String book_contents;
	
	@Column
	private String book_publisher;
	
	@Column
	private String isbn;
	
	@Column
	private int book_pub_year;
	
	@ManyToOne
	@JoinColumn(name = "genre_id")
	private Genre genre;
	
	@Column
	private String book_thumbnail_url;
	
	@Column
	private int loan_count;
}
