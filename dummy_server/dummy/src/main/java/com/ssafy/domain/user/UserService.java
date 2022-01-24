package com.ssafy.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
			result = repo.getOne(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean updateUser(User user) {
		try {
			User output = getUserById(user.getId());
			if(output == null) return false;
			output.update(user);
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
}
