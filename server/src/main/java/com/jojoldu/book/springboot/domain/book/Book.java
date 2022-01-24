package com.jojoldu.book.springboot.domain.book;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data //equals, hashcode method override / toString / Getter, Setter
@NoArgsConstructor
//@RequiredArgsConstructor error: constructor Book() is already defined in class Book
@AllArgsConstructor
@Builder
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // @NonNull // warning: @NonNull is meaningless on a primitive.
    @Column
    private String book_name;

    // @NonNull
    @Column
    private String book_author;

    // @NonNull
    @Column
    private String book_contents;

    // @NonNull
    @Column
    private String book_publisher;

    // @NonNull
    @Column
    private String isbn;

    // @NonNull
    @Column
    private Timestamp book_pubdate;

    // @NonNull
    @Column
    private int loan_count;

    // @NonNull
    @Column
    private int genre_id;

    // @NonNull
    @Column
    private String book_thumbnail_url;

}
