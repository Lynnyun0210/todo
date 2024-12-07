package com.example.todo;

import static java.security.AccessController.getContext;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.adapter.CategoryAdapter;
import com.example.todo.adapter.TaskAdapter;
import com.example.todo.entity.Category;
import com.example.todo.entity.Finished_task;
import com.example.todo.entity.Task;
import com.example.todo.sqlite.BusinessResult;
import com.example.todo.sqlite.CategoryDB;
import com.example.todo.sqlite.FinishedTaskDB;
import com.example.todo.sqlite.TaskDB;
import com.example.todo.utils.CurrentUserUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    private ImageView addBtn;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private TaskDB taskDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);

        // 初始化 RecyclerView
        recyclerView = findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始化适配器并设置给 RecyclerView
        taskAdapter = new TaskAdapter();
        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onClick(Task item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
                Integer id = CurrentUserUtils.getCurrentUser().getId();
                builder.setTitle("Manage Task");

                final EditText input = new EditText(TaskActivity.this);
                input.setText(item.getName()); // 设置初始值为当前任务名称
                builder.setView(input);

                builder.setNeutralButton("Finished", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BusinessResult<Void> result1 = TaskDB.delete(item.getId()); // 使用任务的ID
                        if (result1.isSuccess()) {
                            taskAdapter.setList(TaskDB.getAllTasks().getData());
                            taskAdapter.notifyDataSetChanged();
                            Toast.makeText(TaskActivity.this, "Finish this task successfully", Toast.LENGTH_SHORT).show();

                            String name = item.getName();
                            Finished_task finished_task = new Finished_task();
                            finished_task.setUserid(id);
                            finished_task.setTask_name(name);
                            String payTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            finished_task.setFinished_time(payTime);

                            BusinessResult<Finished_task> addResult = FinishedTaskDB.add(id, finished_task);
                            if (!addResult.isSuccess()) {
                                Log.e("TaskActivity", "Failed to add finished task");
                            }
                        } else {
                            Log.e("TaskActivity", "Failed to delete task");
                        }
                    }
                });

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BusinessResult<Void> result2 = TaskDB.delete(item.getId()); // 使用任务的ID
                        if (result2.isSuccess()) {
                            taskAdapter.setList(TaskDB.getAllTasks().getData());
                            taskAdapter.notifyDataSetChanged();
                            Toast.makeText(TaskActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("TaskActivity", "Failed to delete task");
                        }
                    }
                });

                builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newTaskName = input.getText().toString();
                        item.setName(newTaskName);

                        BusinessResult<Void> result1 = TaskDB.update(item);
                        if (result1.isSuccess()) {
                            taskAdapter.setList(TaskDB.getAllTasks().getData());
                            taskAdapter.notifyDataSetChanged();
                            Toast.makeText(TaskActivity.this, "Edit successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("TaskActivity", "Failed to update task");
                        }
                    }
                });

                builder.show();
            }
        });


        recyclerView.setAdapter(taskAdapter);

        // 初始化数据库
        taskDB = new TaskDB();

        // 获取任务数据并设置到适配器
        loadTasks();

        addBtn = findViewById(R.id.btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
                builder.setTitle("New Task");
                final EditText input = new EditText(TaskActivity.this);
                builder.setView(input);
                builder.setPositiveButton("Cancel", null);
                builder.setNegativeButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Task task = new Task(); // 确保初始化
                        task.setName(input.getText().toString()); // 设置名称
                        BusinessResult<Task> result;
                        result = TaskDB.insert(task);
                        taskAdapter.setList(TaskDB.getAllTasks().getData());
                        // 通知适配器数据已更改
                        taskAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });
    }

    private void loadTasks() {
        // 从数据库获取任务数据
        BusinessResult<List<Task>> result = taskDB.getAllTasks();
        if (result.isSuccess()) {
            List<Task> tasks = result.getData();
            for (Task task : tasks) {
                Log.d("TaskActivity", "Task: " + task.getCategoryName() + ", " + task.getName());
            }
            taskAdapter.setList(tasks);
        } else {
            // 处理错误情况，例如显示错误信息
            // Toast.makeText(this, "Failed to load tasks", Toast.LENGTH_SHORT).show();
        }
    }
}