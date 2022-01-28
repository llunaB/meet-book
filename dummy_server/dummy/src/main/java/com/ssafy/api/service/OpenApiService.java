package com.ssafy.api.service;

import com.ssafy.db.entity.Book;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OpenApiService {

    public List<Book> loadBookData() {

        String uriString = constructUriStringWithQueryParameter();
        List<Map<String, Map<String, Object>>> list = uriToJsonObjectToList(uriString);

        ArrayList<Book> books = new ArrayList<>();

        for (Map<String, Map<String, Object>> doc : list) {
            Map<String, Object> bookInfo = doc.get("doc");

            Book book = Book.builder()
                    .bookname((String)bookInfo.get("bookname"))
                    .book_author((String)bookInfo.get("authors"))
                    .book_contents("")
                    .book_publisher((String)bookInfo.get("publisher"))
                    .isbn((String)bookInfo.get("isbn13"))
                    .book_pub_year(Integer.parseInt((String) bookInfo.get("publication_year")))
                    .loan_count(Integer.parseInt((String) bookInfo.get("loan_count")))
                    .genre(null)
                    .book_thumbnail_url((String) bookInfo.get("bookImageURL"))
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
