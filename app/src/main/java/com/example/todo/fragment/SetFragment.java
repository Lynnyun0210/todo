package com.example.todo.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.LoginActivity;
import com.example.todo.R;
import com.example.todo.TaskActivity;
import com.example.todo.adapter.Finished_taskAdapter;
import com.example.todo.adapter.HotTaskAdapter;
import com.example.todo.adapter.CategoryAdapter;
import com.example.todo.entity.Finished_task;
import com.example.todo.entity.Task;
import com.example.todo.entity.Category;
import com.example.todo.sqlite.BusinessResult;
import com.example.todo.sqlite.FinishedTaskDB;
import com.example.todo.sqlite.TaskDB;
import com.example.todo.sqlite.CategoryDB;
import com.example.todo.utils.CurrentUserUtils;
import com.example.todo.utils.TitleBarUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class SetFragment extends Fragment {

    private RecyclerView rv;
    private TextView tvUsername;
    private Finished_taskAdapter Finished_taskAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TitleBarUtils.setTitle(this, "Center");
        TitleBarUtils.setRight(this, "Sign out", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Hint");
                builder.setMessage("Confirm to sign out");
                builder.setPositiveButton("No", null);
                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FragmentActivity activity = getActivity();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });
                builder.show();

            }
        });
        bindView();
        initData();
        initView();
    }

    private void bindView() {
        tvUsername = getView().findViewById(R.id.tv_username);
        rv = getView().findViewById(R.id.rv);
    }

    private void initData() {
        Finished_taskAdapter = new Finished_taskAdapter();
    }

    private void initView() {
        tvUsername.setText(CurrentUserUtils.getCurrentUser().getUsername());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(Finished_taskAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        //Integer id=CurrentUserUtils.getCurrentUser().getId();
        BusinessResult<List<Finished_task>> result = FinishedTaskDB.queryByUserId(CurrentUserUtils.getCurrentUser().getId());
        Finished_taskAdapter.setList(result.getData());
        //Finished_taskAdapter.notifyDataSetChanged();


    }
}
