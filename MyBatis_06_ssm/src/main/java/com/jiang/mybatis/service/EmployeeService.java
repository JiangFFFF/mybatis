package com.jiang.mybatis.service;

import com.jiang.mybatis.bean.Employee;
import com.jiang.mybatis.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.soap.Addressing;
import java.util.List;

/**
 * @author jiang
 * @create 2021-09-29-9:27 上午
 */
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> getEmps(){
        return employeeMapper.getEmps();
    }
}
