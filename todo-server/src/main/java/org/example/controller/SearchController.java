package org.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import org.example.book.BookResponse;
import org.example.conference.ConferenceResponse;
import org.example.service.SearchService;
import org.example.service.repository.ConferenceRepository;
import org.example.user.UserResponse;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin // CORS Issue prevent
@AllArgsConstructor
@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> userSearch(@RequestParam("nickname") String nickname){
        ArrayList<UserResponse> list = new ArrayList<UserResponse>();
        list.add(new UserResponse());

        return new ResponseEntity<List<UserResponse>>(list, HttpStatus.OK);
    }

    @GetMapping("/book")
    public ResponseEntity<List<BookResponse>> bookSearch(@RequestParam("book_name") String book_name){
        ArrayList<BookResponse> list = new ArrayList<>();
        list.add(new BookResponse());

        return new ResponseEntity<List<BookResponse>>(list, HttpStatus.OK);
    }

    @GetMapping("/conference")
    public ResponseEntity<List<ConferenceResponse>> conferenceSearch(@RequestParam("title") String title){
        ArrayList<ConferenceResponse> list = new ArrayList<>();
        list.add(new ConferenceResponse());

        return new ResponseEntity<List<ConferenceResponse>>(list, HttpStatus.OK);
    }

    @GetMapping("/conference/book")
    public ResponseEntity<List<ConferenceResponse>> conferenceBookSearch(@RequestParam("book") String book){
        ArrayList<ConferenceResponse> list = new ArrayList<>();
        list.add(new ConferenceResponse());

        return new ResponseEntity<List<ConferenceResponse>>(list, HttpStatus.OK);
    }

    @GetMapping("/conference/user")
    public ResponseEntity<List<ConferenceResponse>> conferenceUserSearch(@RequestParam("user") String user){
        ArrayList<ConferenceResponse> list = new ArrayList<>();
        list.add(new ConferenceResponse());

        return new ResponseEntity<List<ConferenceResponse>>(list, HttpStatus.OK);
    }

    @GetMapping("/conference/tag")
    public ResponseEntity<List<ConferenceResponse>> conferenceTagSearch(@RequestParam("tag") String tag){
        ArrayList<ConferenceResponse> list = new ArrayList<>();
        list.add(new ConferenceResponse());

        return new ResponseEntity<List<ConferenceResponse>>(list, HttpStatus.OK);
    }
}
