package com.campus.vistorservice.service;

import com.campus.vistorservice.model.PointModel;
import java.util.List;

public interface PointService {
    // 根据gid获取点数据
    PointModel getPointByGid(Integer gid);

    // 获取所有点数据
    List<PointModel> getAllPoints();
}