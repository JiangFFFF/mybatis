package com.jiang.mybatis.dao;

import com.jiang.mybatis.bean.Employee;

import java.util.List;

public interface EmployeeMapper {
	
	public Employee getEmployeeId(Integer id);

	public List<Employee> getEmps();

	public Long addEmp(Employee employee);

}
