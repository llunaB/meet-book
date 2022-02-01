package com.ssafy.db.openApi;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.*;

import com.ssafy.db.entity.Genre;
import com.ssafy.db.repository.GenreRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

    public List<Book> loadBookData() {

        String uriString = constructUriStringWithQueryParameter();
        List<Map<String, Map<String, Object>>> list = uriToJsonObjectToList(uriString);

        ArrayList<Book> books = new ArrayList<>();

        for (Map<String, Map<String, Object>> doc : list) {
            Map<String, Object> bookInfo = doc.get("doc");

            int genreId = (int)Math.floor(Double.parseDouble((String) bookInfo.get("class_no")) / 100.0);
            Genre genre = genreRepository.findById(genreId).orElseThrow(NullPointerException::new);

            // publication_year=
            // 출판년도가 (공백)인 데이터가 존재해서 따로 처리 필요 (100p 이상 불러오는 경우)
            Book book = Book.builder()
                    .bookName((String) bookInfo.getOrDefault("bookname", ""))
                    .bookAuthor((String) (bookInfo.getOrDefault("authors", "")))
                    .bookContents("")
                    .bookPublisher((String) bookInfo.getOrDefault("publisher", ""))
                    .isbn((String) bookInfo.getOrDefault("isbn13", ""))
                    .bookPubYear(Integer.parseInt((String) bookInfo.getOrDefault("publication_year", "0")))
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
        int pageSize = 10;
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
}
