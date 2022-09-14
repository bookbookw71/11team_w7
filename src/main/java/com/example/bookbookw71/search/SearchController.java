package com.example.bookbookw71.search;

import com.example.bookbookw71.controller.response.ResponseDto;
import com.example.bookbookw71.dto.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SearchController {

    public final SearchService searchService;

//    @RequestMapping(value = "/api/auth/search", method = RequestMethod.POST)
//    public ResponseDto<?> saveItem(@RequestBody SearchRequestDto requestDto) throws Exception {
//        System.out.println("검색 컨트롤러");
//        return searchService.UrlResponse(requestDto.getSearchTitle());
//    }
}
