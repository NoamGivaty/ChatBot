package com.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class AmazonService {

    public static final Pattern PRODUCT_PATTERN = Pattern.compile("span class=\\\"a-size-medium a-color-base a-text-normal\\\">([^<]+)<\\/span>.*<span class=\\\"a-icon-alt\\\">([^<]+)<\\/span>.*<span class=\\\"a-offscreen\\\">([^<]+)<\\/span>");

    private String parseProductHtml(String html, String keyword) {
        String res = "";
        Matcher matcher = PRODUCT_PATTERN.matcher(html);

        for(int i=1;matcher.find();i++) {
            res += i + ". " +matcher.group(1) + " - " + matcher.group(2) + ", price:" + matcher.group(3) + "\n";
        }
        if(res.isEmpty())
            return "No products founds with the name: \"" +keyword +"\"";
        return res;
    }
    public String searchProducts(String keyword) throws IOException {
        return parseProductHtml(getProductHtml(keyword),keyword);
    }

    private String getProductHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url("https://www.amazon.com/s?i=aps&k=" + keyword + "&ref=nb_sb_noss&url=search-alias%3Daps")
                .method("GET", null)
                .addHeader("authority", "www.amazon.com")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("cookie", "aws-target-data=%7B%22support%22%3A%221%22%7D; aws-target-visitor-id=1680241041756-858496.34_0; aws-ubid-main=826-6841044-1217263; aws-account-alias=995553441267; remember-account=true; regStatus=registered; AMCV_7742037254C95E840A4C98A6%40AdobeOrg=1585540135%7CMCIDTS%7C19448%7CMCMID%7C20569596720830034471026638236726947753%7CMCAAMLH-1680965258%7C7%7CMCAAMB-1680965258%7C6G1ynYcLPuiQxYZrsz_pkqfLG9yMXBpb2zX5dvJdYQJzPXImdj0y%7CMCOPTOUT-1680367658s%7CNONE%7CMCAID%7CNONE%7CvVersion%7C4.4.0; aws-userInfo=%7B%22arn%22%3A%22arn%3Aaws%3Aiam%3A%3A995553441267%3Auser%2Fnoam%22%2C%22alias%22%3A%22995553441267%22%2C%22username%22%3A%22noam%22%2C%22keybase%22%3A%22B3KZ0t%2BsSBC8Ng7glPkPLgoEwqJeIxjLMpK8y18A9bI%5Cu003d%22%2C%22issuer%22%3A%22http%3A%2F%2Fsignin.aws.amazon.com%2Fsignin%22%2C%22signinType%22%3A%22PUBLIC%22%7D; aws-userInfo-signed=eyJ0eXAiOiJKV1MiLCJrZXlSZWdpb24iOiJ1cy1lYXN0LTIiLCJhbGciOiJFUzM4NCIsImtpZCI6IjFlMjgyMGJlLTllNjQtNDVlZC04OGY1LTljOTdhZDRlZDM3MyJ9.eyJzdWIiOiI5OTU1NTM0NDEyNjciLCJzaWduaW5UeXBlIjoiUFVCTElDIiwiaXNzIjoiaHR0cDpcL1wvc2lnbmluLmF3cy5hbWF6b24uY29tXC9zaWduaW4iLCJrZXliYXNlIjoiQjNLWjB0K3NTQkM4Tmc3Z2xQa1BMZ29Fd3FKZUl4akxNcEs4eTE4QTliST0iLCJhcm4iOiJhcm46YXdzOmlhbTo6OTk1NTUzNDQxMjY3OnVzZXJcL25vYW0iLCJ1c2VybmFtZSI6Im5vYW0ifQ.TSnEIf-2Sm4SbvYl0URdMB5owMzVXVgenQfcYeBpgepo48JOcoYZqniihd5YJDDS0v2DwrfFoYPvQ9Ez3Pqya83PclQ-QKZqoZwd5HTO44Rctqo_OUoVbyNbsLqHXpVi; noflush_awsccs_sid=c9db0b626709b54417ee6ed85407692ce7a189da9b2eba110ed42f43ba0cfbae; session-id=131-3610226-8281605; session-id-time=2082787201l; i18n-prefs=USD; skin=noskin; ubid-main=134-0563473-2085634; session-token=\"cQo2GUGcWYTHv/P5HDkdmjVuxtXuuN49G0f2LcaSpaqsskgQwC1c1PHOh8MA/PvriY9oHpgeRH4ztwvZMr2viMg6v1A8aht4lGbHY1w9+pxXkG3EI18joDZ4FIvtdVvhKQpbdjNzszOkjGMvrGbUtUhFpk/q6k0drtFBNgMahTaZmacs9tccdhpSQxuyb9CRW2SRTJHcBrPrrEWv1nk758Xaq/GaDmXD0wnjeb3HD+I=\"; csm-hit=tb:V18DKWGQ8XY8CC55WESQ+s-T81PY5PTR1RZ6D7QTNF8|1680516298570&t:1680516298570&adb:adblk_no")
                .addHeader("device-memory", "8")
                .addHeader("downlink", "10")
                .addHeader("dpr", "1")
                .addHeader("ect", "4g")
                .addHeader("referer", "https://www.amazon.com/s?k=ipod&crid=1XCXNJ8Z9XDZG&sprefix=ipod+%2Caps%2C170&ref=nb_sb_noss_2")
                .addHeader("rtt", "50")
                .addHeader("sec-ch-device-memory", "8")
                .addHeader("sec-ch-dpr", "1")
                .addHeader("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Linux\"")
                .addHeader("sec-ch-viewport-width", "1005")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .addHeader("viewport-width", "1005")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}