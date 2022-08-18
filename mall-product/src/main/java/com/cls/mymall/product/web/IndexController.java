package com.cls.mymall.product.web;

import com.cls.mymall.product.entity.CategoryEntity;
import com.cls.mymall.product.service.CategoryService;
import com.cls.mymall.product.vo.CatalogLevel2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {

        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categories();

        model.addAttribute("categories", categoryEntities);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/json/catalog.json")
    public Map<String, List<CatalogLevel2Vo>> getCatalogJson() {
        return categoryService.getCatalogJson();
    }

}
