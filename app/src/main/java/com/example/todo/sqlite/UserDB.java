package com.example.todo.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.todo.entity.User;
import com.example.todo.utils.MD5Utils;
import com.example.todo.utils.SqliteUtils;

public class UserDB {

    /**
     * 注册用户
     */
    public static BusinessResult<User> register(User user) {
        BusinessResult<User> result = new BusinessResult<>();
        if (user == null) {
            result.setSuccess(false);
            result.setMessage("The information can't be null");
            return result;
        }
        if (isExistByUsername(user.getUsername()).getData()) {
            result.setSuccess(false);
            result.setMessage("The user is existing");
            return result;
        }
        if (TextUtils.isEmpty(user.getUsername()) || TextUtils.isEmpty(user.getPassword())) {
            result.setSuccess(false);
            result.setMessage("The username or password can't be null");
            return result;
        }
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", MD5Utils.md5(user.getPassword()));
        long i = db.insert("_user", null, values);
        if (i > 0) {
            result.setSuccess(true);
            result.setMessage("Register Successfully");
            user.setId((int) i);
            result.setData(user);
        } else {
            result.setSuccess(false);
            result.setMessage("Register Unsuccessfully");
        }
        return result;
    }

    /**
     * 登录用户
     */
    @SuppressLint("Range")
    public static BusinessResult<User> login(User user) {
        BusinessResult<User> result = new BusinessResult<>();
        if (user == null) {
            result.setSuccess(false);
            result.setMessage("The information can't be null");
            return result;
        }
        if (TextUtils.isEmpty(user.getUsername()) || TextUtils.isEmpty(user.getPassword())) {
            result.setSuccess(false);
            result.setMessage("The username or password can't be null");
            return result;
        }

        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        Cursor cursor = db.query("_user", null, "username=? and password=?", new String[]{user.getUsername(), MD5Utils.md5(user.getPassword())}, null, null, null);
        if (cursor.moveToNext()) {
            result.setSuccess(true);
            result.setMessage("Login successfully");
            user.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            result.setData(user);
        } else {
            result.setSuccess(false);
            result.setMessage("The username or password might be wrong");
        }
        cursor.close();
        return result;
    }

    /**
     * 根据用户名查询是否存在该用户
     */
    public static BusinessResult<Boolean> isExistByUsername(String username) {
        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        Cursor cursor = db.query("_user", null, "username=?", new String[]{username}, null, null, null);
        BusinessResult<Boolean> result = new BusinessResult<>();
        result.setSuccess(true);
        if (cursor.getCount() > 0) {
            result.setData(true);
            result.setMessage("User is existing");
        } else {
            result.setData(false);
            result.setMessage("User is not existing");
        }
        cursor.close();
        return result;
    }

}
