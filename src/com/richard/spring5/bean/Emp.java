package com.richard.spring5.bean;

public class Emp {
    // 员工名称
    private String ename;
    // 性别
    private String gender;
    // 员工属于某个部门，使用对象形式表示
    private Dept dept;

    public Dept getDept() {
        return dept;
    }
    public void setDept(Dept dept) {
        this.dept = dept;
    }
    public void setEname(String ename) {
        this.ename = ename;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void add() {
        System.out.println(ename + "：" + gender + "：" + dept);
    }
}
