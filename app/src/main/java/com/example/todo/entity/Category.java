package com.example.todo.entity;

import java.io.Serializable;
/*
        类型表(_type)：
        _id          integer  typeid
        name         varchar  type名称
        img          varchar  type图片url
         */

public class Category implements Serializable {
    private Integer id;
    private String name;
    private String img;

    public Integer getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getImg(){
        return img;
    }
    public void setId(Integer id){
        this.id=id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
