package com.jiang.mybatis.dao;

import com.jiang.mybatis.bean.Department;

/**
 * @author jiang
 * @create 2021-09-27-2:34 下午
 */
public interface DepartmentMapper {
    public Department getDepartById(Integer id);

    public Department getDeptByIdPlus(Integer id);

    public Department getDeptByIdStep(Integer id);
}
