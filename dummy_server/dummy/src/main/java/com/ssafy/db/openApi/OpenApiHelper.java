package com.ssafy.db.openApi;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.BufferedInputStream;
import java.net.URL;


public class OpenApiHelper {

    static String key = "b85da2e2bb1b06d1ad67b0f945bf6dcf87e99dd3eb09f70ebe777db8b58d01bd";
    static String startDate = "2021-01-01";
    static String endDate = "2021-01-10";
    static int fromAge = 20;
    static int toAge = 40;

    public static String ApiToJSONObject() throws Exception {
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

    private static String readUrl() throws Exception {
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

