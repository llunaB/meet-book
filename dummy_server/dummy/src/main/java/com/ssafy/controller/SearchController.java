package com.ssafy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.DTO.BookDTO;
import com.ssafy.DTO.ConferenceDTO;
import com.ssafy.DTO.UserDTO;

@RestController
@RequestMapping("/search")
public class SearchController {
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> userSearch(@RequestParam("nickname") String nickname){
		ArrayList<UserDTO> list = new ArrayList<UserDTO>();
		list.add(new UserDTO());
		
		return new ResponseEntity<List<UserDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/book")
	public ResponseEntity<List<BookDTO>> bookSearch(@RequestParam("book_name") String book_name){
		ArrayList<BookDTO> list = new ArrayList<>();
		list.add(new BookDTO());
		
		return new ResponseEntity<List<BookDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference")
	public ResponseEntity<List<ConferenceDTO>> conferenceSearch(@RequestParam("title") String title){
		ArrayList<ConferenceDTO> list = new ArrayList<>();
		list.add(new ConferenceDTO());
		
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/book")
	public ResponseEntity<List<ConferenceDTO>> conferenceBookSearch(@RequestParam("book") String book){
		ArrayList<ConferenceDTO> list = new ArrayList<>();
		list.add(new ConferenceDTO());
		
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/user")
	public ResponseEntity<List<ConferenceDTO>> conferenceUserSearch(@RequestParam("user") String user){
		ArrayList<ConferenceDTO> list = new ArrayList<>();
		list.add(new ConferenceDTO());
		
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/conference/tag")
	public ResponseEntity<List<ConferenceDTO>> conferenceTagSearch(@RequestParam("tag") String tag){
		ArrayList<ConferenceDTO> list = new ArrayList<>();
		list.add(new ConferenceDTO());
		
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
}
