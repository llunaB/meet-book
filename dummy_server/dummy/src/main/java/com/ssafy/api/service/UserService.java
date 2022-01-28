package com.ssafy.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.UserDTO;
import com.ssafy.api.request.DeleteUserRequestDTO;
import com.ssafy.api.request.LoginUserRequestDTO;
import com.ssafy.api.request.UpdateProfileRequestDTO;
import com.ssafy.api.request.UpdateUserRequestDTO;
import com.ssafy.config.JwtTokenProvider;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository repo;
    private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	public UserService(UserRepository repo, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.repo = repo;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	public boolean createUser(UserDTO user) {
		try {
			User entity = Dto2Entity(user);
			repo.save(entity);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public String login(LoginUserRequestDTO data) {
		User user = repo.findByEmail(data.getEmail()).orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다.") );
		if(matchPassword(data.getPassword(), user.getPassword())) {
			return jwtTokenProvider.createToken(user.getUsername(),user.getRoles());
		}
		
		return "";
	}
	
	public UserDTO getUserById(int id) {
		User data = null;
		try {
			data = repo.findById(id).get();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return Entity2Dto(data);
	}
	
	public UserDTO getUserByEmail(String email) throws UsernameNotFoundException {
		User data = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
		return Entity2Dto(data);
	}
	
	private boolean matchPassword(String password, String password1) {
        return passwordEncoder.matches(password,password1);
    }
	
	public List<UserDTO> getUserByNickname(String nickname) {
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			List<User> dataList = repo.findByNicknameContaining(nickname);
			for(User data : dataList) {
				list.add(Entity2Dto(data));
			}
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean updateProfile(UpdateProfileRequestDTO data, int id) {
		try {
			User entity = repo.getById(id);
			if(entity == null) return false;
			entity = UpdateProfile(entity, data);
			repo.save(entity);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateUser(UpdateUserRequestDTO data, int id) {
		try {
			User entity = repo.getById(id);
			if(entity == null) return false;
			entity = UpdateUser(entity, data);
			repo.save(entity);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUser(DeleteUserRequestDTO data, int id) {
		
		User entity = repo.getById(id) ;
		
		if(!matchPassword(data.getPassword(), entity.getPassword())) return false;
		
		try {
			repo.delete(entity);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public User Dto2Entity(UserDTO data) {
		User entity = new User();
		
		entity.setPassword(passwordEncoder.encode(data.getPassword()));
		entity.setNickname(data.getNickname());
		entity.setEmail(data.getEmail());
		entity.setGender(data.getGender());
		entity.setAge(data.getAge());
		entity.setProfileImage(data.getProfileImage());
		entity.setProfileDescription(data.getProfileDescription());
		entity.setHostPoint(data.getHostPoint());
		entity.setGuestPoint(data.getGuestPoint());
        entity.setRoles(Collections.singletonList("ROLE_USER"));
		
		return entity;
	}
	
	public UserDTO Entity2Dto(User data) {
		UserDTO dto = new UserDTO();
		
		dto.setId(data.getId());
		dto.setPassword(data.getPassword());
		dto.setNickname(data.getNickname());
		dto.setEmail(data.getEmail());
		dto.setGender(data.getGender());
		dto.setAge(data.getAge());
		dto.setProfileImage(data.getProfileImage());
		dto.setProfileDescription(data.getProfileDescription());
		dto.setHostPoint(data.getHostPoint());
		dto.setGuestPoint(data.getGuestPoint());
		
		return dto;
	}
	
	private User UpdateProfile(User entity, UpdateProfileRequestDTO data) {
		
		entity.setNickname(data.getNickname());
		entity.setProfileDescription(data.getProfileDescription());
		entity.setProfileImage(data.getProfileImage());
		
		return entity;
	}
	
	private User UpdateUser(User entity, UpdateUserRequestDTO data) {
		entity.setPassword(data.getNewPassword());
		return entity;
	}
	
	private User UpdateEntity(User entity, User data) {
		
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
