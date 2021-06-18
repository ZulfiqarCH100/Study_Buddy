package com.example.studybuddy;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.security.identity.CipherSuiteNotSupportedException;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * When using collections (eg. ListView, StackView etc.) in widgets,
 * it is very costly to set PendingIntents on the individual items, and is hence not recommended.
 * Instead this method should be used to set a single PendingIntentemplate on the collection,
 * and individual items can differentiate their on-click behavior using RemoteViews#setOnClickFillInIntent(int, Intent).
 */
public class TodayWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_REFRESH = "action refresh";
    public static final String EXTRA_ITEM_POSITION = "extra Item Position";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //Clicking the widget opens the application.
        Intent openApp = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, openApp, 0);

        // Construct the RemoteViews object
        Intent serviceIntent = new Intent(context, TodayWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //The below line is useful if we have multiple instances of our widgets in the homescreen so that system can sperate them.
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        Intent clickIntent = new Intent(context, TodayWidgetProvider.class);
        clickIntent.setAction(ACTION_REFRESH);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,
                0, clickIntent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.today_widget); //Displays our layout in another process.
        views.setOnClickPendingIntent(R.id.todayWidgetTitle, pIntent); //Onclick listener to open app when empty space is clicked on the widget.
        views.setRemoteAdapter(R.id.widgetTodayList, serviceIntent); //connecting the adapter to the listView.
        //Setting intent to open app for listview items.
        views.setPendingIntentTemplate(R.id.widgetTodayList, clickPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        //This updates the widget and is the place to do the inital setup for the widget.

        for (int appWidgetId : appWidgetIds) {
            //Calling update for every instance of the widget on the screen.
            updateAppWidget(context, appWidgetManager, appWidgetId);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetTodayList); //On every update update the listView too.
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

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_REFRESH.equals(intent.getAction())) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID); //Retrieving id of the widget to refresh
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetTodayList);
        }
        super.onReceive(context, intent);
    }

}