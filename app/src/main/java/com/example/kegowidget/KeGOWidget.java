package com.example.kegowidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;
import io.paperdb.Paper;

/**
 * Implementation of App Widget functionality.
 */
public class KeGOWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Paper.init(context);

        String title = Paper.book().read("title");
        String content = Paper.book().read("content");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ke_g_o_widget);
        views.setTextViewText(R.id.appwidget_title, title);
        views.setTextViewText(R.id.appwidget_content, content);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
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