package com.github.booknara.notification;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.github.booknara.notification.util.PrefsUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Switch mNotification;
    private AlertDialog mShowAllowNotificationAccessDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotification = (Switch) findViewById(R.id.stop_notification_switch);
        Button newNotification = (Button) findViewById(R.id.create_noti_btn);
        Button clearNotification = (Button) findViewById(R.id.clear_noti_btn);
        newNotification.setOnClickListener(this);
        clearNotification.setOnClickListener(this);

        initNotificationSwitch();
    }

    @Override
    public void onResume() {
        super.onResume();
        mNotification.setChecked(PrefsUtil.getBoolean(this, NotificationApplication.ENABLE_NOTIFICATION_ALLOW));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification_settings:
                showAllowNotificationAccessDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initNotificationSwitch() {
        mNotification.setChecked(PrefsUtil.getBoolean(this, NotificationApplication.ENABLE_NOTIFICATION_ALLOW));
        mNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrefsUtil.addBoolean(MainActivity.this, NotificationApplication.ENABLE_NOTIFICATION_ALLOW, isChecked);
                if (isChecked) {
                    // ON
                    if (!Settings.Secure.getString(MainActivity.this.getContentResolver(), "enabled_notification_listeners").contains(getApplicationContext().getPackageName()))
                        showAllowNotificationAccessDialog();
                } else {
                    // OFF
                }
            }
        });
    }

    public void onClick(View v){
        if(v.getId() == R.id.create_noti_btn){
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("New Notification");
            builder.setContentText("Test notification message");
            builder.setTicker("Test notification message");
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setAutoCancel(true);
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        } else if(v.getId() == R.id.clear_noti_btn){
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
        }
    }

    private void showAllowNotificationAccessDialog() {
        if (mShowAllowNotificationAccessDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.allow_notification_access_title)
                    .setMessage(R.string.allow_notification_access_msg)
                    .setCancelable(false)
                    .setPositiveButton(R.string.lbl_go_to_setting, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent notificationSettingIntent;
                            if (Build.VERSION.SDK_INT < 18)
                                notificationSettingIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                            else
                                notificationSettingIntent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);

                            try {
                                startActivity(notificationSettingIntent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(MainActivity.this, R.string.notifications_settings_not_found, Toast.LENGTH_LONG);
                            }

                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(R.string.lbl_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            mShowAllowNotificationAccessDialog = builder.create();
        }

        if (!mShowAllowNotificationAccessDialog.isShowing())
            mShowAllowNotificationAccessDialog.show();
    }
}
