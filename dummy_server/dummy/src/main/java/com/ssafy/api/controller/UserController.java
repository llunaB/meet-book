package com.ssafy.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.DTO.UserDTO;
import com.ssafy.api.request.DeleteUserRequestDTO;
import com.ssafy.api.request.LoginUserRequestDTO;
import com.ssafy.api.request.SignUpUserRequestDTO;
import com.ssafy.api.request.UpdateProfileRequestDTO;
import com.ssafy.api.request.UpdateUserRequestDTO;
import com.ssafy.api.response.BookmarkResDTO;
import com.ssafy.api.response.MessageDTO;
import com.ssafy.api.response.UserDetailInfoResponseDTO;
import com.ssafy.api.response.UserInfoResponseDTO;
import com.ssafy.api.service.BookmarkService;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.UserService;
import com.ssafy.db.entity.Bookmark;

@RestController
@RequestMapping("/users")
public class UserController {
	private UserService service;
	private BookmarkService bookmarkService;
	
	@Autowired
	public UserController(UserService service, BookmarkService bookmarkService) {
		this.service = service;
		this.bookmarkService = bookmarkService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<MessageDTO> signup(@RequestBody SignUpUserRequestDTO user){
		MessageDTO map = new MessageDTO();
		if(service.createUser(new UserDTO(user))) {
			System.out.println("user created");
			map.setMessage("회원가입 성공");

		}else {
			System.out.println("fail user created");
			map.setMessage("회원가입 실패");

		}

		return new ResponseEntity<MessageDTO>(map, HttpStatus.OK);

	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginUserRequestDTO dto){
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			String token = service.login(dto);
			if(!token.equals("")) {
				map.put("message", "로그인 성공");
	            map.put("token",token);
				return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
			}
			map.put("message", "로그인 실패");
		}catch(Exception e){
			e.printStackTrace();
			map.put("message", "로그인 실패");
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
		}
		map.put("message", "로그인 실패");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserInfoResponseDTO> getInfo(@PathVariable("id") String id){
		UserDTO user = service.getUserById(Integer.parseInt(id));
		return new ResponseEntity<UserInfoResponseDTO>(new UserInfoResponseDTO(user), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/detail")
	public ResponseEntity<UserDetailInfoResponseDTO> getDetailInfo(@PathVariable("id") String id){
		UserDTO user = service.getUserById(Integer.parseInt(id));
		return new ResponseEntity<UserDetailInfoResponseDTO>(new UserDetailInfoResponseDTO(user), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String,String>> updateProfile(@PathVariable("id") String id, @RequestBody UpdateProfileRequestDTO dto){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		
		 if(service.updateProfile(dto, Integer.parseInt(id))){
            UserDTO user = service.getUserById(Integer.parseInt(id));
            map.put("message", "회원정보 수정 성공");
            map.put("token",service.login(new LoginUserRequestDTO(user.getEmail(), user.getPassword())));
            return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
        }
        map.put("message", "회원정보 수정 실패");
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}/detail")
	public ResponseEntity<Map<String,String>> updateInfo(@PathVariable("id") String id,@RequestBody UpdateUserRequestDTO dto){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(service.updateUser(dto, Integer.parseInt(id))){
			 UserDTO user = service.getUserById(Integer.parseInt(id));
	            map.put("message", "회원정보 수정 성공");
	            map.put("token",service.login(new LoginUserRequestDTO(user.getEmail(), user.getPassword())));
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		}
		map.put("message", "회원정보 수정 실패");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,String>> deleteUser(@RequestBody DeleteUserRequestDTO dto, @PathVariable("id") String id){
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if(service.deleteUser(dto, Integer.parseInt(id))) {
				map.put("message", "삭제 성공");
				return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
			}
		}catch(UsernameNotFoundException e) {
			e.printStackTrace();
			map.put("message", "삭제 실패");
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
		}
		map.put("message", "잘못된 password");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
		
	}
	
	@PostMapping("/{user}/bookmark/{conf}")
	public ResponseEntity<Map<String,String>> addBookmark(@PathVariable("user") String userid, @PathVariable("conf") String confid){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(bookmarkService.createBookmark(Integer.parseInt(userid), Integer.parseInt(confid))) {
			map.put("message", "북마크 추가 성공");
		}else {
			map.put("message", "북마크 추가 실패");
		}
		
		
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/{user}/bookmark/{bookmark}")
	public ResponseEntity<Map<String,String>> deleteBookmark(@PathVariable("user") String userid, @PathVariable("bookmark") String bk_id){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(bookmarkService.deleteBookmark(Integer.parseInt(bk_id))) {
			map.put("message", "북마크 삭제 성공");
		}else {
			map.put("message", "북마크 삭제 실패");
		}
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@GetMapping("/{user}/bookmark")
	public ResponseEntity<List<BookmarkResDTO>> getBookmarkList(@PathVariable("user") String userid){
		
		List<BookmarkResDTO> list = bookmarkService.getBookmarks(Integer.parseInt(userid));
		
		return new ResponseEntity<List<BookmarkResDTO>>(list, HttpStatus.OK);
	}
	
}
