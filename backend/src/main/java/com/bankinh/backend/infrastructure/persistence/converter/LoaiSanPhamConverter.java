package com.bankinh.backend.infrastructure.persistence.converter;

import com.bankinh.backend.domain.model.LoaiSanPham;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class LoaiSanPhamConverter implements UserType<LoaiSanPham> {

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<LoaiSanPham> returnedClass() {
        return LoaiSanPham.class;
    }

    @Override
    public boolean equals(LoaiSanPham x, LoaiSanPham y) {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(LoaiSanPham x) {
        return Objects.hashCode(x);
    }

    @Override
    public LoaiSanPham nullSafeGet(ResultSet rs, int position,
                                   SharedSessionContractImplementor session,
                                   Object owner) throws SQLException {
        String value = rs.getString(position);
        if (rs.wasNull() || value == null) return null;
        return LoaiSanPham.valueOf(value);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, LoaiSanPham value, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.name(), Types.OTHER);
        }
    }

    @Override
    public LoaiSanPham deepCopy(LoaiSanPham value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(LoaiSanPham value) {
        return value;
    }

    @Override
    public LoaiSanPham assemble(Serializable cached, Object owner) {
        return (LoaiSanPham) cached;
    }
}