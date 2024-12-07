package com.example.todo.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todo.Dao.CategoryDao;
import com.example.todo.Dao.FinishedTaskDao;
import com.example.todo.Dao.TaskDao;
import com.example.todo.Record.CategoryRecord;
import com.example.todo.Record.FinishedTaskRecord;
import com.example.todo.Record.TaskRecord;

// 数据库类，使用 Room 进行数据库管理
@Database(entities = {TaskRecord.class, CategoryRecord.class, FinishedTaskRecord.class}, version = 1, exportSchema = false)
public abstract class todoAppDatabase extends RoomDatabase {
    // 获取 DAO 对象
    public abstract TaskDao taskDao();
    public abstract CategoryDao categoryDao();
    public abstract FinishedTaskDao finishedtaskDao();

    private static volatile todoAppDatabase instance; // 数据库实例

    // 单例模式获取数据库实例
    public static todoAppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (todoAppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    todoAppDatabase.class, "Todo_database.db")
                            .fallbackToDestructiveMigration() // 允许数据库破坏性迁移
                            .build();
                }
            }
        }
        return instance;
    }
}