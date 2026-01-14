package com.campus.vistorservice.dao;


import com.campus.vistorservice.model.PointModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper

public interface PointMapper {
    // 根据gid查询单个点数据
    PointModel selectPointByGid(@Param("gid") Integer gid);

    // 查询所有点数据
    List<PointModel> selectAllPoints();
}