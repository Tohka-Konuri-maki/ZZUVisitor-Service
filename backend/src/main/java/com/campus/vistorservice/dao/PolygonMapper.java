package com.campus.vistorservice.dao;

import com.campus.vistorservice.model.PolygonModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper

public interface PolygonMapper {
    // 根据gid查询单个面数据
    PolygonModel selectPolygonByGid(@Param("gid") Integer gid);

    // 在 interface PolygonMapper 中添加：
    List<PolygonModel> searchPolygonsByName(@Param("name") String name);

    // 查询所有面数据
    List<PolygonModel> selectAllPolygons();
}