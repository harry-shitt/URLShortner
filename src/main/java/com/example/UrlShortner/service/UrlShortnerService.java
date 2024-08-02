package com.example.UrlShortner.service;


import com.example.UrlShortner.entity.Analytics;
import com.example.UrlShortner.entity.CustomRequest;
import com.example.UrlShortner.entity.CustomUrl;
import com.example.UrlShortner.util.UrlShortnerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

@Service
public class UrlShortnerService {
    private final String prefixUrl = "short.ly";

    HashMap<String, CustomUrl> shorten_url_map = new HashMap<>();
    HashMap<String, Analytics> analytics_map = new HashMap<>();

    public CustomUrl saveUrl(CustomUrl customUrl){
        String customAlias;
        if(customUrl.getCustom_alias()==null){
            customAlias = UrlShortnerUtil.getRandomAlias(customUrl.getLong_url());
        }else{
            customAlias = customUrl.getCustom_alias();
            if(shorten_url_map.get(customAlias)!=null){
                return null;
            }
        }
        if(customUrl.getTtl_seconds()==null){
            customUrl.setTtl_seconds(120);
        }
        String shortendUrl = UrlShortnerUtil.getShortenUrl(customUrl.getLong_url() , prefixUrl , customAlias);
        customUrl.setShorten_url(shortendUrl);
        shorten_url_map.put(customAlias , customUrl);
        Analytics analytics = new Analytics();
        analytics.getAccess_times().add(Timestamp.from(Instant.now()));
        analytics.setAccess_count(0);
        analytics_map.put(customAlias,analytics);
        return customUrl;
    }

    public CustomUrl getUrl(String alias){
        CustomUrl obj = shorten_url_map.get(alias);

        if(obj!=null) {
            Analytics a1 = analytics_map.get(alias);
            int count = a1.getAccess_count();
            a1.setAccess_count(count + 1);
            a1.getAccess_times().add(Timestamp.from(Instant.now()));
        }
        return obj;
    }

    public Analytics getAnalytisc(String alias){
        CustomUrl obj = shorten_url_map.get(alias);
        if(obj!=null) {
            Analytics a1 = analytics_map.get(alias);
            a1.setAlias(alias);
            a1.setLong_url(obj.getLong_url());
            return a1;
        }else{
            return null;
        }
    }

    public HttpStatus updateUrl(String alias, CustomRequest customRequest){
        CustomUrl customUrl = shorten_url_map.get(alias);
        if(customUrl==null){
            return HttpStatus.NOT_FOUND;
        }else if(customRequest.getNew_alias()==null && customRequest.getNewTtl()==null){
            return HttpStatus.BAD_REQUEST;
        }else{
            if(customRequest.getNewTtl()!=null){
                customUrl.setTtl_seconds(customRequest.getNewTtl());
            }

            if(customRequest.getNew_alias()!=null){
                customUrl.setCustom_alias(customRequest.getNew_alias());
            }
            shorten_url_map.put(alias,customUrl);
            Analytics analytics = analytics_map.get(alias);
            analytics.setAlias(customRequest.getNew_alias());
            analytics_map.put(alias,analytics);
            return HttpStatus.OK;
        }
    }

    public HttpStatus deleteAlias(String alias){
        CustomUrl customUrl = shorten_url_map.get(alias);
        if(customUrl==null){
            return HttpStatus.NOT_FOUND;
        }else{
            shorten_url_map.remove(alias);
            analytics_map.remove(alias);
            return HttpStatus.OK;
        }
    }



}
