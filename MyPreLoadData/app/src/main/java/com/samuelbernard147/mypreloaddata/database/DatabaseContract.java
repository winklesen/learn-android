package com.samuelbernard147.mypreloaddata.database;

import android.provider.BaseColumns;


/**
 * Created by Samuel Bernard Jeffersen
 */

class DatabaseContract {

    static String TABLE_NAME = "table_mahasiswa";

    static final class MahasiswaColumns implements BaseColumns {

        // Mahasiswa nama
        static String NAMA = "nama";
        // Mahasiswa nim
        static String NIM = "nim";

    }
}
