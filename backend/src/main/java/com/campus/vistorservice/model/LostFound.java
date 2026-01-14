package com.campus.vistorservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

public class LostFound implements Serializable {
    private Integer id;
    private String itemName;
    private String description;
    private String contact;
    private String lostType;
    private Integer status;
    private String visitorName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Double longitude;
    private Double latitude;
    private String geometry; // 接收 GeoJSON

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getLostType() { return lostType; }
    public void setLostType(String lostType) { this.lostType = lostType; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getVisitorName() { return visitorName; }
    public void setVisitorName(String visitorName) { this.visitorName = visitorName; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public String getGeometry() { return geometry; }
    public void setGeometry(String geometry) { this.geometry = geometry; }
}