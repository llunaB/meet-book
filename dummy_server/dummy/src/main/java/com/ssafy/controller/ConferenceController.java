package com.ssafy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.DTO.ConferenceDTO;

@RestController
@RequestMapping("/conference")
public class ConferenceController {
	
	@PostMapping("")
	public ResponseEntity<Map<String, String>> makeConf(@RequestBody ConferenceDTO conf){
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("message", "회의 생성 성공");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@GetMapping("/list/{pageno}")
	public ResponseEntity<List<ConferenceDTO>> list(@PathVariable("pageno") String pageno){
		ArrayList<ConferenceDTO> list = new ArrayList<ConferenceDTO>();
		list.add(new ConferenceDTO());
		
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ConferenceDTO> info(@PathVariable("id") String id){
		System.out.println("conference id : "+id);
		return new ResponseEntity<ConferenceDTO>(new ConferenceDTO(), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String,String>> updateConf(@PathVariable("id") String id){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("message", "회의 수정 성공");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,String>> deleteConf(@PathVariable("id") String id){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("message", "회의 삭제 성공");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/detail")
	public ResponseEntity<ConferenceDTO> detailInfo(@PathVariable("id") String id){
		System.out.println("conference id : "+id);
		return new ResponseEntity<ConferenceDTO>(new ConferenceDTO(), HttpStatus.OK);
	}
	
}
