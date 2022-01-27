package com.ssafy.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.ConferenceDTO;
import com.ssafy.db.entity.Book;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ConferenceRepository;

@Service
public class ConferenceService {
	
	private ConferenceRepository repo;
	
	@Autowired
	public ConferenceService(ConferenceRepository repo) {
		this.repo = repo;
	}
	
	public List<Conference> getAllConf(){
		return repo.findAll();
	}
	
	public boolean createConference(Conference Conference) {
		try {
			repo.save(Conference);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Conference getConferenceById(int id) {
		Conference result = null;
		try {
			result = repo.findById(id).get();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<Conference> findByTitle(String title){
		try {
			return repo.findByTitleContaining(title);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Conference> findByBook(Book book){
		try {
			return repo.findByBook(book);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Conference> findByUser(User user){
		try {
			return repo.findByUser(user);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Conference> findByTags(String tags){
		try {
			return repo.findByTagsContaining(tags);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean updateConference(Conference c) {
		try {
			Conference output = getConferenceById(c.getId());
			if(output == null) return false;
			output = UpdateEntity(output, c);
			repo.save(output);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteConference(int id) {
		try {
			Conference Conference = getConferenceById(id);
			repo.delete(Conference);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Conference Dto2Entity(ConferenceDTO data, User user, Book book) {
		Conference entity = new Conference();
		entity.setBook(book);
		entity.setUser(user);
		entity.setCall_end_time(data.getCall_end_time());
		entity.setCall_start_time(data.getCall_end_time());
		entity.setThumbnail_url(data.getThumbnail_url());
		entity.setDescription(data.getDescription());
		entity.setMax_members(data.getMax_members());
		entity.setPassword(data.getPassword());
		entity.setQuestion(data.getQuestion());
		entity.setTags(data.getTags());
		entity.setTitle(data.getTitle());
		
		return entity;
	}
	
	public ConferenceDTO Entity2Dto(Conference data) {
		ConferenceDTO dto = new ConferenceDTO();
		
		dto.setId(data.getId());
		dto.setBook_id(data.getBook().getId());
		dto.setUser_id(data.getUser().getId());
		dto.setCall_end_time(data.getCall_end_time());
		dto.setCall_start_time(data.getCall_end_time());
		dto.setThumbnail_url(data.getThumbnail_url());
		dto.setDescription(data.getDescription());
		dto.setMax_members(data.getMax_members());
		dto.setPassword(data.getPassword());
		dto.setQuestion(data.getQuestion());
		dto.setTags(data.getTags());
		dto.setTitle(data.getTitle());
		
		return dto;
	}
	
	public Conference UpdateEntity(Conference target, Conference data) {
		target.setBook(data.getBook());
		target.setUser(data.getUser());
		target.setCall_end_time(data.getCall_end_time());
		target.setCall_start_time(data.getCall_end_time());
		target.setThumbnail_url(data.getThumbnail_url());
		target.setDescription(data.getDescription());
		target.setMax_members(data.getMax_members());
		target.setPassword(data.getPassword());
		target.setQuestion(data.getQuestion());
		target.setTags(data.getTags());
		target.setTitle(data.getTitle());
		
		return target;
	}
}
