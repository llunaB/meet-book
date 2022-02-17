package com.ssafy.api.responseDto;

import com.ssafy.DTO.BookDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetBookRes {
    private int id;
    private String name;
    private String author;
    private String contents;
    private String publisher;
    private String isbn;
    private int pubYear;
    private int loanCount;
    private int genreId;
    private String thumbnailUrl;

    public GetBookRes(BookDTO data) {
        setId(data.getId());
        setName(data.getBookName());
        setAuthor(data.getBookAuthor());
        setContents(data.getBookContents());
        setPublisher(data.getBookPublisher());
        setIsbn(data.getIsbn());
        setPubYear(data.getBookPubYear());
        setLoanCount(data.getLoanCount());
        setGenreId(data.getGenreId());
        setThumbnailUrl(data.getBookThumbnailUrl());
    }
}
