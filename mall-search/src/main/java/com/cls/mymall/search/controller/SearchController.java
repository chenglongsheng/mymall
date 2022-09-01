package com.cls.mymall.search.controller;

import com.cls.mymall.search.service.SearchService;
import com.cls.mymall.search.vo.SearchParam;
import com.cls.mymall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/list.html")
    public String listPage(Model model, SearchParam searchParam) {
        SearchResult searchResult = searchService.search(searchParam);
        model.addAttribute("result" , searchResult);
        return "list";
    }

}
