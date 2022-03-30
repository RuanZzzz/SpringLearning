package com.richard.spring5;

/**
 * 有参构造注入
 */
public class Orders {
    // 属性
    private String orderName;
    private String address;
    // 有参构造
    public Orders(String orderName, String address) {
        this.orderName = orderName;
        this.address = address;
    }

    public void orderTest() {
        System.out.println(orderName + "：" + address);
    }

}
