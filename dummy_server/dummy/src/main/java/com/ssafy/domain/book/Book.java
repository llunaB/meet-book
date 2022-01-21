package com.ssafy.domain.book;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ssafy.domain.genre.Genre;

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
	
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp book_pubdate;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private Genre genre;
	
	@Column
	private String book_thumbnail_url;
}
