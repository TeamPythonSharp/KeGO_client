package com.example.kegowidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;
import io.paperdb.Paper;


import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class KeGOWidget extends AppWidgetProvider {



    Button btnRandom;
    static String CLICK_ACTION = "CLICKED";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, KeGOWidget.class);
        intent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);


        Paper.init(context);

        String content = Paper.book().read("target");
        long milliseconds = Paper.book().read("data_and_time");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ke_g_o_widget);
        views.setTextViewText(R.id.appwidget_content, content);
        new CountDownTimer(milliseconds + 5000, 1000) {

            public void onTick(long millisUntilFinished) {
                long days =  millisUntilFinished / (24 * 60 * 60 * 1000);
                long hours =  millisUntilFinished / (60 * 60 * 1000) % 24;
                long minutes =  millisUntilFinished / (60 * 1000) % 60;
                long seconds =  millisUntilFinished / 1000 % 60;
                String resultString = "";
                if (days > 0)
                    resultString += days + ":";
                if (hours > 0)
                    resultString += hours + ":";
                if (minutes > 0)
                    resultString += minutes + ":";
                resultString += seconds;
                views.setTextViewText(R.id.appwidget_timer,resultString);
                appWidgetManager.updateAppWidget(appWidgetId,views);
            }

            public void onFinish() {
                views.setTextViewText(R.id.appwidget_timer,"00:00:00");
                appWidgetManager.updateAppWidget(appWidgetId,views);
            }
        }.start();
        views.setOnClickPendingIntent(R.id.rndm_btn,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId,views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(CLICK_ACTION)) {
            Random random = new Random();

            Toast.makeText(context,"Дерзай!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}