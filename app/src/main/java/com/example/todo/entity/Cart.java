package com.example.todo.entity;

public class Cart {

    private Integer id;

    private Integer userId;

    private Integer foodId;

    private Integer count;

    private Task task;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskId() {
        return foodId;
    }

    public void setTaskId(Integer foodId) {
        this.foodId = foodId;
    }


    public Task getTask() {
        return task;
    }

    public void setFood(Task task) {
        this.task= task;
    }
}
