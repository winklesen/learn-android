package com.samuelbernard147.smarthomev2.soimah;

import android.content.AsyncTaskLoader;
import android.content.Context;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LampuLoader extends AsyncTaskLoader<ArrayList<LampuItems>> {
    private ArrayList<LampuItems> mData;
    private boolean mHasResult = false;

    public LampuLoader(final Context context) {
        super(context);
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
    public void deliverResult(final ArrayList<LampuItems> data) {
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
    public ArrayList<LampuItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<LampuItems> lampuItems = new ArrayList<>();
        String url = "http://pameran.hopecoding.com/public/api/get";
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
                        LampuItems LampuItems = new LampuItems(responseObject, i);
                        lampuItems.add(LampuItems);
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
        return lampuItems;
    }
}