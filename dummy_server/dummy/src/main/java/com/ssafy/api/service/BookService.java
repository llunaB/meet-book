package com.ssafy.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.BookDTO;
import com.ssafy.db.entity.Book;
import com.ssafy.db.entity.Genre;
import com.ssafy.db.openApi.OpenApiHelper;
import com.ssafy.db.repository.BookRepository;

@Service
public class BookService {
	
	private BookRepository bookRepository;
	private OpenApiHelper openApiHelper;
	private ModelMapper modelMapper;
	
	@Autowired
	public BookService(BookRepository bookRepository, OpenApiHelper openApiHelper) {
		this.bookRepository = bookRepository;
		this.openApiHelper = openApiHelper;
		this.modelMapper = new ModelMapper();
	}
	
	public boolean getBookData() {
		try {
			List<Book> list = openApiHelper.LoadBookData();
			
			bookRepository.saveAll(list);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createBook(Book book) {
		try {
			bookRepository.save(book);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public BookDTO getBookById(int id) {
		try {
			Book source = bookRepository.findById(id).get();
			return modelMapper.map(source, BookDTO.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public BookDTO getBookByIsbn(String isbn) {
		try {
			Book source = bookRepository.findByIsbn(isbn).get();
			return modelMapper.map(source, BookDTO.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<BookDTO> getBooksByName(String bookName) {
		List<BookDTO> list = new ArrayList<BookDTO>();
		try {
			list = bookRepository.findByBookNameContaining(bookName).stream().map(source -> {
				BookDTO res = modelMapper.map(source, BookDTO.class);
			    return res;
			}).collect(Collectors.toList());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean updateBook(BookDTO book) {
		try {
			Book output = bookRepository.getById(book.getId());
			if(output == null) return false;
			output = updateBookEntity(output, modelMapper.map(book, Book.class));
			bookRepository.save(output);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteBook(int id) {
		try {
			Book Book = bookRepository.getById(id);
			bookRepository.delete(Book);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Book updateBookEntity(Book target, Book data) {
		target.setBookAuthor(data.getBookAuthor());
		target.setBookContents(data.getBookContents());
		target.setBookName(data.getBookName());
		target.setBookPubYear(data.getBookPubYear());
		target.setBookPublisher(data.getBookPublisher());
		target.setBookThumbnailUrl(data.getBookThumbnailUrl());
		target.setGenre(data.getGenre());
		
		return target;
	}
}
