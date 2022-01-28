package com.ssafy.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.ssafy.db.entity.Book;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;

@RestController
@RequestMapping("/search")
public class SearchController {
	private UserService uService;
	private BookService bService;
	private ConferenceService cService;
	
	@Autowired
	public SearchController(UserService uService, BookService bService, ConferenceService cService) {
		this.uService = uService;
		this.bService = bService;
		this.cService = cService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> userSearch(@RequestParam("nickname") String nickname){
		List<UserDTO> list = uService.getUserByNickname(nickname);
		
		return new ResponseEntity<List<UserDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/book")
	public ResponseEntity<List<BookDTO>> bookSearch(@RequestParam("book_name") String bookname){
		ArrayList<BookDTO> list = new ArrayList<>();
		List<Book> entities = bService.getBookByName(bookname);
		for(Book b : entities) {
			list.add(bService.Entity2Dto(b));
		}
		
		return new ResponseEntity<List<BookDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference")
	public ResponseEntity<List<ConferenceDTO>> conferenceSearch(@RequestParam("title") String title){
		ArrayList<ConferenceDTO> list = new ArrayList<>();
		List<Conference> entities = cService.findByTitle(title);
		for(Conference c : entities) {
			list.add(cService.Entity2Dto(c));
		}
		
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/book")
	public ResponseEntity<List<ConferenceDTO>> conferenceBookSearch(@RequestParam("book") String bookname){
		ArrayList<ConferenceDTO> list = new ArrayList<>();
		for(Book book : bService.getBookByName(bookname)) {
			List<Conference> entities = cService.findByBook(book);
			for(Conference c : entities) {
				list.add(cService.Entity2Dto(c));
			}
		}
		
		
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/user")
	public ResponseEntity<List<ConferenceDTO>> conferenceUserSearch(@RequestParam("user") String nickname){
		ArrayList<ConferenceDTO> list = new ArrayList<>();
		List<Conference> entities = cService.findByUser(nickname);
		for(Conference c : entities) {
			list.add(cService.Entity2Dto(c));
		}
		
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/tag")
	public ResponseEntity<List<ConferenceDTO>> conferenceTagSearch(@RequestParam("tag") String tag){
		ArrayList<ConferenceDTO> list = new ArrayList<>();
		List<Conference> entities = cService.findByTags(tag);
		for(Conference c : entities) {
			list.add(cService.Entity2Dto(c));
		}
		
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
}
