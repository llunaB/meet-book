package com.ssafy.db.openApi;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import com.ssafy.db.entity.Book;

@Component
public class OpenApiHelper {

    private static String key = "b85da2e2bb1b06d1ad67b0f945bf6dcf87e99dd3eb09f70ebe777db8b58d01bd";
    private static String startDate = "2021-01-01";
    private static String endDate = "2021-01-10";
    private int fromAge = 20;
    private int toAge = 40;

    public List<Book> LoadBookData() throws Exception{
    	JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl());
        JSONObject response = (JSONObject)jsonObject.get("response");
        JSONArray docs = (JSONArray) response.get("docs");
        ArrayList<Book> list = new ArrayList<Book>();
        
        for (int i = 0; i < docs.size(); i++) {
            JSONObject doc = (JSONObject) docs.get(i);
            JSONObject docInfo = (JSONObject) doc.get("doc");
            Book book = new Book();
            book.setBook_author(docInfo.get("authors").toString());
            book.setBookname(docInfo.get("bookname").toString());
            book.setBook_contents("");
            book.setBook_pubdate(new Date());
            book.setBook_publisher("");
            book.setBook_thumbnail_url(docInfo.get("bookImageURL").toString());
            book.setIsbn(docInfo.get("isbn13").toString());
            book.setLoan_count(Integer.parseInt(docInfo.get("loan_count").toString()));
            
            list.add(book);
        }

        
        return list;
    } 
    
    public String ApiToJSONObject() throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl());
        JSONObject response = (JSONObject)jsonObject.get("response");
        JSONArray docs = (JSONArray) response.get("docs");

        String message;
        JSONArray myBooks = new JSONArray();
        JSONObject myBook = new JSONObject();

        for (int i = 0; i < docs.size(); i++) {
            JSONObject doc = (JSONObject) docs.get(i);
            JSONObject docInfo = (JSONObject) doc.get("doc");

            myBook.put("book_name", docInfo.get("bookname"));
            myBook.put("book_author", docInfo.get("authors"));
            myBook.put("book_contents", "");
            myBook.put("isbn ", docInfo.get("isbn13"));
            myBook.put("book_pubYear", docInfo.get("isbn13"));
            myBook.put("class_no", docInfo.get("class_no"));
            myBook.put("book_thumbnail_url", docInfo.get("bookImageURL"));
            myBook.put("loan_count", docInfo.get("loan_count"));

            myBooks.add(myBook);
        }

        message = myBooks.toJSONString();
        return message;

    }

    private String readUrl() throws Exception {
        BufferedInputStream reader = null;

        try {
            URL url = new URL("http://data4library.kr/api/loanItemSrch" + "?authKey=" + key +  "&startDt=" +
                    startDate + "&endDt=" + endDate + "&from_age=" + fromAge + "&to_age=" + toAge + "&format=json" + "&pageSize=5");

            reader = new BufferedInputStream(url.openStream());
            StringBuffer buffer = new StringBuffer();

            int i = 0;
            byte[] b = new byte[4096];

            while ((i = reader.read(b)) != -1) {
                buffer.append(new String(b, 0, i));
            }

            return buffer.toString();

        } finally {
            if (reader != null) reader.close();
        }
    }

}

