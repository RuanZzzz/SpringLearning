package com.richard.spring5.bean;

public class Dept {
    // 部门名称
    private String dname;
    public void setDname(String dname) {
        this.dname = dname;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "dname='" + dname + '\'' +
                '}';
    }
}
