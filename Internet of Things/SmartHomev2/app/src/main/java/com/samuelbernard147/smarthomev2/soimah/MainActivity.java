package com.samuelbernard147.smarthomev2.soimah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.samuelbernard147.smarthomev2.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView card1, card2, card3, card4;
    ImageButton ibtnLamp, ibtnDoor, ibtnInfo, ibtnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soimah_main);
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fadein);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);

        card1.startAnimation(animFadeIn);
        card2.startAnimation(animFadeIn);
        card3.startAnimation(animFadeIn);
        card4.startAnimation(animFadeIn);


        ibtnLamp = findViewById(R.id.ibtn_lampu);
        ibtnDoor = findViewById(R.id.ibtn_doorlock);
        ibtnInfo = findViewById(R.id.ibtn_about);
        ibtnHelp = findViewById(R.id.ibtn_help);

        ibtnLamp.setOnClickListener(this);
        ibtnDoor.setOnClickListener(this);
        ibtnInfo.setOnClickListener(this);
        ibtnHelp.setOnClickListener(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Soimah");
        }

    }

    //    Untuk back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_lampu:
                LampActivity lampActivity = new LampActivity();
                pindahActivity(lampActivity);
                break;
            case R.id.ibtn_doorlock:
                DoorLockActivity doorLockActivity = new DoorLockActivity();
                pindahActivity(doorLockActivity);
                break;
            case R.id.ibtn_about:
                AboutActivity aboutActivity = new AboutActivity();
                pindahActivity(aboutActivity);
                break;
            case R.id.ibtn_help:
                HelpActivity helpActivity = new HelpActivity();
                pindahActivity(helpActivity);
                break;
        }
    }

    void pindahActivity(Context context) {
        Intent i = new Intent(MainActivity.this, context.getClass());
        startActivity(i);
    }
}
