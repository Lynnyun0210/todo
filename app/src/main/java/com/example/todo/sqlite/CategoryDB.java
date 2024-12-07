package com.example.todo.sqlite;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.todo.entity.Category;
import com.example.todo.utils.SqliteUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Range")
public class CategoryDB {

    /**
     * 查询所有任务类型
     */
    public static BusinessResult<List<Category>> selectAll() {
        BusinessResult<List<Category>> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        List<Category> list = new ArrayList<>();
        //根据id倒序查询
        Cursor cursor = db.rawQuery("select * from _type order by _id desc", null);
        while (cursor.moveToNext()) {
            int CategoryId = cursor.getInt(cursor.getColumnIndex("_id"));
            Category Category = new Category();
            Category.setId(CategoryId);
            Category.setName(cursor.getString(cursor.getColumnIndex("name")));
            list.add(Category);
        }
        cursor.close();
        result.setSuccess(true);
        result.setMessage("Search Successfully");
        result.setData(list);
        return result;
    }

    /**
     * 根据类型名称查询类型
     */
    public static BusinessResult<List<Category>> selectByName(String name) {
        BusinessResult<List<Category>> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        //根据id倒序查询,模糊查询
        Cursor cursor = db.rawQuery("select * from _type where name like ? order by _id desc", new String[]{"%" + name + "%"});
        List<Category> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int CategoryId = cursor.getInt(cursor.getColumnIndex("_id"));
            Category Category = new Category();
            Category.setId(CategoryId);
            Category.setName(cursor.getString(cursor.getColumnIndex("name")));
            Category.setImg(cursor.getString(cursor.getColumnIndex("img")));
            list.add(Category);
        }
        cursor.close();
        result.setSuccess(true);
        result.setMessage("Search Successfully");
        result.setData(list);
        return result;
    }

    /**
     * 添加类型
     */
    public static BusinessResult<Category> insert(Category Category) {
        if (TextUtils.isEmpty(Category.getName())) {
            return new BusinessResult<>(false, "The name can't be null", null);
        }

        BusinessResult<Category> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        db.execSQL("insert into _type(name) values(?)", new Object[]{Category.getName()});
        result.setSuccess(true);
        result.setMessage("Add Successfully");
        //回填_id
        Cursor cursor = db.rawQuery("select last_insert_rowid() from _type", null);
        if (cursor.moveToFirst()) {
            Category.setId(cursor.getInt(0));
        }
        cursor.close();
        result.setData(Category);
        return result;
    }

    /**
     * 修改类型
     */
    public static BusinessResult<Category> update(Category Category) {
        if (TextUtils.isEmpty(Category.getName())) {
            return new BusinessResult<>(false, "The name can't be null", null);
        }

        BusinessResult<Category> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        db.execSQL("update _type set name=?where _id=?", new Object[]{Category.getName(), Category.getId()});
        result.setSuccess(true);
        result.setMessage("Edit Successfully");
        result.setData(Category);
        return result;
    }
    public static BusinessResult<Void> add(String name) {
        BusinessResult<Void> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        db.execSQL("insert into _type values(?)", new Object[]{name});
        result.setSuccess(true);
        result.setSuccess(true);
        result.setMessage("Add Successfully");
        return result;
    }


    /**
     * 删除类型
     */
    public static BusinessResult<Void> delete(String name,Integer id) {
        BusinessResult<Void> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        db.execSQL("delete from _type where _name=? and _id=?", new Object[]{id});
        result.setSuccess(true);
        result.setMessage("Delete Successfully");
        return result;
    }


}
