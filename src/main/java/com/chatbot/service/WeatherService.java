package com.chatbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {

    @Autowired
    ObjectMapper om;

    OkHttpClient client = new OkHttpClient().newBuilder().build();
    public String getWeather(String keyword) throws IOException {
        Request request = new Request.Builder()
                .url("https://openweathermap.org/data/2.5/find?q=" + keyword +"&appid=439d4b804bc8187953eb36d2a8c26a02&units=metric")
                .method("GET", null)
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "en-US,en;q=0.9")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "october_session=eyJpdiI6Imp4dlVzaXJNTU5UOVpqXC9KRGQ3NmtnPT0iLCJ2YWx1ZSI6ImRpQk5YQUpaVm9hd1pCa2txT1wvZ051UDJnS1FKTGQyTCtyZVAyajM0XC91bUl4SVwvaFZZY1pKak1aUUFJTmNQZEx6S2FuVjFkeXhaRk5OWXdQYjNYWTFlTEJSeDAwOU03NUVuNE5vTGdzMVwvQWlqNm5KSmdVMHUxK1JIWmhGN1c3cCIsIm1hYyI6IjllYmNmOTdlZDk5ZmMzYjg1OGMxZTIyNTE2YmM5ODJmYzRlNDcyN2U3ZGU0MDEyYzcwNzMxMzFiMzVlZDg1MWMifQ%3D%3D; units=metric; _ga=GA1.2.346416678.1680618392; _gid=GA1.2.1588222755.1680618392; _gat_gtag_UA_31601618_1=1; stick-footer-panel=true; __gads=ID=50837cb70ab0e959-22b0c823dddb00bb:T=1680618391:RT=1680618391:S=ALNI_MYtBAxnQWuti98aEKPZkZgy1yyJOw; __gpi=UID=000009748ec073d2:T=1680618391:RT=1680618391:S=ALNI_Mbc216B3ChTpwYm2hZ4rV-H46z0sw; cityid=2643743")
                .addHeader("Referer", "https://openweathermap.org/")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .addHeader("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Linux\"")
                .build();
        Response response = client.newCall(request).execute();

        String res = response.body().string();
        JsonNode node = om.readTree(res);

        if(node.get("count").asInt()==0)
            return "There is no weather forecast in \"" + keyword +"\".";

        int id = node.get("list").get(0).get("id").asInt();

        return getWeatherForLocationId(id);
    }

    private String getWeatherForLocationId(Integer locationId) throws IOException {

        Request request = new Request.Builder()
                .url("https://openweathermap.org/data/2.5/weather?id=" + locationId +"&appid=439d4b804bc8187953eb36d2a8c26a02")
                .method("GET", null)
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "en-US,en;q=0.9")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "units=metric; _ga=GA1.2.346416678.1680618392; _gid=GA1.2.1588222755.1680618392; stick-footer-panel=true; __gads=ID=50837cb70ab0e959-22b0c823dddb00bb:T=1680618391:RT=1680618391:S=ALNI_MYtBAxnQWuti98aEKPZkZgy1yyJOw; __gpi=UID=000009748ec073d2:T=1680618391:RT=1680618391:S=ALNI_Mbc216B3ChTpwYm2hZ4rV-H46z0sw; cityid=293397; october_session=eyJpdiI6Im1pSzhYSHpYTmdsOFBZQ3VvMWRVSGc9PSIsInZhbHVlIjoiZWpUdnJ6K2hGcFwvUmhwdDJWVEhMXC9zeDJXcDBNdFVLKzhZdEN1YSt5bVNWWjJJN3VsUGlcL09odlBYSGlLamFpYmVyakY4MkM2NmZ3S09SNHFWVjBHRFY4XC9SQXJZM0VjWGY3NTVnYjVlUGNHY0wyOHFEZ1I2bWR0TFFXTENwYVVkIiwibWFjIjoiMWQzOWU0M2M4NjlkOWY5OWE4YjY4N2VmNjE5ZjZhODhhYzNjNTg0OWE1Yzc0NWI0M2RkZTJhYjdkN2MwZmQ4NyJ9")
                .addHeader("Referer", "https://openweathermap.org/city/293397")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .addHeader("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Linux\"")
                .build();
        Response response = client.newCall(request).execute();

        String res = response.body().string();

        JsonNode node = om.readTree(res);
        String city = node.get("name").asText();
        String weather = "The weather in " +city +" is:\n";
        weather += "- Description: " + node.get("weather").get(0).get("description").asText() +"\n";
        weather += "- Temperature: " + node.get("main").get("temp").asText() +"\n";
        weather += "- Feels like: " + node.get("main").get("feels_like").asText() +"\n";
        weather += "- Winds: " + node.get("wind").get("speed").asText() +" MPH\n";

        return weather;
    }
}
