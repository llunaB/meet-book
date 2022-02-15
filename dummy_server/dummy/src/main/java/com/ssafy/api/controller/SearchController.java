package com.ssafy.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.responseDto.GetBookRes;
import com.ssafy.api.responseDto.GetConferencesRes;
import com.ssafy.api.responseDto.GetSimpleBooksRes;
import com.ssafy.api.responseDto.GetUserByProfileRes;
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
	public ResponseEntity<Page<GetUserByProfileRes>> getUsers(@RequestParam("nickname") String nickname, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<GetUserByProfileRes> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);
		try {
			list = userService.getUsersByNickname(nickname, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Page<GetUserByProfileRes>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/book")
	public ResponseEntity<Page<GetBookRes>> getBooks(@RequestParam("book_name") String bookname, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<GetBookRes> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);
		
		try {
			list = bookService.getBooksByName(bookname, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Page<GetBookRes>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/book/simple")
	public ResponseEntity<Page<GetSimpleBooksRes>> getSimpleBooks(@RequestParam("book_name") String bookname, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<GetSimpleBooksRes> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);
		
		try {
			list = bookService.getSimpleBooksByName(bookname, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Page<GetSimpleBooksRes>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference")
	public ResponseEntity<Page<GetConferencesRes>> getConferencesByTitle(@RequestParam("title") String title, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<GetConferencesRes> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);

		try {
			list = conferenceService.getConferencesByTitle(title, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Page<GetConferencesRes>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/book")
	public ResponseEntity<Page<GetConferencesRes>> getConferencesByBook(@RequestParam("book") String bookname, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<GetConferencesRes> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);

		try {
			list = conferenceService.getConferencesByBook(bookname, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Page<GetConferencesRes>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/user")
	public ResponseEntity<Page<GetConferencesRes>> getConferencesByUser(@RequestParam("user") String nickname, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<GetConferencesRes> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);

		try {
			list = conferenceService.getConferencesByNickname(nickname, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Page<GetConferencesRes>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/tag")
	public ResponseEntity<Page<GetConferencesRes>> getConferencesByTags(@RequestParam("tag") String tag, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<GetConferencesRes> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);

		try {
			list = conferenceService.getConferencesByTags(tag, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Page<GetConferencesRes>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/genre")
	public ResponseEntity<Page<GetConferencesRes>> getConferencesByGenre(@RequestParam("genre") String genre, @RequestParam("page") Integer page , @RequestParam("size") Integer size){
		Page<GetConferencesRes> list = Page.empty();
		PageRequest request = PageRequest.of(page, size);

		try {
			list = conferenceService.getConferencesByGenre(genre, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Page<GetConferencesRes>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/genre/count")
	public ResponseEntity<Map<String, Long>> getConferencesByGenre(@RequestParam("genre") String genre){
		Map<String, Long> map = new HashMap<String, Long>();

		try {
			Long data = conferenceService.countConferenceByGenre(genre);
			map.put("data", data);
		}catch(Exception e) {
			e.printStackTrace();
			map.put("data", -1L);
		}
		return new ResponseEntity<Map<String, Long>>(map, HttpStatus.OK);
	}
}
