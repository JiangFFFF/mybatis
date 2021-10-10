package com.jiang.mybatis.bean;

/**
 * @author jiang
 * @create 2021-09-26-3:32 下午
 */
public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    private String gender;
    private EmpStatus empStatus=EmpStatus.LOGOUT;

    public EmpStatus getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(EmpStatus empStatus) {
        this.empStatus = empStatus;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    @Override
    public String toString() {
        return "Employee [id=" + id + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender + "]";
    }

    public Employee() {

    }

    public Employee(String lastName, String email, String gender) {
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }
}
