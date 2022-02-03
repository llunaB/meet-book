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
import com.ssafy.api.service.ConferenceService;

@RestController
@RequestMapping("/conference")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConferenceController {
	
	private ConferenceService conferenceService;
	
	@Autowired
	public ConferenceController(ConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}
	
	@PostMapping("")
	public ResponseEntity<Map<String, String>> createConference(@RequestBody ConferenceDTO conferenceDto){
		
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			conferenceService.createConference(conferenceDto);
			map.put("message", "회의 생성 성공");
		}catch(Exception e) {
			e.printStackTrace();
			map.put("message", "회의 생성  실패");
		}
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@GetMapping("/list/{pageno}")
	public ResponseEntity<List<ConferenceDTO>> getConferences(@PathVariable("pageno") String pageno){
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();
		try {
			list = conferenceService.getConferences();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ConferenceDTO> getConferenceById(@PathVariable("id") String id){
		ConferenceDTO response = null;
		try {
			response = conferenceService.getConferenceById(Integer.parseInt(id));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ResponseEntity<ConferenceDTO>(response, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String,String>> updateConference(@PathVariable("id") String id, @RequestBody ConferenceDTO conferenceDto){
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			conferenceService.updateConference(conferenceDto);
			map.put("message", "회의 수정 성공");
		}catch(Exception e) {
			e.printStackTrace();
			map.put("message", "회의 수정 실패");
		}
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,String>> deleteConference(@PathVariable("id") String id){
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			conferenceService.deleteConference(Integer.parseInt(id));
			map.put("message", "회의 삭제 성공");
		}catch(Exception e){
			map.put("message", "회의 수정 실패");
		}
		
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/detail")
	public ResponseEntity<ConferenceDTO> getConferenceDetail(@PathVariable("id") String id){
		ConferenceDTO response = null;
		try {
			response = conferenceService.getConferenceById(Integer.parseInt(id));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ConferenceDTO>(response, HttpStatus.OK);
	}
	
}
