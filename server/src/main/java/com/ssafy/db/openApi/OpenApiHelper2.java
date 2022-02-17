package com.ssafy.db.openApi;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OpenApiHelper2 {

    public static String getHttpEntityStringFinallyContents(String bookName) {
        String key = "KakaoAK 2e62ef8db528457bf56228492f5b60d2";
        String query = bookName;

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("dapi.kakao.com")
                .path("/v3/search/book")
                .queryParam("query", query)
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", key);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> stringHttpEntity = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, new HttpEntity(httpHeaders), String.class);

        BasicJsonParser basicJsonParser = new BasicJsonParser();
        Map<String, Object> stringObjectMap = basicJsonParser.parseMap(stringHttpEntity.getBody());
        List<Map<String, Object>> docs = (List<Map<String, Object>>) stringObjectMap.get("documents");

        List<Object> bookContents = new ArrayList<>();
        for (Map<String, Object> doc : docs) {
            Object contents = doc.get("contents");
            bookContents.add(contents);
        }
        if (bookContents.size()!=0) { return (String) bookContents.get(0); }
        else {
            return " ";
        }
    }
}

