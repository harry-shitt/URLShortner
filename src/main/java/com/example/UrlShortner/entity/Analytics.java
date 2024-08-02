package com.example.UrlShortner.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Data
public class Analytics {
    private String alias;
    private String long_url;
    private Integer access_count;
    private List<Timestamp> access_times = new ArrayList<>();


}
