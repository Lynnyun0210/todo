package com.example.todo.entity;

import java.io.Serializable;

/*
        子任务表(_task)：
        _id          integer  taskid
        Category_id      integer  Categoryid
        name  varchar  taskname
         */
public class Task implements Serializable {
    private Integer id;
    private Integer CategoryId;
    private String name;
    private String CategoryName;
    public Task(String CategoryName,String name){
        this.CategoryName = CategoryName;
        this.name = name;
    }
    public Task(){

    }
    public String getCategoryName(){
        return CategoryName;
    }
    public Integer getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCategoryid() {
        return CategoryId;
    }

    public void setCategoryName(String categoryName) {

        CategoryName = categoryName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCategoryId(Integer CategoryId) {
        this.CategoryId = CategoryId;
    }

    public void setName(String name) {
        this.name = name;
    }
}

