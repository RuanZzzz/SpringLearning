package com.richard.spring5;

/**
 * 使用set方法进行注入属性
 */
public class Book {
    // 创建属性
    private String bookName;
    private String author;
    private String address;
    // 创建属性对应的set方法
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public void testDemo() {
        System.out.println(bookName + "：" + author + "：" + address);
    }
}
