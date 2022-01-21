package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.conference.Conference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceResponseDto {

    private int id;
    private int user_id;
    private int book_id;
    private Timestamp call_start_time;
    private Timestamp call_end_time;
    private String question;
    private String password;
    private String thumbnail_url;
    private String title;
    private String description;
    private int max_members;
    private String tags;

    public ConferenceResponseDto(Conference conference) {

        this.id = id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.call_start_time = call_start_time;
        this.call_end_time = call_end_time;
        this.question = question;
        this.password = password;
        this.thumbnail_url = thumbnail_url;
        this.title = title;
        this.description = description;
        this.max_members = max_members;
        this.tags = tags;

    }

}
