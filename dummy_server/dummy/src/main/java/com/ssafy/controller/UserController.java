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
import com.ssafy.DTO.LoginReqDTO;
import com.ssafy.DTO.UserDTO;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@PostMapping("/signup")
	public ResponseEntity<Map<String, String>> signup(@RequestBody UserDTO user){
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("message", "회원가입 성공");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginReqDTO dto){
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(dto.getEmail().equals(dto.getPassword())) {
			map.put("message", "로그인 성공");
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		}
		
		map.put("message", "로그인 실패");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<UserDTO> getInfo(@PathVariable("email") String email){
		
		return new ResponseEntity<UserDTO>(new UserDTO(), HttpStatus.OK);
	}
	
	@GetMapping("/{email}/detail")
	public ResponseEntity<UserDTO> getDetailInfo(@PathVariable("email") String email){
		
		return new ResponseEntity<UserDTO>(new UserDTO(), HttpStatus.OK);
	}
	
	@PutMapping("/{email}")
	public ResponseEntity<Map<String,String>> getDetailInfo(@RequestBody UserDTO u){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("message", "수정 성공");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/{email}")
	public ResponseEntity<Map<String,String>> deleteUser(@RequestBody String password, @PathVariable("email") String email){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("message", "삭제 성공");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@PostMapping("/{user}/bookmark/{conf}")
	public ResponseEntity<Map<String,String>> addBookmark(@PathVariable("user") String userid, @PathVariable("conf") String confid){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("message", "북마크 추가 성공");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/{user}/bookmark/{conf}")
	public ResponseEntity<Map<String,String>> deleteBookmark(@PathVariable("user") String userid, @PathVariable("conf") String confid){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("message", "북마크 삭제 성공");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@GetMapping("/{user}/bookmark")
	public ResponseEntity<List<ConferenceDTO>> getBookmarkList(@PathVariable("user") String userid){
		
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();
		list.add(new ConferenceDTO());
		
		return new ResponseEntity<List<ConferenceDTO>>(list, HttpStatus.OK);
	}
	
}
