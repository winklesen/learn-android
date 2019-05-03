package com.samuelbernard147.moviecataloguelocalstorage.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.samuelbernard147.moviecataloguelocalstorage.DB.DatabaseContract.FavColumns.TABLE_NAME;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbfavourite";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAV = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s INTEGER NOT NULL,"+
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_NAME,
            DatabaseContract.FavColumns._ID,
            DatabaseContract.FavColumns.FAVID,
            DatabaseContract.FavColumns.TITLE,
            DatabaseContract.FavColumns.POSTER,
            DatabaseContract.FavColumns.OVERVIEW,
            DatabaseContract.FavColumns.TYPE
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
        */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

