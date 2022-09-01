package com.cls.mymall.search.service;

import com.cls.mymall.search.vo.SearchParam;
import com.cls.mymall.search.vo.SearchResult;

public interface SearchService {
    /**
     *
     * @param searchParam 检索条件
     * @return 检索结果
     */
    SearchResult search(SearchParam searchParam);
}
