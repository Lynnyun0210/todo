package com.example.todo;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.Record.TaskRecord;
import com.example.todo.Repository.TaskRepository;
import com.example.todo.adapter.HistoryAdapter;

import java.util.List;

// 历史记录活动类，显示用户的 BMI 记录
public class HistoryActivity extends AppCompatActivity {
    private TaskRepository taskRepository; // 数据库助手
    private ListView historyList; // 显示历史记录的 ListView
    private HistoryAdapter adapter; // 自定义适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history); // 设置布局

        taskRepository = new TaskRepository(this); // 初始化数据库助手
        historyList = findViewById(R.id.history_list); // 获取 ListView 实例

        // 使用 LiveData 观察 BMI 记录的变化
        taskRepository.getAllBMIRecords().observe(this, this::updateHistoryList);
    }

    // 更新历史记录列表
    private void updateHistoryList(List<TaskRecord> records) {
        if (records == null || records.isEmpty()) {
            Toast.makeText(this, "No task records found.", Toast.LENGTH_SHORT).show();
            return; // 如果没有记录，显示提示并返回
        }
        adapter = new HistoryAdapter(this, records); // 创建适配器
        historyList.setAdapter(adapter); // 设置适配器到 ListView
    }
}