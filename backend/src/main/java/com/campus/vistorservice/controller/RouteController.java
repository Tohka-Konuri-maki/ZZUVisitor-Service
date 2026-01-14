package com.campus.vistorservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.campus.vistorservice.dao.RouteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteMapper routeMapper;

    @GetMapping("/plan")
    public JSONObject planRoute(@RequestParam double startLat, @RequestParam double startLng,
                                @RequestParam double endLat, @RequestParam double endLng) {
        try {
            // === 调试日志 START ===
            System.out.println("收到规划请求: Start(" + startLat + "," + startLng + ") End(" + endLat + "," + endLng + ")");

            // 1. 寻找最近的路网节点
            Integer startNode = routeMapper.findNearestNode(startLng, startLat);
            Integer endNode = routeMapper.findNearestNode(endLng, endLat);

            // 打印找出来的节点ID
            System.out.println("找到的节点ID: StartNode=" + startNode + ", EndNode=" + endNode);
            // === 调试日志 END ===

            if (startNode == null || endNode == null) {
                System.out.println("❌ 失败：找不到附近的道路节点");
                return null;
            }

            if (startNode.equals(endNode)) {
                System.out.println("⚠️ 警告：起点和终点吸附到了同一个路口，距离太近");
                // 这种情况下可以直接返回空，或者给前端一个提示
                return null;
            }

            // 2. 规划路径
            String geoJsonStr = routeMapper.shortestPath(startNode, endNode);

            // 打印结果长度，看有没有算出路
            System.out.println("路径计算结果长度: " + (geoJsonStr == null ? "NULL" : geoJsonStr.length()));

            if (geoJsonStr == null) return null;

            // 3. 包装成 GeoJSON Feature 格式返给前端
            JSONObject geometry = JSONObject.parseObject(geoJsonStr);
            JSONObject feature = new JSONObject();
            feature.put("type", "Feature");
            feature.put("properties", new JSONObject());
            feature.put("geometry", geometry);

            return feature;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}