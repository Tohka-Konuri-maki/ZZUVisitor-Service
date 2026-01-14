package com.campus.vistorservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class Suggestion {
    private Integer id;
    private String visitorName;
    private String content;

    // 格式化时间，方便前端直接显示
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getVisitorName() { return visitorName; }
    public void setVisitorName(String visitorName) { this.visitorName = visitorName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}