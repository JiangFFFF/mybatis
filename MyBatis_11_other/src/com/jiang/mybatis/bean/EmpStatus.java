package com.jiang.mybatis.bean;

/**
 * 希望数据库保存的是状态码
 * @author jiang
 * @create 2021-09-30-8:50 上午
 */
public enum EmpStatus {

    LOGIN(100,"用户登录"),
    LOGOUT(200,"用户登出"),
    REMOVE(300,"用户不存在");

    private Integer code;
    private String msg;

    private EmpStatus(Integer code , String msg) {
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //按照状态码返回枚举对象
    public static EmpStatus getEmpsStatusByCode(Integer code){
        switch (code){
            case 100:
                return LOGIN;
            case 200:
                return LOGOUT;
            case 300:
                return REMOVE;
            default:
                return LOGOUT;
        }
    }
}
