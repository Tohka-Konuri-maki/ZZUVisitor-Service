package com.campus.vistorservice.controller;

import com.campus.vistorservice.dao.SuggestionMapper;
import com.campus.vistorservice.model.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suggestion")
public class SuggestionController {

    @Autowired
    private SuggestionMapper suggestionMapper;

    // 1. 提交建议接口 (用户用)
    @PostMapping("/add")
    public String addSuggestion(@RequestBody Suggestion suggestion) {
        if (suggestion.getVisitorName() == null || suggestion.getVisitorName().isEmpty()) {
            suggestion.setVisitorName("游客");
        }
        suggestionMapper.insert(suggestion);
        return "success";
    }

    // 2. 查看建议列表接口 (管理员用)
    @GetMapping("/list")
    public List<Suggestion> getSuggestionList() {
        return suggestionMapper.findAll();
    }
}