package com.example.studybuddy;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.zip.Inflater;

import static com.example.studybuddy.TodayWidgetProvider.EXTRA_ITEM_POSITION;

//The whole purpose of this service is to return a Factory object or basically the adapter to the widget listView.
//The responsibiliy of this class is to put the data from database into the listview items.
public class TodayWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TodayWidgetItemFactory(getApplicationContext(), intent);
    }

    //Implementing the adapter (Factory Object) as an inner class for convienience.
    class TodayWidgetItemFactory implements RemoteViewsFactory{
        private Context context;
        private int appWidgetId;
        private Database db;
        private String[] data = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        TodayWidgetItemFactory(Context context, Intent intent){
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            //Make the db connection here, launched when the widget is created.
            //Dont do heavy operations here.
            db = new SQLite(context);
        }

        @Override
        public void onDataSetChanged() {
            //Called when we trigger an update of our collection (list). To show us the updated stuff.

            //Fetch data here from db.
            //We can run long running tasks here. Runs in seperate thread.
            Date date = new Date();
            String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
            data[1] = time;
        }

        @Override
        public void onDestroy() {
            //Close DB connection here.
            db = null;
        }

        @Override
        public int getCount() {
            //total number of items we want to display.
            return data.length; //.size() for ArrayList
        }

        @Override
        public RemoteViews getViewAt(int position) {
            //This is similar to the getView method in the recyclerView adapter.
            //We load each index of data into the listView here.
            //Each entry in a listView is a RemoteView here.
            //We can do heavy operations here.
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.today_widget_item);
            views.setTextViewText(R.id.todayWidgetCourse, data[position]); //Setting text of my listView item at the position.

            //Setting on click for each list item to open App. This is basically a continuation of what we did in the Provider.
            Intent fillIntent = new Intent();
            fillIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId); //Refresh the widget with this id.
            views.setOnClickFillInIntent(R.id.todayWidgetItem, fillIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            //What to show when data is loading.
            return null;
        }

        @Override
        public int getViewTypeCount() {
            //How many different sorts of item does the listView has. All rows are same so 1.
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
