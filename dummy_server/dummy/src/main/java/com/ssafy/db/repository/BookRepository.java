package com.ssafy.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	Optional<Book> findByIsbn(String isbn);
	Page<Book> findByBookNameContaining(String bookname, Pageable pageable);
	List<Book> findByBookNameContaining(String bookname);

	@Query(value = "select * from book b order by b.loan_count desc", nativeQuery = true)
	List<Book> findAllOrderByLoanCountDesc();
}
