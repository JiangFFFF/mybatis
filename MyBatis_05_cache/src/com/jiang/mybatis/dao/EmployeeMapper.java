package com.jiang.mybatis.dao;

import com.jiang.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

	public Employee getEmployeeId(Integer id);
	public Long addEmp(Employee employee);
	public boolean updateEmp(Employee employee);
	public void deleteEmpById(Integer id);
	public Employee getEmpByIdAndLastName(@Param("id")Integer id,@Param("lastName") String lastName);

	public Employee getEmpMap(Map<String,Object> map);

	public List<Employee> getEmpsByLastNameLike(String lastName);

	//返回一条记录的map，key就是列名，值就是对应的值
	public Map<String,Object> getEmpByIdReturnMap(Integer id);

	//多条记录封装一个map:Map<Integer,Employee>:键是该条记录的主键，值是记录封装后的javaBean
	//告诉mybatis封装这个map的时候使用哪个属性作为主键
	@MapKey("id")
	public Map<Integer,Employee> getEmpByLastNameLikeReturnMap(String lastName);




}
