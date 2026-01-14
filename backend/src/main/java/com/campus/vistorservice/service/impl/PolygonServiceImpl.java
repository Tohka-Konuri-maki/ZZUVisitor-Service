package com.campus.vistorservice.service.impl;

import com.campus.vistorservice.dao.PolygonMapper;
import com.campus.vistorservice.model.PolygonModel;
import com.campus.vistorservice.service.PolygonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PolygonServiceImpl implements PolygonService {

    @Autowired
    private PolygonMapper polygonMapper;

    @Override
    public PolygonModel getPolygonByGid(Integer gid) {
        PolygonModel polygon = polygonMapper.selectPolygonByGid(gid);
        System.out.println("查询gid=" + gid + "的结果：" + polygon);
        return polygon;
    }

    @Override
    public List<PolygonModel> getAllPolygons() {
        return polygonMapper.selectAllPolygons();
    }


    @Override
    public List<PolygonModel> searchByName(String name) {
        return polygonMapper.searchPolygonsByName(name);
    }

}