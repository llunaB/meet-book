package com.ssafy.api.controller;

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
import com.ssafy.api.requestDto.DeleteUserReq;
import com.ssafy.api.requestDto.LoginReq;
import com.ssafy.api.requestDto.SignUpReq;
import com.ssafy.api.requestDto.UpdateUserByProfileReq;
import com.ssafy.api.requestDto.UpdateUserByDetailReq;
import com.ssafy.api.responseDto.GetBookmarksRes;
import com.ssafy.api.responseDto.MessageRes;
import com.ssafy.api.responseDto.GetUserByDetailRes;
import com.ssafy.api.responseDto.GetUserByProfileRes;
import com.ssafy.api.service.BookmarkService;
import com.ssafy.api.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	private UserService userService;
	private BookmarkService bookmarkService;
	
	@Autowired
	public UserController(UserService userService, BookmarkService bookmarkService) {
		this.userService = userService;
		this.bookmarkService = bookmarkService;
	}
	
	//유저 정보를 입력받고, User를 생성 및 DB에 저장
	@PostMapping("/signup")
	public ResponseEntity<MessageRes> signUp(@RequestBody SignUpReq signUpReq
			){
		MessageRes map = new MessageRes();
		if(userService.createUser(new UserDTO(signUpReq))) {
			System.out.println("user created");
			map.setMessage("회원가입 성공");

		}else {
			System.out.println("fail user created");
			map.setMessage("회원가입 실패");

		}
		return new ResponseEntity<MessageRes>(map, HttpStatus.OK);
	}
	
	//아이디와 비밀번호를 입력받고, JWT 토큰 및 유저 정보를 반환
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginReq loginReq){
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			String token = userService.login(loginReq);
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
	public ResponseEntity<GetUserByProfileRes> getUser(@PathVariable("id") String id){
		UserDTO user = userService.getUserById(Integer.parseInt(id));
		return new ResponseEntity<GetUserByProfileRes>(new GetUserByProfileRes(user), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/detail")
	public ResponseEntity<GetUserByDetailRes> getUserDetail(@PathVariable("id") String id){
		UserDTO user = userService.getUserById(Integer.parseInt(id));
		return new ResponseEntity<GetUserByDetailRes>(new GetUserByDetailRes(user), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String,String>> updateUserByProfile(@PathVariable("id") String id, @RequestBody UpdateUserByProfileReq updateProfileRequestDto){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		
		 if(userService.updateUserByProfile(updateProfileRequestDto, Integer.parseInt(id))){
            UserDTO user = userService.getUserById(Integer.parseInt(id));
            map.put("message", "회원정보 수정 성공");
            map.put("token",userService.login(new LoginReq(user.getEmail(), user.getPassword())));
            return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
        }
        map.put("message", "회원정보 수정 실패");
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}/detail")
	public ResponseEntity<Map<String,String>> updateUserByDetail(@PathVariable("id") String id,@RequestBody UpdateUserByDetailReq updateUserByDetailReq){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(userService.updateUserByDetail(updateUserByDetailReq, Integer.parseInt(id))){
			 UserDTO user = userService.getUserById(Integer.parseInt(id));
	            map.put("message", "회원정보 수정 성공");
	            map.put("token",userService.login(new LoginReq(user.getEmail(), user.getPassword())));
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		}
		map.put("message", "회원정보 수정 실패");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,String>> deleteUser(@RequestBody DeleteUserReq deleteUserReq, @PathVariable("id") String id){
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if(userService.deleteUser(deleteUserReq, Integer.parseInt(id))) {
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
	
	@PostMapping("/{user}/bookmark/{conference}")
	public ResponseEntity<Map<String,String>> createBookmark(@PathVariable("user") String userId, @PathVariable("conference") String conferenceId){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(bookmarkService.createBookmark(Integer.parseInt(userId), Integer.parseInt(conferenceId))) {
			map.put("message", "북마크 추가 성공");
		}else {
			map.put("message", "북마크 추가 실패");
		}
		
		
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/{user}/bookmark/{bookmark}")
	public ResponseEntity<Map<String,String>> deleteBookmark(@PathVariable("user") String userId, @PathVariable("bookmark") String bookmarkId){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(bookmarkService.deleteBookmark(Integer.parseInt(bookmarkId))) {
			map.put("message", "북마크 삭제 성공");
		}else {
			map.put("message", "북마크 삭제 실패");
		}
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@GetMapping("/{user}/bookmark")
	public ResponseEntity<List<GetBookmarksRes>> getBookmarks(@PathVariable("user") String userId){
		
		List<GetBookmarksRes> list = bookmarkService.getBookmarks(Integer.parseInt(userId));
		
		return new ResponseEntity<List<GetBookmarksRes>>(list, HttpStatus.OK);
	}
	
}
