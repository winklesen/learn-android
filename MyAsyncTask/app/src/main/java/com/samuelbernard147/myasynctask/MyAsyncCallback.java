package com.samuelbernard147.myasynctask;

public interface MyAsyncCallback {
    void onPreExecute();

    void onPostExecute(String text);

}
