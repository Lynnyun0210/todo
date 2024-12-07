package com.example.todo.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.todo.entity.Finished_task;
import com.example.todo.entity.Finished_task;
import com.example.todo.utils.CurrentUserUtils;
import com.example.todo.utils.SqliteUtils;
import com.example.todo.entity.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FinishedTaskDB {

    public static BusinessResult<Finished_task> add(Integer userId,Finished_task Finished_task) {
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        if(userId==null){
            Log.e("FinishedTaskDB","no input of userid");
        }
        else{
            Log.e("FinishedTaskDB",userId.toString());
        }
        if(Finished_task.getTask_name()==null){
            Log.e("FinishedTaskDB","no input of taskname");
        }
        else{
            Log.e("FinishedTaskDB",Finished_task.getTask_name());
        }
        if(Finished_task.getFinished_time()==null){
            Log.e("FinishedTaskDB","no input of time");
        }
        else{
            Log.e("FinishedTaskDB",Finished_task.getFinished_time());
        }
        values.put("task_name", Finished_task.getTask_name());
        values.put("finished_time", Finished_task.getFinished_time());

        long insert = db.insert("_finishedtask", null, values);
        if (insert == -1) {
            Log.e("FinishedTaskDB","no input");
            return new BusinessResult<>(false, "Add unsuccessfully", null);
        } else {
            return new BusinessResult<>(true, "Add successfully", Finished_task);
        }
    }
    public static BusinessResult<Finished_task> add1(Integer userId,Task task,Finished_task Finished_task) {
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("Task_name", task.getName());
        values.put("Finished_time", Finished_task.getFinished_time());
        long insert = db.insert("_finishedtask", null, values);
        if (insert == -1) {

            return new BusinessResult<>(false, "Add unsuccessfully", null);
        } else {
            return new BusinessResult<>(true, "Add successfully", Finished_task);
        }
    }


    public static BusinessResult<List<Finished_task>> queryByUserId(Integer userId) {
        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        Cursor cursor = db.query("_finishedtask", null, "user_id=?", new String[]{String.valueOf(userId)}, null, null, null);
        List<Finished_task> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Finished_task Finished_task = new Finished_task();
            Finished_task.setId(cursor.getInt(1));
            Finished_task.setFinished_time(cursor.getString(2));
           // Finished_task.setTotal_task_number(cursor.getInt(3));
            list.add(Finished_task);
        }
        cursor.close();
        return new BusinessResult<>(true, "Search Successfully", list);
    }
    /**
     * 根据购物车id删除
     */
    private static BusinessResult<Finished_task> delete(Task task) {
        if (task.getName() == null) {
            return new BusinessResult<>(false, "The name can't be null", null);
        }
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        String sql = "DELETE FROM _finishedtask WHERE taskname=?";
        String[] args = new String[]{String.valueOf(task.getName())};
        db.execSQL(sql, args);
        return new BusinessResult<>(true, "Delete successfully", null);
    }
}
