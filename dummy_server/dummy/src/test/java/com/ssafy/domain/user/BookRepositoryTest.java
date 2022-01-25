package com.ssafy.domain.user;

import com.ssafy.domain.book.Book;
import com.ssafy.domain.book.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void bookTest() {
        Book book = new Book();
        book.setBook_name("book1");
        book.setBook_author("author1");

        bookRepository.save(book);
        System.out.println(bookRepository.findAll());

    }

}
