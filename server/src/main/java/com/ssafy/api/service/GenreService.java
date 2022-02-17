package com.ssafy.api.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.GenreDTO;
import com.ssafy.db.entity.Genre;
import com.ssafy.db.repository.GenreRepository;

@Service
public class GenreService {
	
	private GenreRepository genreRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public GenreService(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
		this.modelMapper = new ModelMapper();
	}
	
	public boolean createGenre(Genre genre) {
		try {
			genreRepository.save(genre);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public GenreDTO getGenreById(int id) {
		try {
			return modelMapper.map( genreRepository.findById(id).get(), GenreDTO.class );
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean updateGenre(int id, String genre) {
		try {
			Genre output = genreRepository.getById(id);
			if(output == null) return false;
			output.setGenre(genre);;
			genreRepository.save(output);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteGenre(int id) {
		try {
			Genre user = genreRepository.getById(id);
			genreRepository.delete(user);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
