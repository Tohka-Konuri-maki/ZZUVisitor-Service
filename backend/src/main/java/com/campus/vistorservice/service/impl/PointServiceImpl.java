package com.campus.vistorservice.service.impl;

import com.campus.vistorservice.dao.PointMapper;
import com.campus.vistorservice.model.PointModel;
import com.campus.vistorservice.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    private PointMapper pointMapper;

    @Override
    public PointModel getPointByGid(Integer gid) {
        return pointMapper.selectPointByGid(gid);
    }

    @Override
    public List<PointModel> getAllPoints() {
        return pointMapper.selectAllPoints();
    }
}