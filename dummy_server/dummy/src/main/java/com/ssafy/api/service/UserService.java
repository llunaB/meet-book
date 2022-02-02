package com.ssafy.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.UserDTO;
import com.ssafy.api.requestDto.DeleteUserReq;
import com.ssafy.api.requestDto.LoginReq;
import com.ssafy.api.requestDto.UpdateUserByProfileReq;
import com.ssafy.api.requestDto.UpdateUserByDetailReq;
import com.ssafy.config.JwtTokenProvider;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;

@Slf4j
@Service
public class UserService {
	
	private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	private ModelMapper modelMapper;
	
	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.modelMapper = new ModelMapper();
	}
	
	public boolean createUser(UserDTO userDto) {
		try {
			User entity = modelMapper.map(userDto, User.class);
			userRepository.save(entity);
			return true;
		} catch(Exception e) {
			log.info("create user failed");
			return false;
		}
	}
	
	//Login 데이터를 받고, JWT를 반환하는 메소드
	public String login(LoginReq data) {
		User user = userRepository.findByEmail(data.getEmail()).orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다.") );
		if(comparePassword(data.getPassword(), user.getPassword())) {
			return jwtTokenProvider.createToken(user.getUsername(),user.getRoles());
		}
		
		return "";
	}
	
	public UserDTO getUserById(int id) {
		try {
			User source = userRepository.findById(id).get();
			return modelMapper.map(source, UserDTO.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public UserDTO getUserByEmail(String email) throws UsernameNotFoundException {
		try {
			User source = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
			return modelMapper.map(source, UserDTO.class);
		}catch(UsernameNotFoundException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	//구 matchPassword
	//생 비밀번호와, 암호화된 비밀번호를 입력받고, 두 비밀번호의 동일 여부를 반환
	private boolean comparePassword(String rawPassword, String encryptPassword) {
        return passwordEncoder.matches(rawPassword,encryptPassword);
    }
	
	public List<UserDTO> getUsersByNickname(String nickname) {
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			List<User> dataList = userRepository.findByNicknameContaining(nickname);
			list = dataList.stream().map(source -> {
				UserDTO res = modelMapper.map(source, UserDTO.class);
				return res;
			}).collect(Collectors.toList());
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean updateUserByProfile(UpdateUserByProfileReq data, int id) {
		try {
			User entity = userRepository.getById(id);
			if(entity == null) return false;
			entity = updateEntityByProfile(entity, data);
			userRepository.save(entity);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateUserByDetail(UpdateUserByDetailReq data, int id) {
		try {
			User entity = userRepository.getById(id);
			if(entity == null) return false;
			entity = updateEntityByUserInfo(entity, data);
			userRepository.save(entity);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUser(DeleteUserReq data, int id) {
		
		User entity = userRepository.getById(id) ;
		
		if(!comparePassword(data.getPassword(), entity.getPassword())) return false;
		
		try {
			userRepository.delete(entity);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	private User updateEntityByProfile(User entity, UpdateUserByProfileReq data) {
		
		entity.setNickname(data.getNickname());
		entity.setProfileDescription(data.getProfileDescription());
		entity.setProfileImage(data.getProfileImage());
		
		return entity;
	}
	
	private User updateEntityByUserInfo(User entity, UpdateUserByDetailReq data) {
		entity.setPassword(data.getNewPassword());
		return entity;
	}
	
	private User updateUserEntity(User entity, User data) {
		
		entity.setPassword(data.getPassword());
		entity.setNickname(data.getNickname());
		entity.setEmail(data.getEmail());
		entity.setGender(data.getGender());
		entity.setAge(data.getAge());
		entity.setProfileImage(data.getProfileImage());
		entity.setProfileDescription(data.getProfileDescription());
		entity.setHostPoint(data.getHostPoint());
		entity.setGuestPoint(data.getGuestPoint());
        //entity.setRoles(data.getRoles());
		
		return entity;
	}
}
