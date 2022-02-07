package com.ssafy.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.DTO.BookDTO;
import com.ssafy.DTO.ConferenceDTO;
import com.ssafy.DTO.UserDTO;
import com.ssafy.api.service.BookService;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.UserService;

@RestController
@RequestMapping("/search")
public class SearchController {
	private UserService userService;
	private BookService bookService;
	private ConferenceService conferenceService;

	@Autowired
	public SearchController(UserService userService, BookService bookService, ConferenceService conferenceService) {
		this.userService = userService;
		this.bookService = bookService;
		this.conferenceService = conferenceService;
	}

	@GetMapping("/users")
	public ResponseEntity<Page<UserDTO>> getUser(@RequestParam("nickname") String nickname, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<UserDTO> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);
		try {
			list = userService.getUsersByNickname(nickname, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Page<UserDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/book")
	public ResponseEntity<Page<BookDTO>> getBook(@RequestParam("book_name") String bookname, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<BookDTO> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);
		
		try {
			list = bookService.getBooksByName(bookname, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Page<BookDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference")
	public ResponseEntity<Page<ConferenceDTO>> getConferenceByTitle(@RequestParam("title") String title, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<ConferenceDTO> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);

		try {
			list = conferenceService.getConferencesByTitle(title, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Page<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/book")
	public ResponseEntity<Page<ConferenceDTO>> getConferenceByBook(@RequestParam("book") String bookname, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<ConferenceDTO> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);

		try {
			list = conferenceService.getConferencesByBook(bookname, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Page<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/user")
	public ResponseEntity<Page<ConferenceDTO>> getConferenceByUser(@RequestParam("user") String nickname, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<ConferenceDTO> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);

		try {
			list = conferenceService.getConferencesByNickname(nickname, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Page<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/tag")
	public ResponseEntity<Page<ConferenceDTO>> getConferenceByTags(@RequestParam("tag") String tag, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<ConferenceDTO> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);

		try {
			list = conferenceService.getConferencesByTags(tag, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Page<ConferenceDTO>>(list, HttpStatus.OK);
	}
}
