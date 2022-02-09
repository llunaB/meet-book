package com.ssafy.api.controller;

import com.ssafy.DTO.BookDTO;
import com.ssafy.api.responseDto.GetBookRes;
import com.ssafy.api.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;
    private ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("")
    public ResponseEntity<List<GetBookRes>> getBooks() {
        List<GetBookRes> getBookResList = new ArrayList<>();
        try {
            List<GetBookRes> bookDTOList = this.bookService.getBooks();
            // BookDTOList -> GetBookResList
            getBookResList = bookDTOList.stream().map(source -> {
                GetBookRes getBookRes = modelMapper.map(source, GetBookRes.class);
                return getBookRes;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<GetBookRes>>(getBookResList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<GetBookRes> getBookById(@PathVariable("id") String id) {
        BookDTO bookDTO = new BookDTO();
        try {
            bookDTO = bookService.getBookById(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<GetBookRes>(new GetBookRes(bookDTO), HttpStatus.OK);
    }

}
