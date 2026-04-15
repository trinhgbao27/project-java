package com.bankinh.backend.infrastructure.persistence.converter;

import com.bankinh.backend.domain.model.TrangThaiDonHang;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class TrangThaiDonHangConverter implements UserType<TrangThaiDonHang> {

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<TrangThaiDonHang> returnedClass() {
        return TrangThaiDonHang.class;
    }

    @Override
    public boolean equals(TrangThaiDonHang x, TrangThaiDonHang y) {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(TrangThaiDonHang x) {
        return Objects.hashCode(x);
    }

    @Override
    public TrangThaiDonHang nullSafeGet(ResultSet rs, int position,
                                        SharedSessionContractImplementor session,
                                        Object owner) throws SQLException {
        String value = rs.getString(position);
        if (rs.wasNull() || value == null) return null;
        return TrangThaiDonHang.valueOf(value);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, TrangThaiDonHang value, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.name(), Types.OTHER);
        }
    }

    @Override
    public TrangThaiDonHang deepCopy(TrangThaiDonHang value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(TrangThaiDonHang value) {
        return value;
    }

    @Override
    public TrangThaiDonHang assemble(Serializable cached, Object owner) {
        return (TrangThaiDonHang) cached;
    }
}