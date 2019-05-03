package com.samuelbernard147.smartagriculture;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<RiwayatItems>> {
    private ArrayList<RiwayatItems> mData;
    private boolean mHasResult = false;

    //    final Calendar calendar = Calendar.getInstance();
    private int month;
    private int day;
    private String author;

    MyAsyncTaskLoader(final Context context, String author, int day, int month) {
        super(context);
        onContentChanged();
        this.author = author;
        this.day = day;
        this.month = month;
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
    public void deliverResult(final ArrayList<RiwayatItems> data) {
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

    @Override
    public ArrayList<RiwayatItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<RiwayatItems> riwayatItemes = new ArrayList<>();
        String url = "http://agriculture.hopecoding.com/api/search/riwayat?" +
                "day=" + day +
                "&month=" + month +
                "&author=" + author;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                //Menggunakan synchronous karena pada dasarnya thread yang digunakan sudah asynchronous dan method
                //loadInBackground mengembalikan nilai balikan
                setUseSynchronousMode(true);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONArray responseObject = new JSONArray(result);
                    for (int i = 0; i < responseObject.length(); i++) {
                        RiwayatItems riwayatItems = new RiwayatItems(responseObject, i);
                        riwayatItemes.add(riwayatItems);
                    }
                } catch (Exception e) {
                    //Jika terjadi error pada saat parsing maka akan masuk ke catch()
                    Log.e("Exception", "Data gagal diparsing");
                    e.printStackTrace();

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Jika response gagal maka , do nothing
                Log.e("OnFailure", "Data gagal diload");
            }
        });
        return riwayatItemes;
    }
}