package com.bankinh.backend.infrastructure.persistence.converter;

import com.bankinh.backend.domain.model.VaiTro;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class VaiTroConverter implements UserType<VaiTro> {

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<VaiTro> returnedClass() {
        return VaiTro.class;
    }

    @Override
    public boolean equals(VaiTro x, VaiTro y) {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(VaiTro x) {
        return Objects.hashCode(x);
    }

    @Override
    public VaiTro nullSafeGet(ResultSet rs, int position,
                               SharedSessionContractImplementor session,
                               Object owner) throws SQLException {
        String value = rs.getString(position);
        if (rs.wasNull() || value == null) return null;
        return VaiTro.valueOf(value);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, VaiTro value, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.name(), Types.OTHER);
        }
    }

    @Override
    public VaiTro deepCopy(VaiTro value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(VaiTro value) {
        return value;
    }

    @Override
    public VaiTro assemble(Serializable cached, Object owner) {
        return (VaiTro) cached;
    }
}