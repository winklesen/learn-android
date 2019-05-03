package com.samuelbernard147.soimah;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class LightActivity extends AppCompatActivity implements View.OnClickListener {
    SwipeRefreshLayout swipeRefreshLight;
    CardView linUtama, linTeras, linTamu, linKeluarga, linDapur, linMandi, linAnak;
    ImageButton ibtnUtama, ibtnTeras, ibtnTamu, ibtnKeluarga, ibtnDapur, ibtnMandi, ibtnAnak;

    int colorOFF = Color.parseColor("#ffffff");
    int colorPrimary = Color.parseColor("#2196F3");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        linUtama = findViewById(R.id.ll_utama);
        linTeras= findViewById(R.id.ll_teras);
        linTamu= findViewById(R.id.ll_tamu);
        linKeluarga= findViewById(R.id.ll_keluarga);
        linDapur= findViewById(R.id.ll_dapur);
        linMandi= findViewById(R.id.ll_kamar_mandi);
        linAnak= findViewById(R.id.ll_anak);

        ibtnUtama = findViewById(R.id.img_btn_utama);
        ibtnTeras= findViewById(R.id.img_btn_teras);
        ibtnTamu= findViewById(R.id.img_btn_tamu);
        ibtnKeluarga= findViewById(R.id.img_btn_keluarga);
        ibtnDapur= findViewById(R.id.img_btn_dapur);
        ibtnMandi= findViewById(R.id.img_btn_kamar_mandi);
        ibtnAnak= findViewById(R.id.img_btn_anak);

        ibtnUtama.setOnClickListener(this);
        ibtnTeras.setOnClickListener(this);
        ibtnTamu.setOnClickListener(this);
        ibtnKeluarga.setOnClickListener(this);
        ibtnDapur.setOnClickListener(this);
        ibtnMandi.setOnClickListener(this);
        ibtnAnak.setOnClickListener(this);

        getLampStatus();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_utama:
                postStatus(linUtama,2);
                linUtama.setBackgroundColor(inverseColor(linUtama));
                break;
            case R.id.img_btn_teras:
                postStatus(linTeras,1);
                linTeras.setBackgroundColor(inverseColor(linTeras));
                break;
            case R.id.img_btn_tamu:
                postStatus(linTamu,3);
                linTamu.setBackgroundColor(inverseColor(linTamu));
                break;
            case R.id.img_btn_keluarga:
                postStatus(linKeluarga,4);
                linKeluarga.setBackgroundColor(inverseColor(linKeluarga));
                break;
            case R.id.img_btn_dapur:
                postStatus(linDapur,5);
                linDapur.setBackgroundColor(inverseColor(linDapur));
                break;
            case R.id.switch_lamp_mandi:
                postStatus(linMandi,6);
                linMandi.setBackgroundColor(inverseColor(linMandi));
                break;
            case R.id.img_btn_anak:
                postStatus(linAnak,7);
                linAnak.setBackgroundColor(inverseColor(linAnak));
                break;
        }
    }

    private void getLampStatus() {
        Log.d("GetLampStatus", "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://pameran.hopecoding.com/public/api/get";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d("GetLampStatus", result);
                try {
                    JSONArray listObject = new JSONArray(result);
                    int statusUtama = listObject.getJSONObject(0).getInt("status");
                    int statusAnak= listObject.getJSONObject(1).getInt("status");
                    int statusDapur = listObject.getJSONObject(2).getInt("status");
                    int statusKeluarga = listObject.getJSONObject(3).getInt("status");
                    int statusMandi = listObject.getJSONObject(4).getInt("status");
                    int statusTamu = listObject.getJSONObject(5).getInt("status");
                    int statusTeras = listObject.getJSONObject(6).getInt("status");

                    linUtama.setBackgroundColor(cekStatus(statusUtama));
                    linAnak.setBackgroundColor(cekStatus(statusAnak));
                    linDapur.setBackgroundColor(cekStatus(statusDapur));
                    linKeluarga.setBackgroundColor(cekStatus(statusKeluarga));
                    linMandi.setBackgroundColor(cekStatus(statusMandi));
                    linTamu.setBackgroundColor(cekStatus(statusTamu));
                    linTeras.setBackgroundColor(cekStatus(statusTeras));

                } catch (Exception e) {
                    Log.d("GetLampStatus","EROR");
                    // ketika terjadi error, maka jobFinished diset dengan parameter true. Yang artinya job perlu di reschedule
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // ketika proses gagal, maka jobFinished diset dengan parameter true. Yang artinya job perlu di reschedule
            }
        });
    }

    private void postLampStatus(final int id, final int status) {
        Log.d("postLampStatus", "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://pameran.hopecoding.com/public/ubahlampu";

        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("status", status);

        client.get(this, url, param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("POST","BERHASIL");
                Log.e("POST","Id = "+id + "Status = "+status);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("POST","GAGAL");
            }
        });
    }

    private int getCurrentColor(View view){
        int currentColor = ((ColorDrawable)view.getBackground()).getColor();
        return currentColor;
    }

    private int inverseColor(View view){
        if (getCurrentColor(view) == colorPrimary){
            return colorOFF;
        }else {
            return colorPrimary;
        }
    }

    void postStatus(View view, int id){
        if (getCurrentColor(view) == colorPrimary) {
            postLampStatus(id, 0);
        } else {
            postLampStatus(id, 1);
        }
    }

    private int cekStatus(int i){
        int startColor = colorOFF;
        if (i == 1){
            startColor = colorPrimary;
        }
        return startColor;
    }
}