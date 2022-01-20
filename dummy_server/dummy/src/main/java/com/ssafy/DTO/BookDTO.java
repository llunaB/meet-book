package com.ssafy.DTO;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class BookDTO {
	private int id;
	private String book_name;
	private String book_author;
	private String book_contents;
	private String book_publisher;
	private String isbn;
	private Timestamp book_pubdate;
	private int loan_count;
	private int genre_id;
	private String book_thumbnail_url;
	
	public BookDTO() {
		id = 2;
		book_name="ssafy tutorial";
		book_author	="ssafy";
		book_contents="tutorial";
		book_publisher="ssafy";
		isbn="isbn0000000";
		book_pubdate= Timestamp.valueOf("2016-11-22 12:34:56.7");
		loan_count = 10;
		genre_id = 2;
		book_thumbnail_url = "url.of.image/book_id";
	}
}
