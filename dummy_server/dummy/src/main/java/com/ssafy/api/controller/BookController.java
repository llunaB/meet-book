package com.ssafy.api.controller;

import com.ssafy.DTO.BookDTO;
import com.ssafy.DTO.ConferenceDTO;
import com.ssafy.api.responseDto.GetBookRes;
import com.ssafy.api.responseDto.GetConferencesRes;
import com.ssafy.api.service.BookService;
import com.ssafy.api.service.ConferenceService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;
    private ConferenceService conferenceService;
    private ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService, ConferenceService conferenceService) {
        this.bookService = bookService;
        this.conferenceService = conferenceService;
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


/*
    @GetMapping("/popular")
    public ResponseEntity<List<GetBookRes>> getBooksOrderByLoanCount() {
        List<GetBookRes> getBookResList = new ArrayList<>();
        try {
            List<GetBookRes> bookDTOList = this.bookService.getBooksOrderByLoanCount();
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
*/



    @GetMapping("{id}/expecting_conf")
    public ResponseEntity<Page<ConferenceDTO>> getConferencesExpectingByBookId(@PathVariable("id") String id, @RequestParam("page") Integer page , @RequestParam("size") Integer size) {
            Page<ConferenceDTO> list = Page.empty();
            PageRequest request = PageRequest.of(page, size);

            try {
                list = conferenceService.getExpectingConferencesByBookId(Integer.parseInt(id), request);
            } catch(Exception e) {
                e.printStackTrace();
            }

            return new ResponseEntity<Page<ConferenceDTO>>(list, HttpStatus.OK);
        }


    @GetMapping("{id}/finished_conf")
    public ResponseEntity<Page<ConferenceDTO>> getConferencesFinishedByBookId(@PathVariable("id") String id, @RequestParam("page") Integer page , @RequestParam("size") Integer size) {
        Page<ConferenceDTO> list = Page.empty();
        PageRequest request = PageRequest.of(page, size);

        try {
            list = conferenceService.getFinishedConferencesByBookId(Integer.parseInt(id), request);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Page<ConferenceDTO>>(list, HttpStatus.OK);
    }
}
