package com.ssafy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.BookDTO;
import com.ssafy.db.entity.Book;
import com.ssafy.db.entity.Genre;
import com.ssafy.db.repository.BookRepository;

@Service
public class BookService {
	
	private BookRepository repo;
	
	@Autowired
	public BookService(BookRepository repo) {
		this.repo = repo;
	}
	
	public boolean createBook(Book Book) {
		try {
			repo.save(Book);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Book getBookById(int id) {
		Book result = null;
		try {
			result = repo.findById(id).get();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Book getBookByeIsbn(String isbn) {
		Book result = null;
		try {
			result = repo.findByIsbn(isbn).get();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean updateBook(Book b) {
		try {
			Book output = getBookById(b.getId());
			if(output == null) return false;
			output = UpdateEntity(output, b);
			repo.save(output);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteBook(int id) {
		try {
			Book Book = getBookById(id);
			repo.delete(Book);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Book UpdateEntity(Book target, Book data) {
		target.setBook_author(data.getBook_author());
		target.setBook_contents(data.getBook_contents());
		target.setBook_name(data.getBook_name());
		target.setBook_pubdate(data.getBook_pubdate());
		target.setBook_publisher(data.getBook_publisher());
		target.setBook_thumbnail_url(data.getBook_thumbnail_url());
		target.setGenre(data.getGenre());
		
		return target;
	}
	
	public BookDTO Entity2Dto(Book data) {
		BookDTO dto = new BookDTO();
		
		dto.setId(data.getId());
		dto.setBook_author(data.getBook_author());
		dto.setBook_contents(data.getBook_contents());
		dto.setBook_name(data.getBook_name());
		dto.setBook_pubdate(data.getBook_pubdate());
		dto.setBook_publisher(data.getBook_publisher());
		dto.setBook_thumbnail_url(data.getBook_thumbnail_url());
		dto.setGenre_id(data.getGenre().getId());
		dto.setLoan_count(data.getLoan_count());
		
		return dto;
	}
	
	public Book Dto2Entity(BookDTO data, Genre genre) {
		Book entity = new Book();
		entity.setBook_author(data.getBook_author());
		entity.setBook_contents(data.getBook_contents());
		entity.setBook_name(data.getBook_name());
		entity.setBook_pubdate(data.getBook_pubdate());
		entity.setBook_publisher(data.getBook_publisher());
		entity.setBook_thumbnail_url(data.getBook_thumbnail_url());
		entity.setGenre(genre);
		entity.setLoan_count(data.getLoan_count());
		
		return entity;
	}
}
