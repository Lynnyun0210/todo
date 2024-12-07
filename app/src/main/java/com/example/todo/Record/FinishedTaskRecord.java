package com.example.todo.Record;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


// 数据库实体类，表示 BMI 记录
@Entity(tableName = "finished_task_records")
public class FinishedTaskRecord {
    @PrimaryKey(autoGenerate = true)
    public long id; // 唯一标识符
    public long task_id;
    public long user_id;
    public String name;

    public String timestamp; // 时间戳

    // 构造函数，用于创建 BMI 记录
    public FinishedTaskRecord(long task_id, long user_id, String name, String timestamp) {
        this.task_id = task_id;
        this.user_id = user_id;

        this.name = name;
        this.timestamp = timestamp;
    }

}