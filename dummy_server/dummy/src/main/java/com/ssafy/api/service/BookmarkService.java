package com.ssafy.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.BookmarkDTO;
import com.ssafy.api.responseDto.GetBookmarksRes;
import com.ssafy.db.entity.Bookmark;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.BookmarkRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.UserRepository;

@Service
public class BookmarkService {
	
	private BookmarkRepository bookmarkRepository;
	private UserRepository userRepository;
	private ConferenceRepository conferenceRepository;
	private ModelMapper modelmapper;
	
	@Autowired
	public BookmarkService(BookmarkRepository bookmarkRepository, UserRepository userRepository, ConferenceRepository conferenceRepository) {
		this.bookmarkRepository = bookmarkRepository;
		this.userRepository = userRepository;
		this.conferenceRepository = conferenceRepository;
		this.modelmapper = new ModelMapper();
	}
	
	public boolean createBookmark(int userId, int conferenceId) {
		try {
			
			Bookmark bookmark = new Bookmark();
			User user = userRepository.getById(userId);
			Conference conf = conferenceRepository.getById(conferenceId);
			
			bookmark.setAlarm(1);
			bookmark.setConference(conf);
			bookmark.setUser(user);
			
			bookmarkRepository.save(bookmark);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public GetBookmarksRes getBookmarkById(int id) {
		try {
			Bookmark source = bookmarkRepository.findById(id).get();
			if(source == null) return null;
			return modelmapper.map(source, GetBookmarksRes.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<GetBookmarksRes> getBookmarks(int id) {
		List<GetBookmarksRes> list = new ArrayList<GetBookmarksRes>();
		User user = userRepository.getById(id);
		try {
			list = bookmarkRepository.findByUser(user).stream().map(source -> {
				GetBookmarksRes res = modelmapper.map(source, GetBookmarksRes.class);
			    return res;
			}).collect(Collectors.toList());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean updateBookmark(int id) {
		try {
			Bookmark output = bookmarkRepository.getById(id);
			if(output == null) return false;
			if(output.getAlarm() == 0) {
				output.setAlarm(1);
			}else {
				output.setAlarm(0);
			}
			bookmarkRepository.save(output);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteBookmark(int id) {
		try {
			Bookmark Bookmark = bookmarkRepository.getById(id);
			bookmarkRepository.delete(Bookmark);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
