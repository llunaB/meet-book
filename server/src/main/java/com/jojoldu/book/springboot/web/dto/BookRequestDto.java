package com.jojoldu.book.springboot.web.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {

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

}
