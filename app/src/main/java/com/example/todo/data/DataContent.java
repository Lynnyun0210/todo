package com.example.todo.data;

import android.content.res.AssetManager;
import android.text.TextUtils;

import com.example.todo.entity.Finished_task;
import com.example.todo.entity.Task;
import com.example.todo.entity.Category;
import com.example.todo.sqlite.BusinessResult;
import com.example.todo.sqlite.FinishedTaskDB;
import com.example.todo.sqlite.TaskDB;
import com.example.todo.sqlite.CategoryDB;
import com.example.todo.utils.AppUtils;
import com.example.todo.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataContent {
    /**
     * 注意！！
     * 注意！！
     * 注意！！
     * 当前数据编辑以后，需要卸载app，重新安装才能生效
     */
    public static void init(){
        // 初始化数据
        BusinessResult<List<Category>> result = CategoryDB.selectAll();
        if (result.isSuccess() && !result.getData().isEmpty()) {
            return;
        }

        List<Category> CategoryAssetsList = getCategoryList();
        List<Task> TaskAssetsList = getTaskList();
        List<Finished_task> Finished_taskAssetsList =getFinished_taskList();
        for (Category Category : CategoryAssetsList) {
            Integer CategoryId = CategoryDB.insert(Category).getData().getId();



            //打乱菜品顺序
            for (int i = 0; i < TaskAssetsList.size(); i++) {
                int randomIndex = (int) (Math.random() * TaskAssetsList.size());
                Task temp = TaskAssetsList.get(i);
                TaskAssetsList.set(i, TaskAssetsList.get(randomIndex));
                TaskAssetsList.set(randomIndex, temp);
            }
            for (Task Task : TaskAssetsList) {
                Task.setCategoryId(CategoryId);
                TaskDB.insert(Task);
                Integer id=TaskDB.insert(Task).getData().getId();

            }


        }
    }

    /**
     * 使用gson从assets中读取Category.json的数据
     * 获取列表
     */
    private static List<Category> getCategoryList() {
        //读取assets中的Category.json文件
        String json = getJson("Category.json");
        //将json转换为List<Category>
        List<Category> CategoryList = JsonUtils.parse(json, new TypeToken<List<Category>>() {
        });

        return CategoryList;
    }
    /**
     * 使用gson从assets中读取Finished_task.json的数据
     * 获取列表
     */
    private static List<Finished_task> getFinished_taskList() {
        //读取assets中.json文件
        String json = getJson("Finished_task.json");
        //将json转换为List<>
        List<Finished_task> Finished_taskList = JsonUtils.parse(json, new TypeToken<List<Finished_task>>() {
        });

        return Finished_taskList;
    }

    /**
     * 获取任务列表
     */
    public static List<Task> getTaskList() {
        String json = getJson("Task.json");
        List<Task> TaskList = JsonUtils.parse(json, new TypeToken<List<Task>>() {
        });
        return TaskList;
    }

    private static String getJson(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = AppUtils.getApplication().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));

            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException var6) {
            var6.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
