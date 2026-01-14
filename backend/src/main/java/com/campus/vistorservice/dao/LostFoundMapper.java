package com.campus.vistorservice.dao;

import com.campus.vistorservice.model.LostFound;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface LostFoundMapper {
    void insert(LostFound lostFound);
    List<LostFound> findAllActive();

    // 🔥 新增：根据 ID 删除记录
    void deleteById(Integer id);
}