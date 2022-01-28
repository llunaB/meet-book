package com.ssafy.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.api.response.BookmarkResDTO;
import com.ssafy.db.entity.Bookmark;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.BookmarkRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.UserRepository;

@Service
public class BookmarkService {
	
	private BookmarkRepository repo;
	private UserRepository uRepo;
	private ConferenceRepository cRepo;
	
	@Autowired
	public BookmarkService(BookmarkRepository repo, UserRepository uRepo, ConferenceRepository cRepo) {
		this.repo = repo;
		this.uRepo = uRepo;
		this.cRepo = cRepo;
	}
	
	public boolean createBookmark(int userId, int conferenceId) {
		try {
			
			Bookmark bookmark = new Bookmark();
			User user = uRepo.getById(userId);
			Conference conf = cRepo.getById(conferenceId);
			
			bookmark.setAlarm(1);
			bookmark.setConference(conf);
			bookmark.setUser(user);
			
			repo.save(bookmark);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Bookmark getBookmarkById(int id) {
		Bookmark result = null;
		try {
			result = repo.findById(id).get();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<BookmarkResDTO> getBookmarks(int userId) {
		List<BookmarkResDTO> result = null;
		User user = uRepo.getById(userId);
		try {
			result = repo.findByUser(user).stream().map(objA -> {
				BookmarkResDTO objB = new BookmarkResDTO(objA);
			    return objB;
			}).collect(Collectors.toList());;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean updateBookmark(int id) {
		try {
			Bookmark output = getBookmarkById(id);
			if(output == null) return false;
			if(output.getAlarm() == 0) {
				output.setAlarm(1);
			}else {
				output.setAlarm(0);
			}
			repo.save(output);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteBookmark(int id) {
		try {
			Bookmark Bookmark = getBookmarkById(id);
			repo.delete(Bookmark);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
