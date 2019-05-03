package com.samuelbernard147.smarthomev2.alirkan;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.samuelbernard147.smarthomev2.R;
import com.samuelbernard147.smarthomev2.alirkan.Job.GetHumidityJobService;

public class SplashActivity extends AppCompatActivity {
    ProgressBar pbWelcome;
    private int jobId = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 4000);
        startJob();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }

    private void startJob() {
        Log.e("StartJob", "Berjalan");
        ComponentName mServiceComponent = new ComponentName(this, GetHumidityJobService.class);

        JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setRequiresDeviceIdle(false);
        builder.setRequiresCharging(false);
        builder.setPeriodic(1000);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }

    private void cancelJob() {
        Log.e("StartJob", "Berhenti");
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancel(jobId);
        finish();
    }
}