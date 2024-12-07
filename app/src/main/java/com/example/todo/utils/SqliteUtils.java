package com.example.todo.utils;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteUtils extends SQLiteOpenHelper {

    public SqliteUtils() {

        super(AppUtils.getApplication(), "todo.db", null, 3);
    }

    /**
     * 创建并获取单例
     */
    public static SqliteUtils getInstance() {

        return InstanceHolder.instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*
        用户表(_user)：
        _id       integer  用户id
        username  varchar  用户名
        password  varchar  密码
         */
        //sqLiteDatabase.execSQL("CREATE TABLE _user(_id INTEGER PRIMARY AUTOINCREMENT,username varchar(20),password varchar(20) )");
        sqLiteDatabase.execSQL("CREATE TABLE _user(_id INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR(20) ,password VARCHAR(20))");
        /*
        类型表(_type)：
        _id          integer  typeid
        name         varchar  type名称
        img          varchar  type图片url
         */
        sqLiteDatabase.execSQL("CREATE TABLE _type(_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),img VARCHAR(200))");
        /*
        子任务表(_task)：
        _id          integer  taskid
        type_id      integer  typeid
        name  varchar  taskname
         */
        sqLiteDatabase.execSQL("CREATE TABLE _task(_id INTEGER PRIMARY KEY AUTOINCREMENT,type_id INTEGER,name VARCHAR(200))");
        /*
        购物车表(_cart)：
        _id          integer  购物车id
        user_id      integer  用户id
        food_id      integer  taskid
        count        integer  task数量
         */
        sqLiteDatabase.execSQL("CREATE TABLE _cart(_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,task_id INTEGER,count INTEGER)");
        /*
        Finished_task表(_finished_task)：
        name         varchar  taskname
        _id          integer  taskid
        user_id      integer  用户id
        finished_time     text     finished时间
        total_task_number  integer     完成任务的数量
         */
        sqLiteDatabase.execSQL("CREATE TABLE _finishedtask(_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,task_name varchar(20),finished_time Text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    private static final class InstanceHolder {
        /**
         * 单例
         */
        static final SqliteUtils instance = new SqliteUtils();
    }
}
