package org.example.service;

import lombok.AllArgsConstructor;
import org.example.service.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;


}
