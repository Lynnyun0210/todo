package com.example.todo.entity;

import org.w3c.dom.Text;

import java.io.Serializable;
/*
       Finished_task表(_finished_task)：
       name         varchar  taskname
       _id          integer  taskid
       user_id      integer  用户id
       finished_time     text     finished时间
       total_task_number  integer     完成任务的数量
        */
public class Finished_task implements Serializable {
    Integer id;
    Integer userid;
    String finished_time;

    String task_name;

    public Integer getId() {
        return id;
    }


    public Integer getUserid() {
        return userid;
    }

    public String getTask_name() {
        return task_name;
    }
    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getFinished_time() {

        return finished_time;
    }

    public void setFinished_time(String finished_time) {
        this.finished_time = finished_time;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
