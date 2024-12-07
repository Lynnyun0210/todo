package com.example.todo.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.TaskActivity;
import com.example.todo.adapter.Finished_taskAdapter;
import com.example.todo.adapter.HotTaskAdapter;
import com.example.todo.adapter.CategoryAdapter;
import com.example.todo.adapter.TaskAdapter;
import com.example.todo.entity.Finished_task;
import com.example.todo.entity.Task;
import com.example.todo.entity.Category;
import com.example.todo.sqlite.BusinessResult;
import com.example.todo.sqlite.FinishedTaskDB;
import com.example.todo.sqlite.TaskDB;
import com.example.todo.sqlite.CategoryDB;
import com.example.todo.utils.CurrentUserUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PointFragment extends Fragment {

    private RecyclerView rvCategory, rvHotTask;

    private EditText etSearch;

    private TextView tvDate;

    private CategoryAdapter CategoryAdapter;

    private HotTaskAdapter HotTaskAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragemnt_point, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindView();
        initData();
        initView();
    }

    private void bindView() {
        rvCategory = getView().findViewById(R.id.rv_category);
        rvHotTask = getView().findViewById(R.id.rv_important_task);
        etSearch = getView().findViewById(R.id.et_search);
        tvDate = getView().findViewById(R.id.tv_date);
    }

    private void initData() {
        List<Category> categories = CategoryDB.selectAll().getData();
        List<Task> tasks = TaskDB.getAllTasks().getData();
        if (categories == null ) {
            Log.e("PointFragment", "Category data source is empty!");
        } else {
            Log.d("PointFragment", "Category data source size: " + categories.size());
        }

        if (tasks == null || tasks.isEmpty()) {
            Log.e("PointFragment", "Task data source is empty!");
        } else {
            Log.d("PointFragment", "Task data source size: " + tasks.size());
        }

        // 检查数据源是否为空

            CategoryAdapter = new CategoryAdapter();
            CategoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
                @Override
                public void onClick(Category item) {
                    Intent intent = new Intent(getContext(), TaskActivity.class);
                    intent.putExtra("Category", item);
                    startActivity(intent);
                }
            });

        HotTaskAdapter = new HotTaskAdapter();
        rvHotTask.setAdapter(HotTaskAdapter);

        HotTaskAdapter.setOnItemClickListener(new HotTaskAdapter.OnItemClickListener() {
            @Override
            public void onFinished(Task item) {
                String name=item.getName();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Hint");
                builder.setMessage("You have finished：" + name );
                builder.setPositiveButton("NO", null);
                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Integer id=CurrentUserUtils.getCurrentUser().getId();
                        BusinessResult<Void> result;
                        result=TaskDB.delete(id);
                        HotTaskAdapter.setList(TaskDB.getAllTasks().getData());
                        HotTaskAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Finish this task successfully", Toast.LENGTH_SHORT).show();

                        String name1;
                        Finished_task finished_task = new Finished_task();
                        finished_task.setUserid(id);
                        finished_task.setTask_name(name);
                        name1=finished_task.getTask_name();
                        if(name1==null){
                            Log.e("PointFragment","no name input");
                        }
                        else{Log.e("PointFragment",name1);}
                        String payTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        finished_task.setFinished_time(payTime);
                        if(payTime==null){
                            Log.e("PointFragment","no  input");
                        }
                        else{Log.e("PointFragment",payTime);}
                        FinishedTaskDB.add(id,finished_task).getData();
                    }
                });
                builder.show();
            }
        });
    }

    private void initView() {
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategory.setAdapter(CategoryAdapter);
        rvHotTask.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        rvHotTask.setAdapter(HotTaskAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CategoryAdapter.setList(CategoryDB.selectByName(s.toString()).getData());
            }
        });
        // 设置日期 格式为 月 日
        tvDate.setText(getDate());
    }

    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("M/d");
        return sdf.format(System.currentTimeMillis());
    }

    @Override
    public void onResume() {
        super.onResume();
        HotTaskAdapter.setList(TaskDB.selectRandom().getData());
        CategoryAdapter.setList(CategoryDB.selectAll().getData());
    }


}

