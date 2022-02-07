package com.ssafy.api.controller;

import com.ssafy.DTO.BookDTO;
import com.ssafy.api.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public ResponseEntity<List<BookDTO>> getBooks() {
        List<BookDTO> list = new ArrayList<>();
        try {
            list = this.bookService.getBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<BookDTO>>(list, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") String id) {
        BookDTO response = null;
        try {
            response = bookService.getBookById(Integer.parseInt(id));
            log.info(String.valueOf(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<BookDTO>(response, HttpStatus.OK);
    }

}
