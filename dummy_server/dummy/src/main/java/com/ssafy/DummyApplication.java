package com.ssafy;

import com.ssafy.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableScheduling
@SpringBootApplication
public class DummyApplication {

/*
	public static BookService bookService;

	@Autowired
	public DummyApplication(BookService bookService) {
		this.bookService = bookService;
	}
*/

	public static void main(String[] args) {
		SpringApplication.run(DummyApplication.class, args);

//		bookService.getBookData();
	}
}
