package com.ssafy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.User_conference;
import com.ssafy.db.repository.User_conferenceRepository;

@Service
public class User_conferenceService {
	
	private User_conferenceRepository repo;
	
	@Autowired
	public User_conferenceService(User_conferenceRepository repo) {
		this.repo = repo;
	}
	
	public boolean createUser_conference(User_conference User_conference) {
		try {
			repo.save(User_conference);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public User_conference getUser_conferenceById(int id) {
		User_conference result = null;
		try {
			result = repo.findById(id).get();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
	
	public boolean updateUser_conference(User_conference User_conference) {
		try {
			User_conference output = getUser_conferenceById(User_conference.getId());
			output.setAuthority(User_conference.getAuthority());
			output.setConference(User_conference.getConference());
			output.setUser(User_conference.getUser());
			repo.save(output);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUser_conference(int id) {
		try {
			User_conference User_conference = getUser_conferenceById(id);
			repo.delete(User_conference);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
