package com.ssafy.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ssafy.DTO.LoginReqDTO;
import com.ssafy.DTO.UserDTO;
import com.ssafy.api.response.BookmarkResDTO;
import com.ssafy.api.service.BookmarkService;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.UserService;
import com.ssafy.db.entity.Bookmark;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;

@RestController
@RequestMapping("/users")
public class UserController {
	private UserService service;
	private BookmarkService bookmarkService;
	private ConferenceService confService;
	
	@Autowired
	public UserController(UserService service, BookmarkService bookmarkService, ConferenceService confService) {
		this.service = service;
		this.bookmarkService = bookmarkService;
		this.confService = confService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<Map<String, String>> signup(@RequestBody UserDTO user){
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(service.createUser(service.Dto2Entity(user))) {
			System.out.println("user created");
			map.put("message", "회원가입 성공");

		}else {
			System.out.println("fail user created");
			map.put("message", "회원가입 실패");

		}
		
		
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginReqDTO dto){
		HashMap<String, String> map = new HashMap<String, String>();
		
		User user = service.getUserByeEmail(dto.getEmail());
		
		if(user.getPassword().equals(dto.getPassword())) {
			map.put("message", "로그인 성공");
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		}
		
		map.put("message", "로그인 실패");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<UserDTO> getInfo(@PathVariable("email") String email){
		User user = service.getUserByeEmail(email);
		return new ResponseEntity<UserDTO>(service.Entity2Dto(user), HttpStatus.OK);
	}
	
	@GetMapping("/{email}/detail")
	public ResponseEntity<UserDTO> getDetailInfo(@PathVariable("email") String email){
		User user = service.getUserByeEmail(email);
		return new ResponseEntity<UserDTO>(service.Entity2Dto(user), HttpStatus.OK);
	}
	
	@PutMapping("/{email}")
	public ResponseEntity<Map<String,String>> getDetailInfo(@RequestBody UserDTO u){
		
		HashMap<String, String> map = new HashMap<String, String>();
		service.updateUser(service.Dto2Entity(u));
		
		map.put("message", "수정 성공");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/{email}")
	public ResponseEntity<Map<String,String>> deleteUser(@RequestBody String password, @PathVariable("email") String email){
		HashMap<String, String> map = new HashMap<String, String>();
		User user = service.getUserByeEmail(email);
		if(password.equals(user.getPassword())) {
			map.put("message", "삭제 성공");
			service.deleteUser(user.getId());
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		}else {
			map.put("message", "삭제 실패");
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		}
		
		
	}
	
	@PostMapping("/{user}/bookmark/{conf}")
	public ResponseEntity<Map<String,String>> addBookmark(@PathVariable("user") String userid, @PathVariable("conf") String confid){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		Bookmark bk = new Bookmark();
		User user = service.getUserById(Integer.parseInt(userid));
		Conference conf = confService.getConferenceById(Integer.parseInt(confid));
		
		bk.setAlarm(1);
		bk.setConference(conf);
		bk.setUser(user);
		
		bookmarkService.createBookmark(bk);
		
		map.put("message", "북마크 추가 성공");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/{user}/bookmark/{bookmark}")
	public ResponseEntity<Map<String,String>> deleteBookmark(@PathVariable("user") String userid, @PathVariable("bookmark") String bk_id){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		bookmarkService.deleteBookmark(Integer.parseInt(bk_id));
		
		map.put("message", "북마크 삭제 성공");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@GetMapping("/{user}/bookmark")
	public ResponseEntity<List<BookmarkResDTO>> getBookmarkList(@PathVariable("user") String userid){
		
		List<BookmarkResDTO> list = new ArrayList<BookmarkResDTO>();
		User user = service.getUserById(Integer.parseInt(userid));
		List<Bookmark> bookmarks = bookmarkService.getBookmarks(user);
		for(Bookmark bk : bookmarks) {
			BookmarkResDTO dto = new BookmarkResDTO();
			dto.setId(bk.getId());
			dto.setTitle(bk.getConference().getTitle());
			dto.setDate(bk.getConference().getCall_start_time());
			dto.setAlarm(bk.getAlarm() == 1);
			list.add(dto);
		}
		
		
		return new ResponseEntity<List<BookmarkResDTO>>(list, HttpStatus.OK);
	}
	
}
