package com.github.booknara.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.github.booknara.notification.util.PrefsUtil;

public class NotificationService extends NotificationListenerService {
    private final static String TAG = NotificationService.class.getClass().getSimpleName();
    private final static String CUSTOM_RECEIVER_FILTER = "custom_receiver_filter";

    private NotificationServiceReceiver mNotificationServiceReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationServiceReceiver = new NotificationServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(CUSTOM_RECEIVER_FILTER);
        registerReceiver(mNotificationServiceReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNotificationServiceReceiver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (!PrefsUtil.getBoolean(this, NotificationApplication.ENABLE_NOTIFICATION_ALLOW))
            return;

        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Intent i = new Intent(CUSTOM_RECEIVER_FILTER);
        String pack = sbn.getPackageName();
        String ticker = sbn.getNotification().tickerText != null ? sbn.getNotification().tickerText.toString() : "";
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text") != null ? extras.getCharSequence("android.text").toString() : "";

        i.putExtra("package", pack);
        i.putExtra("ticker", ticker);
        i.putExtra("title", title);
        i.putExtra("text", text);
        i.putExtra("tag", sbn.getTag());
        i.putExtra("id", sbn.getId());
        i.putExtra("key", Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? sbn.getKey() : "");

        sendBroadcast(i);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    private class NotificationServiceReceiver extends BroadcastReceiver {

        @SuppressWarnings("deprecation")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null)
                return;

            String pckg = intent.getStringExtra("package");
            String ticker = intent.getStringExtra("ticker");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");
            String tag = intent.getStringExtra("tag");
            int id = intent.getIntExtra("id", 0);
            String key = intent.getStringExtra("key");

            Log.i(TAG, "Package : " + pckg);
            Log.i(TAG, "Ticker : " + ticker);
            Log.i(TAG, "Title : " + title);
            Log.i(TAG, "Text : " + text);
            Log.i(TAG, "Tag : " + tag);
            Log.i(TAG, "Id : " + id);
            Log.i(TAG, "Key : " + key);

            if (!PrefsUtil.getBoolean(NotificationService.this, NotificationApplication.ENABLE_NOTIFICATION_ALLOW))
                return;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                NotificationService.this.cancelNotification(pckg, tag, id);
            } else {
                NotificationService.this.cancelNotification(key);
            }

        }
    }

}
