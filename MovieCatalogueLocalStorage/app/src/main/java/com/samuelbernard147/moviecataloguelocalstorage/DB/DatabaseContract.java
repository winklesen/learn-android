package com.samuelbernard147.moviecataloguelocalstorage.DB;

import android.provider.BaseColumns;

class DatabaseContract {

    static final class FavColumns implements BaseColumns {
        static final String TABLE_NAME = "favourite";

        //Favourite Id
        static final String FAVID = "favid";
        //Favourite title
        static final String TITLE = "title";
        //Favourite poster url
        static final String POSTER = "poster";
        //Favourite overview
        static final String OVERVIEW = "overview";
        //Favourite type
        static final String TYPE = "type";
    }
}