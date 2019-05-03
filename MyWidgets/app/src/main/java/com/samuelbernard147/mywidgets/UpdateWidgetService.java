package com.samuelbernard147.mywidgets;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.widget.RemoteViews;

public class UpdateWidgetService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.random_number_widget);
        ComponentName theWidget = new ComponentName(this, RandomNumbersWidget.class);
        String lastUpdate = "Random: " + NumberGenerator.Generate(100);
        view.setTextViewText(R.id.appwidget_text, lastUpdate);
        manager.updateAppWidget(theWidget, view);
        jobFinished(jobParameters, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
