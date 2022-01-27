package com.ssafy.api.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.UserDTO;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository repo;
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
		this.repo = repo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public boolean createUser(User user) {
		try {
			repo.save(user);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public User getUserById(int id) {
		User result = null;
		try {
			result = repo.findById(id).get();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean matchPassword(String password, String password1) {
        return passwordEncoder.matches(password,password1);
    }
	
	public List<User> getUserByNickname(String nickname) {
		try {
			return repo.findByNicknameContaining(nickname);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	public User getUserByEmail(String email) throws UsernameNotFoundException {
		return repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}
	
	public boolean updateUser(User user) {
		try {
			User output = getUserByEmail(user.getEmail());
			if(output == null) return false;
			output = UpdateEntity(output, user);
			repo.save(output);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUser(User user, String password) {
		
		if(!matchPassword(password, user.getPassword())) return false;
		
		try {
			repo.delete(user);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public User Dto2Entity(UserDTO data) {
		User entity = new User();
		
		entity.setName(data.getName());
		entity.setPassword(passwordEncoder.encode(data.getPassword()));
		entity.setNickname(data.getNickname());
		entity.setEmail(data.getEmail());
		entity.setGender(data.getGender());
		entity.setAge(data.getAge());
		entity.setProfile_image(data.getProfile_image());
		entity.setProfile_description(data.getProfile_description());
		entity.setHost_point(data.getHost_point());
		entity.setGuest_point(data.getGuest_point());
        entity.setRoles(Collections.singletonList("ROLE_USER"));
		
		return entity;
	}
	
	public UserDTO Entity2Dto(User data) {
		UserDTO dto = new UserDTO();
		
		dto.setId(data.getId());
		dto.setName(data.getName());
		dto.setPassword(data.getPassword());
		dto.setNickname(data.getNickname());
		dto.setEmail(data.getEmail());
		dto.setGender(data.getGender());
		dto.setAge(data.getAge());
		dto.setProfile_image(data.getProfile_image());
		dto.setProfile_description(data.getProfile_description());
		dto.setHost_point(data.getHost_point());
		dto.setGuest_point(data.getGuest_point());
		
		return dto;
	}
	
	public User UpdateEntity(User entity, User data) {
		
		entity.setName(data.getName());
		entity.setPassword(data.getPassword());
		entity.setNickname(data.getNickname());
		entity.setEmail(data.getEmail());
		entity.setGender(data.getGender());
		entity.setAge(data.getAge());
		entity.setProfile_image(data.getProfile_image());
		entity.setProfile_description(data.getProfile_description());
		entity.setHost_point(data.getHost_point());
		entity.setGuest_point(data.getGuest_point());
        //entity.setRoles(data.getRoles());
		
		return entity;
	}
}
