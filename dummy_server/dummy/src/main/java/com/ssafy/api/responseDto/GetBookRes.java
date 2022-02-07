package com.ssafy.api.responseDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}
