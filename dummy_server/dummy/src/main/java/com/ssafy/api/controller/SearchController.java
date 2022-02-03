package com.ssafy.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
	public ResponseEntity<List<UserDTO>> getUser(@RequestParam("nickname") String nickname){
		List<UserDTO> list = new ArrayList<UserDTO>();
		
		try {
			list = userService.getUsersByNickname(nickname);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<UserDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/book")
	public ResponseEntity<List<BookDTO>> getBook(@RequestParam("book_name") String bookname){
		List<BookDTO> list = new ArrayList<BookDTO>();
		
		try {
			list = bookService.getBooksByName(bookname);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<BookDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference")
	public ResponseEntity<List<ConferenceDTO>> getConferenceByTitle(@RequestParam("title") String title){
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();

		try {
			list = conferenceService.getConferencesByTitle(title);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/book")
	public ResponseEntity<List<ConferenceDTO>> getConferenceByBook(@RequestParam("book") String bookname){
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();

		try {
			list = conferenceService.getConferencesByBook(bookname);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/user")
	public ResponseEntity<List<ConferenceDTO>> getConferenceByUser(@RequestParam("user") String nickname){
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();

		try {
			list = conferenceService.getConferencesByNickname(nickname);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/tag")
	public ResponseEntity<List<ConferenceDTO>> getConferenceByTags(@RequestParam("tag") String tag){
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();
		
		try {
			list = conferenceService.getConferencesByTags(tag);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
}
