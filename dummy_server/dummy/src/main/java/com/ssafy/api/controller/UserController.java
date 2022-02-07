package com.ssafy.api.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ssafy.api.requestDto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.ssafy.DTO.UserDTO;
import com.ssafy.api.responseDto.GetBookmarksRes;
import com.ssafy.api.responseDto.GetUserByDetailRes;
import com.ssafy.api.responseDto.GetUserByProfileRes;
import com.ssafy.api.responseDto.MessageRes;
import com.ssafy.api.service.BookmarkService;
import com.ssafy.api.service.UserService;
import com.ssafy.error.exception.AlreadyExistEmailException;
import com.ssafy.error.exception.AlreadyExistNicknameException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	private UserService userService;
	private BookmarkService bookmarkService;

	@Autowired
	public UserController(UserService userService, BookmarkService bookmarkService) {
		this.userService = userService;
		this.bookmarkService = bookmarkService;
	}

	@PostMapping("/signup")
	public ResponseEntity<MessageRes> signUp(@Valid @RequestBody SignUpReq signUpReq) throws AlreadyExistEmailException, AlreadyExistNicknameException {
		MessageRes messageRes = new MessageRes();
		UserDTO userDto = new UserDTO(signUpReq);

		if (userService.createUser(userDto)) {
			messageRes.setMessage("유저생성 성공");
			messageRes.setData("user email : " + userDto.getEmail());
			return new ResponseEntity<MessageRes>(messageRes, HttpStatus.CREATED);
		}

		messageRes.setMessage("유저생성 실패");
		return new ResponseEntity<MessageRes>(messageRes, HttpStatus.BAD_REQUEST);
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

	//비밀번호찾기, 이메일만
	@PostMapping("/findpwd")
	public ResponseEntity<MessageRes> getUser(@RequestBody FindPwdReq findPwdReq){
		HashMap<String, String> map = new HashMap<String, String>();
		MessageRes messageRes = new MessageRes();
		UserDTO user = null;
		try {
			user = userService.getUserByEmail(findPwdReq.getEmail());
			messageRes.setMessage("유저정보가 있습니다");
			map.put("email",user.getEmail());
			map.put("id",String.valueOf(user.getId()));
			messageRes.setData(map);
			return new ResponseEntity<MessageRes>(messageRes, HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			messageRes.setMessage("유저정보가 없습니다");
			messageRes.setData("user email : ");
			return new ResponseEntity<MessageRes>(messageRes, HttpStatus.BAD_REQUEST);
		}
	}

	//비밀번호찾기, 이메일만
	@PostMapping("/findpwd/anotherway")
	public ResponseEntity<MessageRes> getUserByEmail(@RequestBody FindPwdReq findPwdReq, HttpServletResponse response){
		HashMap<String, String> map = new HashMap<String, String>();
		MessageRes messageRes = new MessageRes();
		UserDTO user = null;
		try {
			user = userService.getUserByEmail(findPwdReq.getEmail());
			response.sendRedirect("/email/pwdcheck/"+user.getId()+"/"+user.getEmail());
		}catch(Exception e){
			e.printStackTrace();
			messageRes.setMessage("유저정보가 없습니다");
			messageRes.setData("user email : ");
		}
		return new ResponseEntity<MessageRes>(messageRes, HttpStatus.BAD_REQUEST);
	}

	//비밀번호 수정
	@PostMapping("/findpwd/modify/{id}")
	public ResponseEntity<Map<String,String>> updateUserByPwd(@PathVariable("id") String id, @RequestBody Map<String,String> object){
		UpdateUserByDetailReq updateUserByDetailReq = new UpdateUserByDetailReq();
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			updateUserByDetailReq.setNewPassword(object.get("password"));
			if(userService.updateUserByDetail(updateUserByDetailReq, Integer.parseInt(id))){
				UserDTO user = userService.getUserById(Integer.parseInt(id));
				map.put("message", "비밀번호 수정 성공");
				return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("message", "회원정보 수정 실패");
		}
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GetUserByProfileRes> getUser (@PathVariable("id") String id) {
		UserDTO user = userService.getUserById(Integer.parseInt(id));
		return new ResponseEntity<GetUserByProfileRes>(new GetUserByProfileRes(user), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/detail")
	public ResponseEntity<GetUserByDetailRes> getUserDetail(@PathVariable("id") String id) {
		UserDTO user = userService.getUserById(Integer.parseInt(id));
		return new ResponseEntity<GetUserByDetailRes>(new GetUserByDetailRes(user), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String,String>> updateUserByProfile(@PathVariable("id") String id, @RequestBody UpdateUserByProfileReq updateProfileRequestDto){
		
		HashMap<String, String> map = new HashMap<String, String>();

		 if(userService.updateUserByProfile(updateProfileRequestDto, Integer.parseInt(id))){
            UserDTO user = userService.getUserById(Integer.parseInt(id));
            map.put("message", "회원정보 수정 성공");
            map.put("token", userService.login(new LoginReq(user.getEmail(), user.getPassword())));
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

		if (userService.deleteUser(deleteUserReq, Integer.parseInt(id))) {
			map.put("message", "삭제 성공");
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		} else {
			map.put("message", "잘못된 password");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/{user}/bookmark/{conference}")
	public ResponseEntity<Map<String,String>> createBookmark(@PathVariable("user") String userId, @PathVariable("conference") String conferenceId){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if (bookmarkService.createBookmark(Integer.parseInt(userId), Integer.parseInt(conferenceId))) {
			map.put("message", "북마크 추가 성공");
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		} else {
			map.put("message", "북마크 추가 실패");
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
		}
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
