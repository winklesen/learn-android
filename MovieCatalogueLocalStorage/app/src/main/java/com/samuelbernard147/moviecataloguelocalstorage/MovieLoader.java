package com.samuelbernard147.moviecataloguelocalstorage;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.samuelbernard147.moviecataloguelocalstorage.DB.FavHelper;
import com.samuelbernard147.moviecataloguelocalstorage.Model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private ArrayList<Movie> mListMovie;
    private boolean mHasResult = false;

    private Context context;
    private int id;
    private String url;
    private String type;

    public final static String TYPE_API = "API";
    public final static String TYPE_FAVORITE = "FAVORITE";

    public final static int ID_MOVIE = 100;
    public final static int ID_TV = 101;

    public static String BHS_INDO = "in";
    public static String BHS_INGGRIS = "en";

    //    URL
    private final static String URL_MOVIE = "https://api.themoviedb.org/3/discover/movie?api_key=";
    private final static String URL_TV = "https://api.themoviedb.org/3/discover/tv?api_key=";

    //    API KEY
    final static String API_KEY = "be4808a7b6d2d8684012bc817583647d";

    public MovieLoader(final Context context, int id, String lang, String type) {
        super(context);
        this.context = context;
        this.id = id;
        this.url = getUrl(id, lang);
        this.type = type;
        onContentChanged();
    }

    //Ketika data loading,
    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mListMovie);
    }

    @Override
    public void deliverResult(final ArrayList<Movie> data) {
        mListMovie = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            mListMovie = null;
            mHasResult = false;
        }
    }

    /*
     * Fungsi untuk menentukan url
     * dengan parameter id (Movie/Tv), dan bahasa
     */
    private String getUrl(int id, String language) {
        String url = null;
        if (id == ID_MOVIE && language.equals(BHS_INGGRIS)) {
            url = URL_MOVIE + API_KEY + "&language=en-US";
        } else if (id == ID_MOVIE && language.equals(BHS_INDO)) {
            url = URL_MOVIE + API_KEY + "&language=id-ID";
        } else if (id == ID_TV && language.equals(BHS_INGGRIS)) {
            url = URL_TV + API_KEY + "&language=en-US";
        } else if (id == ID_TV && language.equals(BHS_INDO)) {
            url = URL_TV + API_KEY + "&language=id-ID";
        }
        return url;
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        ArrayList<Movie> listMovie = null;
        if (type.equals(TYPE_API)) {
            listMovie = loadFromAPI();
        } else if (type.equals(TYPE_FAVORITE)) {
            listMovie = loadFromLocal();
        }
        return listMovie;
    }

    /*
     * Load data favorite(local)
     */
    private ArrayList<Movie> loadFromLocal() {
        ArrayList<Movie> listFavorite = null;
        if (id == ID_MOVIE) {
            FavHelper favHelper = FavHelper.getInstance(context);
            listFavorite = favHelper.getAllFav("movie");
        } else if (id == ID_TV) {
            FavHelper favHelper = FavHelper.getInstance(context);
            listFavorite = favHelper.getAllFav("tv");
        }
        return listFavorite;
    }

    /*
     * Load data movie dari API
     */
    private ArrayList<Movie> loadFromAPI() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> listMovie = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray responseArray = responseObject.getJSONArray("results");

                    for (int i = 0; i < responseArray.length(); i++) {
                        Movie movieItems = new Movie();
                        movieItems.setMovie(responseArray, i, id);
                        listMovie.add(movieItems);
                    }
                } catch (Exception e) {
                    //Respon bila eror
                    Log.e("Exception", "Data gagal diparsing");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Respon bila eror
                Log.e("OnFailure", "Data gagal diload");
            }
        });
        return listMovie;
    }
}