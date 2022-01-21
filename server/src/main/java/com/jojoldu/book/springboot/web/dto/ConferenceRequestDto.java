package com.jojoldu.book.springboot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceRequestDto {

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

}