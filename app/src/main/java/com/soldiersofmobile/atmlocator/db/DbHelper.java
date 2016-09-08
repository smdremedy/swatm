package com.soldiersofmobile.atmlocator.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DbHelper extends OrmLiteSqliteOpenHelper {

    public DbHelper(Context context) {
        super(context, "atm.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Bank.class);
            TableUtils.createTable(connectionSource, Atm.class);

            Dao<Bank, Long> bankDao = getDao(Bank.class);

            bankDao.create(new Bank("ING Bank", "500 600 700"));
            bankDao.create(new Bank("PKO SA", "500 600 701"));
            bankDao.create(new Bank("PKO BP", "500 600 702"));
            bankDao.create(new Bank("Milenium", "500 600 703"));
            bankDao.create(new Bank("Alior", "500 600 704"));
            bankDao.create(new Bank("CitiBank", "500 600 705"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
