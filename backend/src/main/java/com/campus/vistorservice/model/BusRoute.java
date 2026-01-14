package com.campus.vistorservice.model;
import java.io.Serializable;

public class BusRoute implements Serializable {
    private Integer id;
    private String name;
    private String stops;
    private String schedule;
    private String geometry; // 接收 GeoJSON

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStops() { return stops; }
    public void setStops(String stops) { this.stops = stops; }
    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
    public String getGeometry() { return geometry; }
    public void setGeometry(String geometry) { this.geometry = geometry; }
}