package com.campus.vistorservice.service;

import com.campus.vistorservice.model.PolygonModel;
import java.util.List;

public interface PolygonService {
    // 根据gid获取面数据
    PolygonModel getPolygonByGid(Integer gid);

    List<PolygonModel> searchByName(String name);

    // 获取所有面数据
    List<PolygonModel> getAllPolygons();
}