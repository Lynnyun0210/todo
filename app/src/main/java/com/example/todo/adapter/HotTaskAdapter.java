package com.example.todo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.todo.R;
import com.example.todo.entity.Task;
import com.example.todo.sqlite.BusinessResult;
import com.example.todo.sqlite.TaskDB;
import com.example.todo.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;

public class HotTaskAdapter extends RecyclerView.Adapter<HotTaskAdapter.ViewHolder> {

    private final List<Task> list = new ArrayList<>();

    private OnItemClickListener onItemClickListener;
    private TaskAdapter taskAdapter;
    private TaskDB taskDB;


    @NonNull
    @Override
    public HotTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_important_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotTaskAdapter.ViewHolder holder, int position) {
        List<Task> tasks = TaskDB.getAllTasks().getData();
        Task item = list.get(position);
        if (item.getName()==null){
            Log.e("HotTaskAdapter", "data source is empty!");
        } else {
            Log.d("HotTaskAdapter", "data source size: " + tasks.size());
        }
        Log.d("TaskAdapter", "Binding Task: " + item.getCategoryName() + ", " + item.getName());
        holder.tvName.setText(item.getCategoryName());
        holder.tvName2.setText(item.getName());
        holder.ivfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onFinished(item);
                }
            }
        });
    }

    public void setList(List<Task> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    //at point-fragment page need it

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onFinished(Task item);
    }
    public void clear() {
        // 找到任务ID对应的任务并将其移除
        this.list.clear();
        notifyDataSetChanged();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName, tvName2;

        private final ImageView ivfinish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.type_name);
            tvName2 = itemView.findViewById(R.id.task_name);
            ivfinish = itemView.findViewById(R.id.iv_finish);
        }
    }


}

