package com.campus.vistorservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.campus.vistorservice.dao.LostFoundMapper;
import com.campus.vistorservice.model.LostFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lost")
public class LostFoundController {

    @Autowired
    private LostFoundMapper lostFoundMapper;

    // 1. 发布失物招领
    @PostMapping("/add")
    public String add(@RequestBody LostFound lostFound) {
        if (lostFound.getLongitude() == null || lostFound.getLatitude() == null) {
            return "error: missing location";
        }
        if (lostFound.getVisitorName() == null || lostFound.getVisitorName().isEmpty()) {
            lostFound.setVisitorName("游客");
        }
        lostFoundMapper.insert(lostFound);
        return "success";
    }

    // 2. 获取所有点位 (GeoJSON 格式)
    @GetMapping("/list")
    public JSONObject list() {
        List<LostFound> list = lostFoundMapper.findAllActive();

        // 组装标准 GeoJSON FeatureCollection
        JSONObject featureCollection = new JSONObject();
        featureCollection.put("type", "FeatureCollection");
        List<JSONObject> features = new ArrayList<>();

        for (LostFound item : list) {
            JSONObject feature = new JSONObject();
            feature.put("type", "Feature");

            // 属性
            JSONObject properties = new JSONObject();
            properties.put("id", item.getId());
            properties.put("itemName", item.getItemName());
            properties.put("description", item.getDescription());
            properties.put("contact", item.getContact());
            properties.put("lostType", item.getLostType());
            properties.put("createTime", item.getCreateTime());
            properties.put("visitorName", item.getVisitorName());
            feature.put("properties", properties);

            // 几何
            if (item.getGeometry() != null) {
                feature.put("geometry", JSON.parse(item.getGeometry()));
            }
            features.add(feature);
        }

        featureCollection.put("features", features);
        return featureCollection;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        lostFoundMapper.deleteById(id);
        return "success";

    }
}