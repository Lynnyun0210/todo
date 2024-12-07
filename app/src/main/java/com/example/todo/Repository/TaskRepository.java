package com.example.todo.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.todo.Dao.TaskDao;
import com.example.todo.Record.TaskRecord;
import com.example.todo.data.todoAppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 数据库操作的仓库类，处理与 BMIDao 的交互
public class TaskRepository {
    private final TaskDao taskDao; // 数据访问对象

    private final ExecutorService executorService; // 用于数据库操作的线程池

    // 构造函数，初始化数据库和 DAO
    public TaskRepository(Context context) {
        todoAppDatabase db = todoAppDatabase.getInstance(context);
        this.taskDao = db.taskDao();
        this.executorService = Executors.newSingleThreadExecutor(); // 创建单线程执行器
    }

    // 插入记录
    public void insertTaskRecord(long type_id,  String name) {
        TaskRecord record = new TaskRecord(type_id, name);
        executorService.execute(() -> taskDao.insert(record)); // 在子线程中执行插入操作
    }

    // 获取所有 BMI 记录
    public LiveData<List<TaskRecord>> getAllBMIRecords() {
        return taskDao.getAllRecords(); // 返回 LiveData 以便观察
    }

    // 清空所有 BMI 记录
    public void clearBMIRecords() {
        executorService.execute(taskDao::clearAllRecords); // 在子线程中执行清空操作
    }
}