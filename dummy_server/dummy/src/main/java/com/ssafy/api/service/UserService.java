package com.ssafy.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ssafy.DTO.UserDTO;
import com.ssafy.api.requestDto.DeleteUserReq;
import com.ssafy.api.requestDto.LoginReq;
import com.ssafy.api.requestDto.UpdateUserByDetailReq;
import com.ssafy.api.requestDto.UpdateUserByProfileReq;
import com.ssafy.config.JwtTokenProvider;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.error.exception.AlreadyExistEmailException;
import com.ssafy.error.exception.AlreadyExistNicknameException;

import lombok.extern.slf4j.Slf4j;

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
	
	public boolean createUser(UserDTO userDto) throws AlreadyExistEmailException, AlreadyExistNicknameException {

		String email = userDto.getEmail();
		Optional<User> userByEmail = userRepository.findByEmail(email);
		String nickname = userDto.getNickname();
		Optional<User> userByNickname = userRepository.findByNickname(nickname);

		if (userByEmail.isPresent()) {
			log.info("user email already exists");
			throw new AlreadyExistEmailException();
		}

		if (userByNickname.isPresent()) {
			log.info("user nickname already exists");
			throw new AlreadyExistNicknameException();
		}

		User entity = modelMapper.map(userDto, User.class);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		userRepository.save(entity);
		return true;
	}


	//Login 데이터를 받고, JWT를 반환하는 메소드
	public String login(LoginReq data) {
		User user = userRepository.findByEmail(data.getEmail()).orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다.") );
		if(comparePassword(data.getPassword(), user.getPassword())) {
			return jwtTokenProvider.createToken(user,user.getRoles());
		}
		
		return "";
	}


	public UserDTO getUserById(int id) {
		User source = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return modelMapper.map(source, UserDTO.class);
	}

	public UserDTO getUserByEmail(String email) {
		User source = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return modelMapper.map(source, UserDTO.class);
	}


	//구 matchPassword
	//생 비밀번호와, 암호화된 비밀번호를 입력받고, 두 비밀번호의 동일 여부를 반환
	private boolean comparePassword(String rawPassword, String encryptPassword) {
        return passwordEncoder.matches(rawPassword,encryptPassword);
    }
	
	public Page<UserDTO> getUsersByNickname(String nickname, Pageable pageable) {
		Page<UserDTO> list = Page.empty();
		try {
			Page<User> dataList = userRepository.findByNicknameContaining(nickname, pageable);
			list = dataList.map(source -> modelMapper.map(source, UserDTO.class));
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}


	
	public boolean updateUserByProfile(UpdateUserByProfileReq data, int id) {
		try {
			User entity = userRepository.getById(id);
			userRepository.save(updateEntityByProfile(entity, data));
		} catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean updateUserByDetail(UpdateUserByDetailReq data, int id) {
		try {
			User entity = userRepository.getById(id);
			if(entity == null) return false;
			entity = updateEntityByUserInfo(entity, data);
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
			userRepository.save(entity);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUser(DeleteUserReq data, int id) {
		
		User entity = userRepository.getById(id);
		
		if(!comparePassword(data.getPassword(), entity.getPassword())) return false;

		userRepository.delete(entity);
		return true;
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
