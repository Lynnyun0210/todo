package com.example.todo.Record;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


// 数据库实体类，表示 BMI 记录
@Entity(tableName = "category_records")
public class CategoryRecord {
    @PrimaryKey(autoGenerate = true)
    public long id; // 唯一标识符
    public String name;

    // 构造函数，用于创建 BMI 记录
    public CategoryRecord(String name) {
        this.name=name;
    }

}