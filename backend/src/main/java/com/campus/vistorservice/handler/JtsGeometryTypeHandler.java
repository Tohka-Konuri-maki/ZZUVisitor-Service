package com.campus.vistorservice.handler; // 包路径必须和yml中扫描的一致

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自定义JTS几何类型处理器（适配PostgreSQL PostGIS）
 */
public class JtsGeometryTypeHandler extends BaseTypeHandler<Geometry> {
    private final WKTReader wktReader = new WKTReader();
    private final WKTWriter wktWriter = new WKTWriter();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Geometry parameter, JdbcType jdbcType) throws SQLException {
        PGobject pgObj = new PGobject();
        pgObj.setType("geometry");
        pgObj.setValue(wktWriter.write(parameter));
        ps.setObject(i, pgObj);
    }

    @Override
    public Geometry getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseGeometry(rs.getObject(columnName));
    }

    @Override
    public Geometry getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseGeometry(rs.getObject(columnIndex));
    }

    @Override
    public Geometry getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseGeometry(cs.getObject(columnIndex));
    }

    private Geometry parseGeometry(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PGobject pgObj) {
            try {
                return wktReader.read(pgObj.getValue());
            } catch (Exception e) {
                throw new IllegalArgumentException("解析几何类型失败：" + pgObj.getValue(), e);
            }
        }
        if (obj instanceof String wkt) {
            try {
                return wktReader.read(wkt);
            } catch (Exception e) {
                throw new IllegalArgumentException("解析WKT字符串失败：" + wkt, e);
            }
        }
        throw new IllegalArgumentException("不支持的几何类型：" + obj.getClass().getName());
    }
}