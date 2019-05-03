package com.samuelbernard147.moviecataloguelocalstorage.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.samuelbernard147.moviecataloguelocalstorage.Model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.samuelbernard147.moviecataloguelocalstorage.DB.DatabaseContract.FavColumns.FAVID;
import static com.samuelbernard147.moviecataloguelocalstorage.DB.DatabaseContract.FavColumns.OVERVIEW;
import static com.samuelbernard147.moviecataloguelocalstorage.DB.DatabaseContract.FavColumns.POSTER;
import static com.samuelbernard147.moviecataloguelocalstorage.DB.DatabaseContract.FavColumns.TABLE_NAME;
import static com.samuelbernard147.moviecataloguelocalstorage.DB.DatabaseContract.FavColumns.TITLE;
import static com.samuelbernard147.moviecataloguelocalstorage.DB.DatabaseContract.FavColumns.TYPE;

public class FavHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static FavHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    /*
     * Method untuk cek data Id favourite
     * Return berupa boolean
     */
    public boolean checkFav(String id, String type) {
        boolean dataFound = false;
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                FAVID + " = ? AND " + TYPE + " LIKE ?",
                new String[]{id, type},
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            dataFound = true;
        }
        cursor.close();
        return dataFound;
    }

    /**
     * Method untuk mengambil semua data favourite movie yang ada
     * data akan di parsing ke dalam model Movie
     * kemudian hasilnya berbentuk array model Movie
     */
    public ArrayList<Movie> getAllFav(String type) {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                TYPE + " LIKE ?",
                new String[]{type},
                null,
                null,
                _ID + " ASC",
                null);

        cursor.moveToFirst();
        Movie fav;
        if (cursor.getCount() > 0) {
            do {
                fav = new Movie();
                fav.setId(cursor.getInt(cursor.getColumnIndexOrThrow(FAVID)));
                fav.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                fav.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                fav.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                arrayList.add(fav);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    /**
     * Method untuk menambah data favourite movie
     * param model movie yang akan dimasukkan
     * return id dari data yang baru saja dimasukkan
     */
    public long insertFav(Movie movie, String type) {
        ContentValues args = new ContentValues();
        args.put(FAVID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(POSTER, movie.getPoster());
        args.put(OVERVIEW, movie.getOverview());
        args.put(TYPE, type);
        return database.insert(DATABASE_TABLE, null, args);
    }

    /**
     * Method untuk menghapus data favourite movie
     * param id yang akan di delete
     * return int jumlah row yang di delete
     */
    public int deleteFav(int id) {
        return database.delete(TABLE_NAME, FAVID + " = '" + id + "'", null);
    }
}