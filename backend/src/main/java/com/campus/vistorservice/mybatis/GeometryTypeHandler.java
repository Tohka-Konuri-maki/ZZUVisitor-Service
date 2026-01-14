package com.campus.vistorservice.mybatis;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Geometry.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class GeometryTypeHandler extends BaseTypeHandler<Geometry> {

    private final WKBReader wkbReader = new WKBReader();
    private final WKBWriter wkbWriter = new WKBWriter();

    // 核心修复：手动实现字节数组转十六进制字符串（替代WKBReader.toHex）
    private String bytesToHex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b)); // 转大写十六进制，兼容PostgreSQL
        }
        return sb.toString();
    }

    // 核心修复：手动实现十六进制字符串转字节数组（替代WKBReader.hexToBytes）
    private byte[] hexToBytes(String hex) {
        if (hex == null || hex.isEmpty()) {
            return null;
        }
        int len = hex.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Geometry geometry, JdbcType jdbcType) throws SQLException {
        PGobject pgObject = new PGobject();
        pgObject.setType("geometry");
        try {
            byte[] wkbBytes = wkbWriter.write(geometry);
            // 替换WKBReader.toHex → 手动实现的bytesToHex
            pgObject.setValue(bytesToHex(wkbBytes));
        } catch (Exception e) {
            throw new SQLException("Failed to convert JTS Geometry to PostgreSQL WKB", e);
        }
        ps.setObject(i, pgObject);
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

    private Geometry parseGeometry(Object obj) throws SQLException {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof PGobject pgGeometry)) {
            throw new SQLException("Expected PGobject type for geometry column, but got: " + obj.getClass().getName());
        }
        String value = pgGeometry.getValue();
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            // 替换WKBReader.hexToBytes → 手动实现的hexToBytes
            return wkbReader.read(hexToBytes(value));
        } catch (ParseException e) {
            throw new SQLException("Failed to parse geometry value: " + value, e);
        }
    }
}