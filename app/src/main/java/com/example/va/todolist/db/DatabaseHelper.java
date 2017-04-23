package com.example.va.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.example.va.todolist.db.model.Item;
import com.example.va.todolist.db.model.ListI;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;



public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME    = "priprema.db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<Item, Integer> mItemDao = null;
    private Dao<ListI, Integer> mListDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Item.class);
            TableUtils.createTable(connectionSource, ListI.class);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Item.class, true);
            TableUtils.dropTable(connectionSource, ListI.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Item, Integer> getItemDao() throws SQLException {
        if (mItemDao == null) {
            mItemDao = getDao(Item.class);
        }

        return mItemDao;
    }

    public Dao<ListI, Integer> getListIDao() throws SQLException {
        if (mListDao == null) {
            mListDao = getDao(ListI.class);
        }

        return mListDao;
    }

    @Override
    public void close() {
        mItemDao = null;
        mListDao = null;

        super.close();
    }
}
