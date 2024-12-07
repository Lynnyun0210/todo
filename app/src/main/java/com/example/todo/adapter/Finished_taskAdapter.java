package com.example.todo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.todo.R;
import com.example.todo.entity.Finished_task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Finished_taskAdapter extends RecyclerView.Adapter<Finished_taskAdapter.ViewHolder> {

    private final List<Finished_task> list = new ArrayList<>();

    @NonNull
    @Override
    public Finished_taskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finished_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Finished_taskAdapter.ViewHolder holder, int position) {
        Finished_task item = list.get(position);
        String payTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        item.setTask_name(payTime);

        holder.tvName.setText("The task_name：" + item.getFinished_time());
        if(item.getFinished_time()!=null){
            Log.e("Finished_task_adapter",item.getFinished_time());
        }
        holder.tvTime.setText("The finished time："+item.getTask_name());
    }

    public void setList(List<Finished_task> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName, tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);

        }
    }
}
