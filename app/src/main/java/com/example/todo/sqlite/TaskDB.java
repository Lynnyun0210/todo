package com.example.todo.sqlite;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.todo.entity.Task;
import com.example.todo.utils.SqliteUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressLint("Range")
public class TaskDB {

    /**
     * 根据店铺id查询菜品
     */
    public static BusinessResult<List<Task>> selectByCategoryId(Integer CategoryId) {
        BusinessResult<List<Task>> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from _task where type_id=? order by _id desc", new String[]{String.valueOf(CategoryId)});
        List<Task> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int TaskId = cursor.getInt(cursor.getColumnIndex("_id"));
            Task Task = new Task();
            Task.setId(TaskId);
            Task.setCategoryId(cursor.getInt(cursor.getColumnIndex("type_id")));
            Task.setName(cursor.getString(cursor.getColumnIndex("name")));
            list.add(Task);
        }
        cursor.close();
        result.setSuccess(true);
        result.setMessage("Search successfully");
        result.setData(list);
        return result;
    }

    /**
     * 随机查询5个菜品
     */
    public static BusinessResult<List<Task>> selectRandom() {
        BusinessResult<List<Task>> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from _task order by random() limit 5", null);
        List<Task> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int TaskId = cursor.getInt(cursor.getColumnIndex("_id"));
            Task Task = new Task();
            Task.setId(TaskId);
            Task.setCategoryId(cursor.getInt(cursor.getColumnIndex("type_id")));
            Task.setName(cursor.getString(cursor.getColumnIndex("name")));
            list.add(Task);
        }
        cursor.close();
        result.setSuccess(true);
        result.setMessage("Search successfully");
        result.setData(list);
        return result;
    }
    /**
     * 输出全部的菜品记录
     */
    public static BusinessResult<List<Task>> getAllTasks(){
        BusinessResult<List<Task>> result=new BusinessResult<>();
        SQLiteDatabase db=SqliteUtils.getInstance().getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from _task ",null);

        List<Task> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int TaskId = cursor.getInt(cursor.getColumnIndex("_id"));
            Task Task = new Task();
            Task.setId(TaskId);
            Task.setCategoryId(cursor.getInt(cursor.getColumnIndex("type_id")));
            Task.setName(cursor.getString(cursor.getColumnIndex("name")));
            list.add(Task);
        }
        cursor.close();
        result.setSuccess(true);
        result.setMessage("Search successfully");
        result.setData(list);
        return result;
    }

    /**
     * 添加菜品
     */
    public static BusinessResult<Task> insert(Task Task) {
        if (TextUtils.isEmpty(Task.getName())) {
            return new BusinessResult<>(false, "The name can't be null", null);
        }

        BusinessResult<Task> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        db.execSQL("insert into _task(_id,type_id,name) values(?,?,?)",
                new Object[]{Task.getId(), Task.getCategoryid(),Task.getName()});
        result.setSuccess(true);
        result.setMessage("Add successfully");
        return result;
    }

    /**
     * 修改菜品
     */
    public static BusinessResult<Void> update(Task Task) {
        if (TextUtils.isEmpty(Task.getName())) {
            return new BusinessResult<>(false, "The name can't be null", null);
        }
        BusinessResult<Void> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        db.execSQL("update _task set type_id=?,name=?where _id=?",
                new Object[]{Task.getCategoryid(),Task.getName(),Task.getId()});
        result.setSuccess(true);
        result.setMessage("Edit Successfully");
        return result;
    }

    /**
     * 删除菜品
     *
     */
    public static BusinessResult<Task> delete1(Task task) {
        BusinessResult<Task> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        db.execSQL("delete from _task ", new Object[]{});
        result.setSuccess(true);
        result.setMessage("Delete Successfully");
        return result;
    }
    public static BusinessResult<Void> delete(Integer id) {
        BusinessResult<Void> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        db.execSQL("delete from _task where _id=?", new Object[]{id});
        result.setSuccess(true);
        result.setMessage("Delete Successfully");
        return result;
    }

    public static BusinessResult<Task> getById(Integer TaskId) {
        BusinessResult<Task> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from _task where _id=?", new String[]{String.valueOf(TaskId)});
        Task Task = null;
        if (cursor.moveToNext()) {
            Task = new Task();
            Task.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            Task.setCategoryId(cursor.getInt(cursor.getColumnIndex("type_id")));
            Task.setName(cursor.getString(cursor.getColumnIndex("name")));
        }
        cursor.close();
        if (Task == null) {
            result.setSuccess(false);
            result.setMessage("The Task not exist");
        } else {
            result.setSuccess(true);
            result.setMessage("查询成功");
            result.setData(Task);
        }
        return result;
    }
}
