package com.campus.vistorservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.campus.vistorservice.model.PointModel;
import com.campus.vistorservice.model.PolygonModel;
import com.campus.vistorservice.service.PointService;
import com.campus.vistorservice.service.PolygonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class MapController {

    @Autowired
    private PointService pointService;
    @Autowired
    private PolygonService polygonService;

    @GetMapping("/pointmodel/all")
    public JSONObject getAllPointsGeoJSON() {
        return buildGeoJSONFeatureCollection(pointService.getAllPoints());
    }

    @GetMapping("/polygonmodel")
    public JSONObject getAllPolygons() {
        return buildGeoJSONFeatureCollection(polygonService.getAllPolygons());
    }

    @GetMapping("/polygonmodel/search")
    public JSONObject searchPolygons(@RequestParam("name") String name) {
        return buildGeoJSONFeatureCollection(polygonService.searchByName(name));
    }

    private JSONObject buildGeoJSONFeatureCollection(List<?> dataList) {
        JSONObject featureCollection = new JSONObject();
        featureCollection.put("type", "FeatureCollection");
        List<JSONObject> features = new ArrayList<>();

        for (Object obj : dataList) {
            try {
                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                String geometryStr = null;
                Integer gid = null;
                String name = "";

                if (obj instanceof PointModel) {
                    PointModel p = (PointModel) obj;
                    geometryStr = p.getGeometry();
                    gid = p.getGid();
                    name = p.getName();
                } else if (obj instanceof PolygonModel) {
                    PolygonModel p = (PolygonModel) obj;
                    geometryStr = p.getGeometry();
                    gid = p.getGid();
                    name = p.getName();
                }

                if (geometryStr == null) continue;

                JSONObject properties = new JSONObject();
                properties.put("gid", gid);
                properties.put("name", name);
                feature.put("properties", properties);
                feature.put("geometry", JSON.parse(geometryStr)); // 直接解析数据库给的 GeoJSON

                features.add(feature);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        featureCollection.put("features", features);
        return featureCollection;
    }
}