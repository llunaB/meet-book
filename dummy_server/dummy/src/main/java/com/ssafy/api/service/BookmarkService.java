package com.ssafy.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Bookmark;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.BookmarkRepository;

@Service
public class BookmarkService {
	
	private BookmarkRepository repo;
	
	@Autowired
	public BookmarkService(BookmarkRepository repo) {
		this.repo = repo;
	}
	
	public boolean createBookmark(Bookmark Bookmark) {
		try {
			repo.save(Bookmark);
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
	
	public List<Bookmark> getBookmarks(User user) {
		List<Bookmark> result = null;
		try {
			result = repo.findByUser(user);
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
