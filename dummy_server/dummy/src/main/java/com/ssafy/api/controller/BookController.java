package com.ssafy.api.controller;

import com.ssafy.api.service.BookService;
import com.ssafy.api.service.OpenApiService;
import com.ssafy.db.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequestMapping("/book")
@RestController
public class BookController {

    private final OpenApiService openApiService;
    private final BookService bookService;

    @Autowired
    public BookController(OpenApiService openApiService, BookService bookService) {
        this.openApiService = openApiService;
        this.bookService = bookService;
    }

    @GetMapping("/load")
    public boolean createBookData() {
        HashMap<String, String> map = new HashMap<String, String>();

        List<Book> books = openApiService.loadBookData();
        boolean result = bookService.loadBookData(books);

        if (result) {
            System.out.println("book data load created");
            map.put("message", "도서 데이터 탑재 성공");
        } else {
            System.out.println("book data load failed");
            map.put("message", "도서 데이터 탑재 실패");
        }

        return result;
    }
}
