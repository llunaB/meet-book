package com.ssafy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Genre;
import com.ssafy.db.repository.GenreRepository;

@Service
public class GenreService {
	
	private GenreRepository repo;
	
	@Autowired
	public GenreService(GenreRepository repo) {
		this.repo = repo;
	}
	
	public boolean createUser(Genre genre) {
		try {
			repo.save(genre);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Genre getUserById(int id) {
		Genre result = null;
		try {
			result = repo.findById(id).get();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean updateUser(int id, String genre) {
		try {
			Genre output = getUserById(id);
			if(output == null) return false;
			output.setGenre(genre);;
			repo.save(output);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUser(int id) {
		try {
			Genre user = getUserById(id);
			repo.delete(user);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
