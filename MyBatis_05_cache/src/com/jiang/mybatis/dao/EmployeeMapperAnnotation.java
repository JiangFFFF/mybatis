package com.jiang.mybatis.dao;

import com.jiang.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Select;

/**
 * @author jiang
 * @create 2021-09-26-5:29 下午
 */
public interface EmployeeMapperAnnotation {

    @Select("select * from tbl_employee where id=#{id}")
    public Employee getEmployeeId(Integer id);
}

