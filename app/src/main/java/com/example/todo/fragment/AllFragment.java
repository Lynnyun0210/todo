package com.example.todo.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.HistoryActivity;
import com.example.todo.LoginActivity;
import com.example.todo.R;
import com.example.todo.Repository.CategoryRepository;
import com.example.todo.Repository.TaskRepository;
import com.example.todo.TaskActivity;
import com.example.todo.adapter.HotTaskAdapter;
import com.example.todo.adapter.CategoryAdapter;
import com.example.todo.entity.Task;
import com.example.todo.entity.Category;
import com.example.todo.sqlite.BusinessResult;
import com.example.todo.sqlite.TaskDB;
import com.example.todo.sqlite.CategoryDB;
import com.example.todo.utils.CurrentUserUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class AllFragment extends Fragment {

    private String name;
    private RecyclerView rvCategory, rvHotTask;

    private EditText etSearch;

    private TextView tvDate;

    private ImageView ivadd;
    private boolean isEdit = false;
    private Category category;

    private CategoryAdapter CategoryAdapter,CategoryAdapter1;

    private HotTaskAdapter hotTaskAdapter;
    private Button historybutton;
    private CategoryRepository categoryRepository;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all, container, false);

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
        etSearch = getView().findViewById(R.id.et_search);
        tvDate = getView().findViewById(R.id.tv_date);
        ivadd=getView().findViewById(R.id.add_category);

    }

    private void initData() {
        CategoryAdapter = new CategoryAdapter();
        CategoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onClick(Category item) {
                Intent intent = new Intent(getContext(),TaskActivity.class);
                intent.putExtra("Category", item);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategory.setAdapter(CategoryAdapter);

        ivadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("New Category");
                final EditText input = new EditText(getContext());
                builder.setView(input);
                builder.setPositiveButton("Cancel", null);
                builder.setNegativeButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Category category = new Category(); // 确保初始化
                        category.setName(input.getText().toString()); // 设置名称
                        BusinessResult<Category> result;
                        result=CategoryDB.insert(category);
                        CategoryAdapter.setList(CategoryDB.selectAll().getData());

                        // 通知适配器数据已更改
                        CategoryAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();


            }
        });

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
        CategoryAdapter.setList(CategoryDB.selectAll().getData());
    }

}
