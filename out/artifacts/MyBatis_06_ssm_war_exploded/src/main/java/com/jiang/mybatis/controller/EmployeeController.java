package com.jiang.mybatis.controller;

import com.jiang.mybatis.bean.Employee;
import com.jiang.mybatis.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author jiang
 * @create 2021-09-29-9:21 上午
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/getemps")
    public String emps(Model model){
        List<Employee> emps = employeeService.getEmps();
        model.addAttribute("allEmps",emps);
        return "list";
    }
}
