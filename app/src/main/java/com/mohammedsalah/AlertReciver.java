package com.mohammedsalah;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.mohammedsalah.salah.chat;

import com.mohammedsalah.salah.notekeeper.R;

public class AlertReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        PendingIntent notification = PendingIntent.getActivity(context,
                0, new Intent(context, chat.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_launcher)
                .setContentText(" \" new message received \" ").setContentTitle("Chat Room")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setShowWhen(true);

        builder.setContentIntent(notification);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
        notificationManager.notify(1, builder.build());


    }
}
