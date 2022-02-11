package com.ssafy.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.DTO.BookDTO;
import com.ssafy.api.responseDto.GetBookRes;
import com.ssafy.api.responseDto.GetSimpleBooksRes;
import com.ssafy.db.entity.Book;
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


	// 도서데이터 생성
	public boolean getBookData() {
		try {
			List<Book> list = openApiHelper.loadBookData();
			bookRepository.saveAll(list);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<GetBookRes> getBooks(){
		List<GetBookRes> bookDTOList = new ArrayList<>();

		try {
			List<Book> bookList = bookRepository.findAll();
			// Book List -> BookDTO List
			bookDTOList = bookList.stream().map(source -> {
				GetBookRes bookDTO = modelMapper.map(source, GetBookRes.class);
				return bookDTO;
			}).collect(Collectors.toList());

		}catch(Exception e){
			e.printStackTrace();
		}

		return bookDTOList;
	}

	public List<GetBookRes> getBooksOrderByLoanCount(){
		List<GetBookRes> bookDTOList = new ArrayList<>();

		try {
			List<Book> bookList = bookRepository.findAllOrderByLoanCountDesc();
			// Book List -> BookDTO List
			bookDTOList = bookList.stream().map(source -> {
				GetBookRes bookDTO = modelMapper.map(source, GetBookRes.class);
				return bookDTO;
			}).collect(Collectors.toList());

		}catch(Exception e){
			e.printStackTrace();
		}

		return bookDTOList;
	}
	
	public BookDTO getBookById(int id) {
		try {
			Book source = bookRepository.findById(id).get();
			System.out.println(source);
			// Book -> BookDTO
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
	
	public Page<GetBookRes> getBooksByName(String bookName, Pageable pageable) {
		Page<GetBookRes> list = Page.empty();
		try {
			Page<Book> data = bookRepository.findByBookNameContaining(bookName, pageable);
			list = data.map(source -> modelMapper.map(source, GetBookRes.class));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Page<GetSimpleBooksRes> getSimpleBooksByName(String bookName, Pageable pageable) {
		Page<GetSimpleBooksRes> list = Page.empty();
		try {
			Page<Book> data = bookRepository.findByBookNameContaining(bookName, pageable);
			list = data.map(source -> modelMapper.map(source, GetSimpleBooksRes.class));
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
