package com.example.todo.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todo.Record.CategoryRecord;

import java.util.List;

// 数据访问对象 (DAO) 接口，用于定义与 BMI 记录的数据库操作
@Dao
public interface CategoryDao {
    // 插入一条记录
    @Insert
    void insert(CategoryRecord record);

    // 查询所有记录，返回 LiveData 类型以便观察
    @Query("SELECT * FROM category_records ORDER BY id DESC")
    LiveData<List<CategoryRecord>> getAllRecords();

    // 清空所有记录
    @Query("DELETE FROM category_records")
    void clearAllRecords();
}