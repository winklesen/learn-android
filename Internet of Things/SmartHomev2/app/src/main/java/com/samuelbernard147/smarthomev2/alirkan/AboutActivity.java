package com.samuelbernard147.smarthomev2.alirkan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.samuelbernard147.smarthomev2.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

//        Rubah title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tentang Aplikasi");
        }

//        Buat back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
