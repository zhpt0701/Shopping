package com.example.cloudAndPurchasing.sqllite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/5/5 0005.
 */
public class MySqLite extends SQLiteOpenHelper{
    private static final String SQL = "mysql.db";
    private static final Integer VERION = 1;
    public MySqLite(Context context) {
        super(context,SQL,null,VERION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Personal.CREATE_TABLES);
        db.execSQL(EachTable.CREATE_TABLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
