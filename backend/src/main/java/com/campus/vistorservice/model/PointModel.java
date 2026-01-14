package com.campus.vistorservice.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
// import org.locationtech.jts.geom.Geometry;

public class PointModel implements Serializable {
    private Integer gid;
    private String name;

    // 数据库查出来的 GeoJSON 字符串放这里
    private String geometry;

    public Integer getGid() { return gid; }
    public void setGid(Integer gid) { this.gid = gid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGeometry() { return geometry; }
    public void setGeometry(String geometry) { this.geometry = geometry; }

    // 兼容旧代码，返回 null 即可，Controller 改用 geometry 字段
    public Object getGeom() { return null; }
}