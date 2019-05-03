package com.samuelbernard147.moviecataloguelocalstorage;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    String language;

    private SettingsPreference mSettingPreference;

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
            getSupportActionBar().setTitle("Movie Catalogue");
        }

        mSettingPreference = new SettingsPreference(this);

//        Cek data preference
        if (mSettingPreference.getLang().isEmpty()) {
            language = Locale.getDefault().getLanguage();
        } else {
            language = mSettingPreference.getLang();
        }

        setupRadio(language);
    }

    //    Set radio button yang aktif
    void setupRadio(String lang) {
        if (lang.equals("en")) {
            rbEn.setChecked(true);
            rbIn.setChecked(false);
        } else {
            rbEn.setChecked(false);
            rbIn.setChecked(true);
        }
    }

    //    Untuk back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    /*
     * Merubah bahasa dengan parameter bahasa yang dipilih (String)
     */
    public void setLanguage(String language) {
        Locale mLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.locale = mLocale;
        res.updateConfiguration(config, dm);
        Intent i = new Intent(SettingsActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //    Set bahasa di preference
        mSettingPreference.setLang(language);
        startActivity(i);
    }

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