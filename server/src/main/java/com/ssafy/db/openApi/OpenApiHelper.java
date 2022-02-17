package com.ssafy.db.openApi;

import java.util.*;

import com.ssafy.db.entity.Genre;
import com.ssafy.db.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ssafy.db.entity.Book;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenApiHelper {

    private GenreRepository genreRepository;

    @Autowired
    public OpenApiHelper(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public static String truncate(String str, int length) {
        assert length >= 3 : length;
        length = length - 3;
        if (str.length() <= length)
            return str;
        else
            return str.substring(0, length) + "...";
    }

    public List<Book> loadBookData() {
        // kakao search - bookContent
        OpenApiHelper2 opn = new OpenApiHelper2();

        // rest infos
        String uriString = constructUriStringWithQueryParameter();
        List<Map<String, Map<String, Object>>> list = uriToJsonObjectToList(uriString);

        ArrayList<Book> books = new ArrayList<>();

        for (Map<String, Map<String, Object>> doc : list) {
            Map<String, Object> bookInfo = doc.get("doc");

            // get Genre
            int genreId = (int)Math.floor(Double.parseDouble((String) bookInfo.get("class_no")) / 100.0) + 1;
            Genre genre = genreRepository.findById(genreId).orElseThrow(NullPointerException::new);

            // get bookContent from Kakao API
            String bookContent = OpenApiHelper2.getHttpEntityStringFinallyContents((String) bookInfo.get("bookname"));
            bookContent = truncate(bookContent, 255);

            // make Book entity
            Book book = Book.builder()
                    .bookName((String) bookInfo.getOrDefault("bookname", ""))
                    .bookAuthor((String) (bookInfo.getOrDefault("authors", "")))
                    .bookContents(bookContent)
                    .bookPublisher((String) bookInfo.getOrDefault("publisher", ""))
                    .isbn((String) bookInfo.getOrDefault("isbn13", ""))
                    .bookPubYear(Integer.parseInt(((String) bookInfo.get("publication_year")).isEmpty() ? "0000" : ((String) bookInfo.get("publication_year"))))
                    .loanCount(Integer.parseInt((String) bookInfo.getOrDefault("loan_count", "0")))
                    .genre(genre)
                    .bookThumbnailUrl((String) bookInfo.getOrDefault("bookImageURL", ""))
                    .build();

            books.add(book);
        }
        return books;
    }
    
    public List<Book> loadBookDataWithKDC(int kdc) {

        String uriString = constructUriStringWithQueryParameter(kdc);
        List<Map<String, Map<String, Object>>> list = uriToJsonObjectToList(uriString);

        ArrayList<Book> books = new ArrayList<>();

        int genreId = kdc + 1;
        Genre genre = genreRepository.findById(genreId).orElseThrow(NullPointerException::new);
        
        for (Map<String, Map<String, Object>> doc : list) {
            Map<String, Object> bookInfo = doc.get("doc");
            Book book = Book.builder()
                    .bookName((String) bookInfo.getOrDefault("bookname", ""))
                    .bookAuthor((String) (bookInfo.getOrDefault("authors", "")))
                    .bookContents("")
                    .bookPublisher((String) bookInfo.getOrDefault("publisher", ""))
                    .isbn((String) bookInfo.getOrDefault("isbn13", ""))
                    .bookPubYear(Integer.parseInt(((String) bookInfo.get("publication_year")).isEmpty() ? "0000" : ((String) bookInfo.get("publication_year"))))
                    .loanCount(Integer.parseInt((String) bookInfo.getOrDefault("loan_count", "0")))
                    .genre(genre)
                    .bookThumbnailUrl((String) bookInfo.getOrDefault("bookImageURL", ""))
                    .build();

            books.add(book);
        }
        return books;
    }

    private static List<Map<String, Map<String, Object>>> uriToJsonObjectToList(String uriString) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uriString, String.class);

        BasicJsonParser basicJsonParser = new BasicJsonParser();
        Map<String, Object> stringObjectMap = basicJsonParser.parseMap(forEntity.getBody());

        Map<String, Object> response = (Map<String, Object>) stringObjectMap.get("response");

        List<Map<String, Map<String, Object>>> docs = (List<Map<String, Map<String, Object>>>) response.get("docs");

        return docs;
    }

    private static String constructUriStringWithQueryParameter() {

        String key = "b85da2e2bb1b06d1ad67b0f945bf6dcf87e99dd3eb09f70ebe777db8b58d01bd";
        String startDate = "2021-01-01";
        String endDate = "2021-01-10";
        int fromAge = 20;
        int toAge = 40;
        int pageSize = 100;
        String format = "json";

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("data4library.kr")
                .path("/api/loanItemSrch")
                .queryParam("authKey", key)
                .queryParam("startDt", startDate)
                .queryParam("endDt", endDate)
                .queryParam("from_age", fromAge)
                .queryParam("to_age", toAge)
                .queryParam("pageSize", pageSize)
                .queryParam("format", format)
                .build();

        return uriComponents.toString();
    }
    
    private static String constructUriStringWithQueryParameter(int kdc) {

        String key = "b85da2e2bb1b06d1ad67b0f945bf6dcf87e99dd3eb09f70ebe777db8b58d01bd";
        String startDate = "2021-01-01";
        String endDate = "2021-12-31";
        int fromAge = 20;
        int toAge = 40;
        int pageSize = 200;
        String format = "json";

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("data4library.kr")
                .path("/api/loanItemSrch")
                .queryParam("authKey", key)
                .queryParam("startDt", startDate)
                .queryParam("endDt", endDate)
                .queryParam("from_age", fromAge)
                .queryParam("to_age", toAge)
                .queryParam("pageSize", pageSize)
                .queryParam("format", format)
                .queryParam("kdc", kdc)
                .build();

        return uriComponents.toString();
    }
    
    
}


