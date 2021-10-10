package com.jiang.mybatis.typehandler;

import com.jiang.mybatis.bean.EmpStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jiang
 * @create 2021-09-30-9:27 上午
 */

public class MyEnumEmployeeStatusTypeHandler implements TypeHandler<EmpStatus> {


    /**
     * 定义当前数据如何到数据库中
     * @param preparedStatement
     * @param i
     * @param empStatus
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, EmpStatus empStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i,empStatus.getCode().toString());
    }

    @Override
    public EmpStatus getResult(ResultSet resultSet, String s) throws SQLException {
        //需要根据从数据库中拿到的枚举的状态码返回一个枚举对象
        int code = resultSet.getInt(s);
        EmpStatus status = EmpStatus.getEmpsStatusByCode(code);
        return status;
    }

    @Override
    public EmpStatus getResult(ResultSet resultSet, int i) throws SQLException {
        int code = resultSet.getInt(i);
        EmpStatus status = EmpStatus.getEmpsStatusByCode(code);
        return status;
    }

    @Override
    public EmpStatus getResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        EmpStatus status = EmpStatus.getEmpsStatusByCode(code);
        return status;
    }
}
