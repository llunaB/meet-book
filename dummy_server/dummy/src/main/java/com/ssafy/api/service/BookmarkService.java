package com.ssafy.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.BookmarkDTO;
import com.ssafy.api.responseDto.GetBookmarksRes;
import com.ssafy.db.entity.Bookmark;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.BookmarkRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;

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
	
	public int createBookmark(int userId, int conferenceId) {
		try {
			Bookmark bookmark = new Bookmark();
			User user = userRepository.getById(userId);
			Conference conf = conferenceRepository.getById(conferenceId);

			bookmark.setAlarm(1);
			bookmark.setConference(conf);
			bookmark.setUser(user);

			bookmarkRepository.save(bookmark);
			bookmarkRepository.flush();
			return bookmarkRepository.findByUserAndConference(user, conf).get(0).getId();
		} catch (Exception e) {
			return -1;
		}
	}
	
	public GetBookmarksRes getBookmarkById(int id) {
		Bookmark source = bookmarkRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return modelmapper.map(source, GetBookmarksRes.class);
	}
	
	//check user have bookmark of conference
	public int checkUserHaveBookmark(int uid, int cid) {
		try {
			return bookmarkRepository.findByUserAndConference(userRepository.findById(uid).get(), conferenceRepository.findById(cid).get()).get(0).getId();
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		
	}
	
	public List<GetBookmarksRes> getBookmarks(int id) {
		List<GetBookmarksRes> list = new ArrayList<GetBookmarksRes>();
		User user = userRepository.getById(id);

		try {
			list = bookmarkRepository.findByUser(user).stream().map(source -> {
				GetBookmarksRes res = modelmapper.map(source, GetBookmarksRes.class);
			    return res;
			}).collect(Collectors.toList());
		} catch(Exception e){
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
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
