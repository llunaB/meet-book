package com.jojoldu.book.springboot.domain.conference;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data //equals, hashcode method override / toString / Getter, Setter
@NoArgsConstructor
//@RequiredArgsConstructor
//@AllArgsConstructor
//@Builder
@Entity
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //    @NonNull
    @Column
    private int user_id;

    //    @NonNull
    @Column
    private int book_id;

    //    @NonNull
    @Column
    private Timestamp call_start_time;

    //    @NonNull
    @Column
    private Timestamp call_end_time;

    @Column
    private String question;

    @Column
    private String password;

    @Column
    private String thumbnail_url;

    //    @NonNull
    @Column
    private String title;

    //    @NonNull
    @Column
    private String description;

    //    @NonNull
    @Column
    private int max_members;

    @Column
    private String tags;

}

