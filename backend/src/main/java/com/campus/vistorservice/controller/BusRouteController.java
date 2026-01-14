package com.campus.vistorservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.campus.vistorservice.dao.BusRouteMapper;
import com.campus.vistorservice.model.BusRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
// 🔥 核心修复：添加 /zzuvisitor 前缀，与你的项目风格保持一致
@RequestMapping("/bus")
public class BusRouteController {

    @Autowired
    private BusRouteMapper busRouteMapper;

    @GetMapping("/all")
    public JSONObject getAllBusRoutes() {
        List<BusRoute> routes = busRouteMapper.findAll();

        JSONObject featureCollection = new JSONObject();
        featureCollection.put("type", "FeatureCollection");

        List<JSONObject> features = new ArrayList<>();
        for (BusRoute route : routes) {
            JSONObject feature = new JSONObject();
            feature.put("type", "Feature");

            JSONObject properties = new JSONObject();
            properties.put("id", route.getId());
            properties.put("name", route.getName());
            properties.put("stops", route.getStops());
            properties.put("schedule", route.getSchedule());
            feature.put("properties", properties);

            if (route.getGeometry() != null) {
                feature.put("geometry", JSON.parse(route.getGeometry()));
            }

            features.add(feature);
        }

        featureCollection.put("features", features);
        return featureCollection;
    }
}