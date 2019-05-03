package com.samuelbernard147.moviecatalogueapi;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup rgOptions;
    RadioButton rbIn, rbEn;
    Button btnChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        rgOptions = findViewById(R.id.rg_options);
        rbIn = findViewById(R.id.rb_in);
        rbEn = findViewById(R.id.rb_en);
        btnChoose = findViewById(R.id.btn_choose);
        btnChoose.setOnClickListener(this);

//        Menampilkan backbutton
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    //    Untuk back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //    Fungsi merubah bahasa
    public void setLanguage(String language) {
        Locale mLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.locale = mLocale;
        res.updateConfiguration(config, dm);
        Intent i = new Intent(SettingsActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    //    Fungsi ketika dipilih
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_choose) {
            switch (rgOptions.getCheckedRadioButtonId()) {
                case R.id.rb_en:
                    setLanguage("en");
                    break;
                case R.id.rb_in:
                    setLanguage("in");
                    break;
            }
        }
    }
}
