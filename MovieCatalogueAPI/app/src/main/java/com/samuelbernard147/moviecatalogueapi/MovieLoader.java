package com.samuelbernard147.moviecatalogueapi;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private ArrayList<Movie> mData;
    private boolean mHasResult = false;

    private int id;
    private String url;

    //    API KEY
    public final static String API_KEY = "be4808a7b6d2d8684012bc817583647d";

    public MovieLoader(final Context context, int id, String lang) {
        super(context);
        this.id = id;
        this.url = getUrl(id, lang);
        onContentChanged();
    }

    //Ketika data loading,
    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<Movie> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            mData = null;
            mHasResult = false;
        }
    }

    //    Fungsi untuk menentukan url berdasarkan parameter language
    private String getUrl(int id, String language) {
        String url = null;
        if (id == 100 && language.equals("en")) {
            url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
        } else if (id == 100 && language.equals("in")) {
            url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=id-ID";
        } else if (id == 101 && language.equals("en")) {
            url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        } else if (id == 101 && language.equals("in")) {
            url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=id-ID";
        }
        return url;
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> movieItemes = new ArrayList<>();
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
                        Movie MovieItems = new Movie(responseArray, i, id);
                        movieItemes.add(MovieItems);
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
        return movieItemes;
    }
}