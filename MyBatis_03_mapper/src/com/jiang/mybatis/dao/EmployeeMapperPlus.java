package com.jiang.mybatis.dao;

import com.jiang.mybatis.bean.Employee;

import java.util.List;

/**
 * @author jiang
 * @create 2021-09-27-11:35 上午
 */
public interface EmployeeMapperPlus {
    public Employee getEmpById(Integer id);

    public Employee getEmpAndDept(Integer id);

    public Employee getEmpBuIdStep(Integer id);

    public List<Employee> getEmpsByDeptId(Integer deptId);
}
