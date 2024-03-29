package com.app.pakreformers.services;


import android.app.Notification;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.app.pakreformers.R;
import com.app.pakreformers.info.Info;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService implements Info {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        Notification notification2 = new NotificationCompat.Builder(this, "CHANNEL_ID_2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(2, notification2);

    }

    @Override
    public void onNewToken(@NonNull String s) {
        String refreshedToken = String.valueOf(FirebaseMessaging.getInstance().getToken());
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        Log.d(TAG, "Refreshed token: --" + s);
    }
}
