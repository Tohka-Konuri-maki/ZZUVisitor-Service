package com.campus.vistorservice.dao;

import com.campus.vistorservice.model.BusRoute;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BusRouteMapper {
    List<BusRoute> findAll();
}