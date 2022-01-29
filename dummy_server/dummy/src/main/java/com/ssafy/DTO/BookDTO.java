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
	private String bookName;
	private String bookAuthor;
	private String bookContents;
	private String bookPublisher;
	private String isbn;
	private int bookPubYear;
	private int loanCount;
	private int genreId;
	private String bookThumbnailUrl;
}
