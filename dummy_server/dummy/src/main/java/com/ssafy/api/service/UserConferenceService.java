package com.ssafy.api.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.UserConferenceDTO;
import com.ssafy.db.entity.UserConference;
import com.ssafy.db.repository.UserConferenceRepository;

@Service
public class UserConferenceService {
	
	private UserConferenceRepository userConferenceRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public UserConferenceService(UserConferenceRepository userConferenceRepository) {
		this.userConferenceRepository = userConferenceRepository;
		this.modelMapper = new ModelMapper();
	}
	
	public boolean createUserConference(UserConferenceDTO userConferenceDTO) {
		try {
			userConferenceRepository.save(modelMapper.map(userConferenceDTO, UserConference.class));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public UserConferenceDTO getUserConferenceById(int id) {
		try {
			UserConference source = userConferenceRepository.findById(id).get();
			return modelMapper.map(source, UserConferenceDTO.class);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean updateUserConference(UserConferenceDTO userConferenceDTO) {
		try {
			UserConference output = modelMapper.map(userConferenceDTO, UserConference.class);
			userConferenceRepository.save(output);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUserConference(int id) {
		try {
			UserConference UserConference = userConferenceRepository.getById(id);
			userConferenceRepository.delete(UserConference);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
