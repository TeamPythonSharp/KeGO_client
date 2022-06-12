package com.example.kegowidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.RemoteViews;
import android.widget.Toast;
import io.paperdb.Paper;


/**
 * Implementation of App Widget functionality.
 */
public class KeGOWidget extends AppWidgetProvider {

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
                String resultString = (days > 0) ? String.format("%01d:%02d:%02d:%02d", days, hours, minutes, seconds) :
                        String.format("%02d:%02d:%02d", hours, minutes, seconds);
                if (milliseconds / 20 > millisUntilFinished) {          //Когда меньше 20% от исходного - таймер красный
                    views.setTextColor(R.id.appwidget_timer, Color.parseColor("#FFD10E0E"));
                    appWidgetManager.updateAppWidget(appWidgetId,views);
                }
                views.setTextViewText(R.id.appwidget_timer,resultString);
                appWidgetManager.updateAppWidget(appWidgetId,views);
            }

            public void onFinish() {
                views.setTextViewText(R.id.appwidget_timer,"00:00:00");
            }
        }.start();

        views.setOnClickPendingIntent(R.id.change_btn,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId,views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(CLICK_ACTION)) {


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