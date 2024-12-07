package com.example.todo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todo.R;
import com.example.todo.Record.TaskRecord;

import java.util.List;

// 自定义适配器，用于显示历史记录的列表
public class HistoryAdapter extends ArrayAdapter<TaskRecord> {
    // 构造函数，初始化适配器
    public HistoryAdapter(Context context, List<TaskRecord> records) {
        super(context, 0, records);
    }

    // 获取视图并填充数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 如果没有现成的视图，创建一个新的视图
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }
        TaskRecord record = getItem(position); // 获取当前记录
        if (record != null) {
            // 设置视图中的文本
           // ((ImageView) convertView.findViewById(R.id.dot)).setText("Height: " + record.height + " cm");
            ((TextView) convertView.findViewById(R.id.task_information)).setText("Task: " + record.name );
        }
        return convertView; // 返回填充好的视图
    }
}