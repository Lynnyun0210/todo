package com.example.todo.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.todo.DBHelper;
import com.example.todo.R;
import com.example.todo.Repository.TaskRepository;
import com.example.todo.fragment.AllFragment;
import com.example.todo.fragment.PointFragment;
import com.example.todo.fragment.SetFragment;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> fragmentList;

    private ViewPager2 pager;
    private DBHelper dbhelper,dbhelper1;
    private SQLiteDatabase db,db1;

    private NavigationBarView bottomNavigation;
    private TaskRepository dbHelper; // 数据库助手

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskRepository(this);

        SQLiteStudioService.instance().start(this);
        dbhelper=new DBHelper(MainActivity.this);
        db=dbhelper.getWritableDatabase();


        fragmentList = new ArrayList<>();
        fragmentList.add(new AllFragment());
        fragmentList.add(new PointFragment());
        fragmentList.add(new SetFragment());

        bindView();
        initView();
    }

    private void bindView() {
        pager = findViewById(R.id.page);
        bottomNavigation = findViewById(R.id.bottom_navigation);
    }

    private void initView() {

        pager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        });
        pager.setOffscreenPageLimit(fragmentList.size());

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bottomNavigation.getMenu().getItem(position).setChecked(true);
            }
        });
        pager.setUserInputEnabled(false);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                pager.setCurrentItem(item.getOrder(), false);
                return true;
            }
        });
    }
}