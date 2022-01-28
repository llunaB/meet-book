package com.ssafy.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.DTO.ConferenceDTO;
import com.ssafy.DTO.UserDTO;
import com.ssafy.api.service.BookService;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.UserService;
import com.ssafy.db.entity.Book;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;

@RestController
@RequestMapping("/conference")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConferenceController {
	
	private ConferenceService service;
	private UserService uService;
	private BookService bService;
	
	@Autowired
	public ConferenceController(ConferenceService conf, UserService u, BookService b) {
		this.service = conf;
		this.uService = u;
		this.bService = b;
	}
	
	@PostMapping("")
	public ResponseEntity<Map<String, String>> makeConf(@RequestBody ConferenceDTO conf){
		
		HashMap<String, String> map = new HashMap<String, String>();
		UserDTO user = uService.getUserById(conf.getUser_id());
		Book book = bService.getBookById(conf.getBook_id());
		
		service.createConference(service.Dto2Entity(conf, uService.Dto2Entity(user), book));
		
		map.put("message", "회의 생성 성공");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@GetMapping("/list/{pageno}")
	public ResponseEntity<List<ConferenceDTO>> list(@PathVariable("pageno") String pageno){
		ArrayList<ConferenceDTO> list = new ArrayList<ConferenceDTO>();
		List<Conference> data = service.getAllConf();
		for(Conference c : data) {
			list.add(service.Entity2Dto(c));
		}
		
		
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ConferenceDTO> info(@PathVariable("id") String id){
		System.out.println("conference id : "+id);
		return new ResponseEntity<ConferenceDTO>(service.Entity2Dto(service.getConferenceById(Integer.parseInt(id))), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String,String>> updateConf(@PathVariable("id") String id, @RequestBody ConferenceDTO dto){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("message", "회의 수정 성공");
		UserDTO user = uService.getUserById(dto.getUser_id());
		Book book = bService.getBookById(dto.getBook_id());
		service.updateConference(service.Dto2Entity(dto, uService.Dto2Entity(user), book));
		
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,String>> deleteConf(@PathVariable("id") String id){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("message", "회의 삭제 성공");
		
		service.deleteConference(Integer.parseInt(id));
		
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/detail")
	public ResponseEntity<ConferenceDTO> detailInfo(@PathVariable("id") String id){
		System.out.println("conference id : "+id);
		return new ResponseEntity<ConferenceDTO>(service.Entity2Dto(service.getConferenceById(Integer.parseInt(id))), HttpStatus.OK);
	}
	
}
