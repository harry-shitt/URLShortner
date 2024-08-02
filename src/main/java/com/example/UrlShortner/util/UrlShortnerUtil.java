package com.example.UrlShortner.util;


import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UrlShortnerUtil {

    public static String getShortenUrl(String originalUrl, String prefixUrl, String customAlias) {
        if (originalUrl.indexOf("https") == -1) {
            return "http://" + prefixUrl + customAlias;
        } else {
            return "https://" + prefixUrl + "/" + customAlias;
        }
    }

    public static String getRandomAlias(String longUrl){
        String randomAlias = Base64.getUrlEncoder().encodeToString(longUrl.getBytes()).substring(0,6);
        return randomAlias;
    }


}
