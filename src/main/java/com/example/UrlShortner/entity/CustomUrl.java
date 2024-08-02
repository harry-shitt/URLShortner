package com.example.UrlShortner.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomUrl {
    private String long_url;
    private String custom_alias;
    private Integer ttl_seconds;
    private String shorten_url;
}
