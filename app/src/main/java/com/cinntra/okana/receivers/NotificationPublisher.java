package com.cinntra.okana.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cinntra.okana.R;


public class NotificationPublisher extends BroadcastReceiver {

    String description,title;


    @Override
    public void onReceive(Context context, Intent intent) {
       /* NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);*/

//        PendingIntent pendingIntent = PendingIntent.getActivity(context,PendingIntent.FLAG_UPDATE_CURRENT);

        description = (String) intent.getExtras().get("value");
        title       = (String) intent.getExtras().get("value");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"androidnotification")
                .setContentText(description)
                .setTicker(title)
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.logo))
                .setContentTitle(title)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        NotificationManagerCompat notificationManagerCompat  = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());

    }
}
