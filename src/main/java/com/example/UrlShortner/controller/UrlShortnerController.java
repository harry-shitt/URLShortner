package com.example.UrlShortner.controller;


import com.example.UrlShortner.entity.Analytics;
import com.example.UrlShortner.entity.CustomRequest;
import com.example.UrlShortner.entity.CustomUrl;
import com.example.UrlShortner.service.UrlShortnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/urlshortner")
public class UrlShortnerController {

    @Autowired
    UrlShortnerService urlShortnerService;

    @PostMapping("/shorten")
    public ResponseEntity<?> getShortenUrl(@RequestBody CustomUrl customUrl){
            CustomUrl customUrl1 = urlShortnerService.saveUrl(customUrl);
            if(customUrl1==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(customUrl1, HttpStatus.OK);
    }




    @GetMapping("/alias/{alias}")
    public ResponseEntity<?> getOriginalUrl(@PathVariable String alias){
        CustomUrl customUrl = urlShortnerService.getUrl(alias);

        if(customUrl==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(customUrl,HttpStatus.MOVED_PERMANENTLY);
        }

    }

    @GetMapping("/analytics/{alias}")
    public ResponseEntity<?> getAnalytics(@PathVariable String alias){
        Analytics analytics = urlShortnerService.getAnalytisc(alias);
        if(analytics==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(analytics,HttpStatus.OK);
        }
    }


    @PutMapping("/update/{alias}")
    public ResponseEntity<?> updateAlias(@RequestBody CustomRequest customRequest, @PathVariable String alias){
            HttpStatus currentStatus = urlShortnerService.updateUrl(alias, customRequest);
            return new ResponseEntity<>(currentStatus);
    }


    @DeleteMapping("/delete/{alias}")
    public ResponseEntity<?> deleteAlias(@PathVariable String alias){
        HttpStatus currentStatus = urlShortnerService.deleteAlias(alias);
        return new ResponseEntity<>(currentStatus);
    }


    @GetMapping("/ping")
    public String getOriginalUrl(){

//        return new ResponseEntity<>(ResponseEntity.status())
        return "pong";
    }

}
