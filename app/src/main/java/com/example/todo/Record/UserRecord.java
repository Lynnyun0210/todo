package com.example.todo.Record;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


// 数据库实体类，表示 BMI 记录
@Entity(tableName = "user_records")
public class UserRecord {
    @PrimaryKey(autoGenerate = true)
    public long id; // 唯一标识符
    public String username;
    public String password; // 时间戳

    // 构造函数，用于创建 BMI 记录
    public UserRecord(String username,String password) {


        this.username = username;
        this.password = password;
    }

}