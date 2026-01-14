package com.campus.vistorservice.dao;

import com.campus.vistorservice.model.Suggestion;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SuggestionMapper {
    // 添加建议
    void insert(Suggestion suggestion);

    // 查询所有建议 (按时间倒序)
    List<Suggestion> findAll();
}