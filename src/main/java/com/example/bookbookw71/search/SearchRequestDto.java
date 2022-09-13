package com.example.bookbookw71.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequestDto {
    private String searchTitle;
}
