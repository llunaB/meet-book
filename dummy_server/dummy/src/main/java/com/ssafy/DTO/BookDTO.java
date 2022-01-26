package com.ssafy.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
	private int id;
	private String book_name;
	private String book_author;
	private String book_contents;
	private String book_publisher;
	private String isbn;
	private String book_pubYear;
	private int loan_count;
	private int genre_id;
	private String book_thumbnail_url;
}
