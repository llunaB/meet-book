package com.ssafy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.UserDTO;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository repo;
	
	@Autowired
	public UserService(UserRepository repo) {
		this.repo = repo;
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
	
	public User getUserByeEmail(String email) {
		User result = null;
		try {
			result = repo.findByEmail(email).get();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean updateUser(User user) {
		try {
			User output = getUserById(user.getId());
			if(output == null) return false;
			output = UpdateEntity(output, user);
			repo.save(output);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUser(int id) {
		try {
			User user = getUserById(id);
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
		entity.setPassword(data.getPassword());
		entity.setNickname(data.getNickname());
		entity.setEmail(data.getEmail());
		entity.setGender(data.getGender());
		entity.setAge(data.getAge());
		entity.setProfile_image(data.getProfile_image());
		entity.setProfile_description(data.getProfile_description());
		entity.setHost_point(data.getHost_point());
		entity.setGuest_point(data.getGuest_point());
		
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
		
		return entity;
	}
}
