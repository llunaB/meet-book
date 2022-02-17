package com.ssafy.api.service;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import com.ssafy.api.responseDto.GetSimpleBooksRes;

import lombok.extern.slf4j.Slf4j;
import com.ssafy.api.responseDto.GetUserByProfileRes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.ConferenceDTO;
import com.ssafy.DTO.ConferenceHistoryDTO;
import com.ssafy.api.responseDto.GetConferencesRes;
import com.ssafy.db.entity.Book;
import com.ssafy.db.entity.Bookmark;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceHistory;
import com.ssafy.db.entity.User;
import com.ssafy.db.mapping.ConferenceOnly;
import com.ssafy.db.repository.BookRepository;
import com.ssafy.db.repository.BookmarkRepository;
import com.ssafy.db.repository.ConferenceHistoryRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.UserConferenceRepository;
import com.ssafy.db.repository.UserRepository;

import javax.swing.text.html.Option;

@Slf4j
@Service
public class ConferenceService {
	
	private ConferenceRepository conferenceRepository;
	private UserRepository userRepository;
	private BookRepository bookRepository;
	private BookmarkRepository bookmarkRepository;
	
	private UserConferenceRepository userConferenceRepository;
	private ConferenceHistoryRepository conferenceHistoryRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public ConferenceService(ConferenceRepository conferenceRepository, UserRepository userRepository, BookRepository bookRepository, UserConferenceRepository userConferenceRepository, ConferenceHistoryRepository conferenceHistoryRepository, BookmarkRepository bookmarkRepository) {
		this.conferenceRepository = conferenceRepository;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
		this.userConferenceRepository = userConferenceRepository;
		this.conferenceHistoryRepository = conferenceHistoryRepository;
		this.bookmarkRepository = bookmarkRepository;
		this.modelMapper = new ModelMapper();
	}
	
	public List<ConferenceDTO> getConferences(){
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();
		
		try {
			list = conferenceRepository.findAll().stream().map(source -> modelMapper.map(source, ConferenceDTO.class)).collect(Collectors.toList());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<GetUserByProfileRes> getUsersByBookmarkConference(int conferenceId){
		List<GetUserByProfileRes> list = new ArrayList<GetUserByProfileRes>();
		
		try {
			List<Bookmark> data = bookmarkRepository.findByConferenceId(conferenceId);
			list = data.stream().map(source -> modelMapper.map(source.getUser(), GetUserByProfileRes.class)).collect(Collectors.toList());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Page<GetConferencesRes> getConferences(Pageable pageable, User user){
		Page<GetConferencesRes> list = Page.empty();
		
		try {
			Page<Conference> data = conferenceRepository.findAll(pageable);
			list = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source, GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source).size() > 0);
				return result;});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	public Page<GetConferencesRes> getConferencesById(int userid, User user, Pageable pageable) {
		Page<GetConferencesRes> list =Page.empty();
		try {
			Page<Conference> data = conferenceRepository.findById(userid, pageable);
			list = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source, GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source).size() > 0);
				return result;});
		}catch (Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean createConference(ConferenceDTO source) {
		try {
			Conference conference = conferenceRepository.save(modelMapper.map(source, Conference.class));
			Bookmark bookmark = new Bookmark();
			
			bookmark.setAlarm(1);
			bookmark.setConference(conference);
			bookmark.setUser(conference.getUser());
			bookmarkRepository.save(bookmark);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public GetConferencesRes getConferenceById(Integer id) {
		try {
			Conference source = conferenceRepository.findById(id).get();
			return modelMapper.map(source, GetConferencesRes.class); 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Page<GetConferencesRes> getConferencesByTitle(String title, User user, Pageable pageable){
		Page<GetConferencesRes> list = Page.empty();
		try {
			Page<Conference> data = conferenceRepository.findByTitleContaining(title, pageable);
			list = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source, GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source).size() > 0);
				return result;});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Page<GetConferencesRes> getConferencesByBook(String bookname, User user, Pageable pageable){
		Page<GetConferencesRes> list = Page.empty();
		try {
			Page<Conference> data = conferenceRepository.findConferencesByBookBookNameContaining(bookname, pageable);
			list = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source, GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source).size() > 0);
				return result;});

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	public Page<GetConferencesRes> getFinishedConferencesByBookId(Integer book_id, User user, Pageable pageable){
		Page<GetConferencesRes> page = Page.empty();
		try {
			Page<Conference> data = conferenceRepository.findFinishedConferencesByBookId(book_id, pageable);
			page = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source, GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source).size() > 0);
				return result;});

		}catch(Exception e){
			e.printStackTrace();
		}

		return page;
	}

	public Page<GetConferencesRes> getExpectingConferencesByBookId(Integer book_id, User user, Pageable pageable){
		Page<GetConferencesRes> page = Page.empty();
		try {
			Page<Conference> data = conferenceRepository.findExpectingConferencesByBookId(book_id, pageable);
			page = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source, GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source).size() > 0);
				return result;});

		}catch(Exception e){
			e.printStackTrace();
		}

		return page;
	}
	
	public Page<GetConferencesRes> getConferencesByNicknameContaining(String nickname, User user, Pageable pageable){
		Page<GetConferencesRes> list = Page.empty();
		try {
			Page<Conference> data = conferenceRepository.findConferencesByUserNicknameContaining(nickname, pageable);
			list = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source, GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source).size() > 0);
				return result;});

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Page<GetConferencesRes> getConferencesByNickname(String nickname, User user, Pageable pageable){
		Page<GetConferencesRes> list = Page.empty();
		try {
			Page<Conference> data = conferenceRepository.findConferencesByUserNickname(nickname, pageable);
			list = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source, GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source).size() > 0);
				return result;});

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Page<GetConferencesRes> getConferencesByTags(String tags, User user, Pageable pageable){
		Page<GetConferencesRes> list = Page.empty();
		try {
			Page<Conference> data = conferenceRepository.findByTagsContaining(tags, pageable);
			list = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source, GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source).size() > 0);
				return result;});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Page<GetConferencesRes> getConferencesByGenre(String genre, User user, Pageable pageable){
		Page<GetConferencesRes> list = Page.empty();
		try {
			Page<Conference> data = conferenceRepository.findConferenceByBookGenreGenreOrderByCallStartTime(genre, pageable);
			list = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source, GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source).size() > 0);
				return result;});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Page<GetConferencesRes> getFinishedConferencesByGenre(String genre, User user, Pageable pageable){
		Page<GetConferencesRes> list = Page.empty();
		try {
			Page<Conference> data = conferenceRepository.findConferenceByBookGenreGenreAndCallEndTimeBeforeOrderByCallStartTime(genre, new Date(), pageable);
			list = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source, GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source).size() > 0);
				return result;});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Page<GetConferencesRes> getReservedConferencesByGenre(String genre, User user, Pageable pageable){
		Page<GetConferencesRes> list = Page.empty();
		try {
//<<<<<<< HEAD
//
//			Date date = new Date();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(date);
//			cal.add(Calendar.HOUR,9);
//			date = cal.getTime();
//			log.info("");
//			Page<Conference> data = conferenceRepository.findConferenceByBookGenreGenreAndCallEndTimeAfterOrderByCallStartTime(genre, date, pageable);
//			list = data.map(source -> modelMapper.map(source, GetConferencesRes.class));
//=======
			Page<Conference> data = conferenceRepository.findConferenceByBookGenreGenreAndCallEndTimeAfterOrderByCallStartTime(genre, new Date(), pageable);
			list = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source, GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source).size() > 0);
				return result;});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Page<GetConferencesRes> getJoinedConferencesByUser(int id, User user, Pageable pageable){
		Page<GetConferencesRes> list = Page.empty();
		try {
			Page<ConferenceOnly> data = conferenceHistoryRepository.findDistinctConferenceByUserIdAndActionOrderByConferenceCallStartTimeDesc(id, "JOIN", pageable);
			list = data.map(source ->{ 
				GetConferencesRes result = modelMapper.map(source.getConference(), GetConferencesRes.class);
				if(user != null) result.setBookmark(bookmarkRepository.findByUserAndConference(user, source.getConference()).size() > 0);
				return result;});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Long countConferenceByGenre(String genre) {
		Long count = 0L;
		
		try {
			count = conferenceRepository.countByBookGenreGenre(genre);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return count;
	}
	
	public boolean updateConference(ConferenceDTO conferenceDTO) {
		try {
			Conference output = conferenceRepository.getById(conferenceDTO.getId());
			if(output == null) return false;
			Conference data = modelMapper.map(conferenceDTO, Conference.class);
			output = updateConferenceEntity(output, data);
			conferenceRepository.save(output);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteConference(int id) {
		try {
			Conference Conference = conferenceRepository.getById(id);
			conferenceRepository.delete(Conference);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createSessionHistory(ConferenceHistoryDTO dto) {
		try {
			conferenceHistoryRepository.save(modelMapper.map(dto, ConferenceHistory.class));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Conference updateConferenceEntity(Conference target, Conference data) {
		target.setBook(data.getBook());
		target.setUser(data.getUser());
		target.setCallEndTime(data.getCallEndTime());
		target.setCallStartTime(data.getCallEndTime());
		target.setThumbnailUrl(data.getThumbnailUrl());
		target.setDescription(data.getDescription());
		target.setMaxMembers(data.getMaxMembers());
		target.setPassword(data.getPassword());
		target.setQuestion(data.getQuestion());
		target.setTags(data.getTags());
		target.setTitle(data.getTitle());
		
		return target;
	}


}
