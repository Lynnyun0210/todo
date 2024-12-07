package com.example.todo.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todo.Record.FinishedTaskRecord;

import java.util.List;

// 数据访问对象 (DAO) 接口，用于定义与 BMI 记录的数据库操作
@Dao
public interface FinishedTaskDao {
    // 插入一条 BMI 记录
    @Insert
    void insert(FinishedTaskRecord record);

    // 查询所有 BMI 记录，返回 LiveData 类型以便观察
    @Query("SELECT * FROM finished_task_records ORDER BY timestamp DESC")
    LiveData<List<FinishedTaskRecord>> getAllRecords();

    // 清空所有 BMI 记录
    @Query("DELETE FROM finished_task_records")
    void clearAllRecords();
}