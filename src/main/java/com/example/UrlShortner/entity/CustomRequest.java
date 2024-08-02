package com.example.UrlShortner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomRequest {
    private String new_alias;
    private Integer newTtl;
}
