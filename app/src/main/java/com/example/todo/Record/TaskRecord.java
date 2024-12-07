package com.example.todo.Record;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


// 数据库实体类，表示 BMI 记录
@Entity(tableName = "task_records")
public class TaskRecord {
    @PrimaryKey(autoGenerate = true)
    public long id; // 唯一标识符
    public long type_id;
    public String name;


    // 构造函数，用于创建 BMI 记录
    public TaskRecord(long type_id,  String name) {


        this.type_id = type_id;
        this.name = name;
    }

}