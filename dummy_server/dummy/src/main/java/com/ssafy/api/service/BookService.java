package com.ssafy.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.BookDTO;
import com.ssafy.db.entity.Book;
import com.ssafy.db.entity.Genre;
import com.ssafy.db.repository.BookRepository;

@Service
public class BookService {
	
	private final BookRepository repo;
	private final OpenApiService openApiService;

	@Autowired
	public BookService(BookRepository repo, OpenApiService openApiService) {
		this.repo = repo;
		this.openApiService = openApiService;
	}

	// BookController 에서 실행
	public boolean loadBookData() {
		try {
			List<Book> list = openApiService.loadBookData();
			repo.saveAll(list);
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
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
	
	public List<Book> getBookByName(String bookname) {
		try {
			return repo.findByBooknameContaining(bookname);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
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
		target.setBookname(data.getBookname());
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
		dto.setBookname(data.getBookname());
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
		entity.setBookname(data.getBookname());
		entity.setBook_pubdate(data.getBook_pubdate());
		entity.setBook_publisher(data.getBook_publisher());
		entity.setBook_thumbnail_url(data.getBook_thumbnail_url());
		entity.setGenre(genre);
		entity.setLoan_count(data.getLoan_count());
		
		return entity;
	}
}
