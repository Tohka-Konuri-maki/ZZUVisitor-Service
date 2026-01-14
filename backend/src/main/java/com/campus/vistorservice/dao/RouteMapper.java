package com.campus.vistorservice.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RouteMapper {
    /**
     * 查找距离点击点最近的道路节点ID
     * @param lon 经度
     * @param lat 纬度
     * @return 节点ID
     */
    Integer findNearestNode(@Param("lon") double lon, @Param("lat") double lat);

    /**
     * 计算最短路径
     * @param startNode 起点ID
     * @param endNode 终点ID
     * @return 路径的GeoJSON字符串
     */
    String shortestPath(@Param("startNode") int startNode, @Param("endNode") int endNode);
}