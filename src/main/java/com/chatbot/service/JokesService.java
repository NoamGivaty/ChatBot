package com.chatbot.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

@Service
public class JokesService {

    OkHttpClient client = new OkHttpClient().newBuilder().build();
    @Autowired
    ObjectMapper om;
    public String getJokes(String keyword) throws IOException {

        Request request = new Request.Builder()
                .url("https://api.chucknorris.io/jokes/search?query=" + keyword)
                .method("GET", null)
                .addHeader("authority", "api.chucknorris.io")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("cache-control", "max-age=0")
                .addHeader("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Linux\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "none")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string();
        JsonNode node = om.readTree(res);
        String jokes = "";
        int total = node.get("total").asInt();
        if (total == 0)
            return "No jokes found with \"" + keyword + "\".";

        for(int i=0;i<total;i++)
        {
            jokes += i+1 +". " + node.get("result").get(i).get("value").asText() + "\n";
        }

        return jokes;
    }
}
