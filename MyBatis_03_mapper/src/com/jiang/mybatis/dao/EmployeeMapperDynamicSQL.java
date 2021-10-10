package com.jiang.mybatis.dao;

import com.jiang.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jiang
 * @create 2021-09-27-5:22 下午
 */
public interface EmployeeMapperDynamicSQL {
    //携带了哪个字段 查询条件就带上这个字段的值
    public List<Employee> getEmpsByConditionIf(Employee employee);

    public List<Employee> getEmpsByConditionTrim(Employee employee);

    public List<Employee> getEmpsByConditionTChoose(Employee employee);

    public void updateEmp(Employee employee);

    //查询员工id在给定集合中
    public List<Employee> getEmpsByConditionForeach(@Param("ids") List<Integer> ids);

    public void addEmps(@Param("emps") List<Employee> emps);

    public List<Employee> getEmpsTestInnerParameter(Employee employee);

}
