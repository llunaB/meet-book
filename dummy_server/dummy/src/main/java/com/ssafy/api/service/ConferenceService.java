package com.ssafy.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.ConferenceDTO;
import com.ssafy.db.entity.Book;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.BookRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.UserRepository;

@Service
public class ConferenceService {
	
	private ConferenceRepository conferenceRepository;
	private UserRepository userRepository;
	private BookRepository bookRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public ConferenceService(ConferenceRepository conferenceRepository, UserRepository userRepository, BookRepository bookRepository) {
		this.conferenceRepository = conferenceRepository;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
		this.modelMapper = new ModelMapper();
	}
	
	public List<ConferenceDTO> getConferences(){
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();
		
		try {
			list = conferenceRepository.findAll().stream().map(source -> {
				ConferenceDTO res = modelMapper.map(source, ConferenceDTO.class);
			    return res;
			}).collect(Collectors.toList());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean createConference(ConferenceDTO source) {
		try {
			conferenceRepository.save(modelMapper.map(source, Conference.class));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public ConferenceDTO getConferenceById(int id) {
		try {
			Conference source = conferenceRepository.findById(id).get();
			return modelMapper.map(source, ConferenceDTO.class); 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<ConferenceDTO> getConferencesByTitle(String title){
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();
		try {
			list = conferenceRepository.findByTitleContaining(title).stream().map(source -> {
				ConferenceDTO res = modelMapper.map(source, ConferenceDTO.class);
				return res;
			}).collect(Collectors.toList());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<ConferenceDTO> getConferencesByBook(String bookname){
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();
		try {
			List<Book> bookList = bookRepository.findByBookNameContaining(bookname);
			list.addAll(conferenceRepository.findConferencesByBook(bookList).stream().map(source -> {
				ConferenceDTO res = modelMapper.map(source, ConferenceDTO.class);
				return res;
			}).collect(Collectors.toList()));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<ConferenceDTO> getConferencesByNickname(String nickname){
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();
		try {
			List<User> userList = userRepository.findByNicknameContaining(nickname);
			list.addAll(conferenceRepository.findConferencesByUser(userList).stream().map(source -> {
				ConferenceDTO res = modelMapper.map(source, ConferenceDTO.class);
				return res;
			}).collect(Collectors.toList()));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<ConferenceDTO> getConferencesByTags(String tags){
		List<ConferenceDTO> list = new ArrayList<ConferenceDTO>();
		try {
			list = conferenceRepository.findByTagsContaining(tags).stream().map(source -> {
				ConferenceDTO res = modelMapper.map(source, ConferenceDTO.class);
				return res;
			}).collect(Collectors.toList());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
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
