package com.ttb.quiz;

/**
 * Created by AndroidBash on 20-Aug-16.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ttb.quiz.View.Splash;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessageService";

    Context context = this;
    String mytitle, message, type, imageUri, text;
    int id;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Random r = new Random();
        id = r.nextInt(1000000000 - 0) + 0;
        Log.d(TAG, "From: " + remoteMessage.getData());
        Log.d(TAG, "From: " + remoteMessage.getData().get("google.sent_time"));
        mytitle = remoteMessage.getData().get("title");
        message = remoteMessage.getData().get("message");
        type = remoteMessage.getData().get("type");
        text = remoteMessage.getData().get("text");
        sendTextNotification(mytitle, message, text);

    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }


    private void sendTextNotification(String title, String messageBody, String text) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent i = new Intent(this, Splash.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        i.putExtra("imagein", "True");
//        i.putExtra("message", messageBody);
//        i.putExtra("imageUri", imageUri);
        PendingIntent intent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);
        builder.setTicker(messageBody);
        builder.setSmallIcon(R.drawable.logo);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.textnotification);
        contentView.setTextViewText(R.id.title, title);
        contentView.setTextViewText(R.id.text, messageBody);
        notification.contentView = contentView;
        if (Build.VERSION.SDK_INT >= 16) {
            // Inflate and set the layout for the expanded notification view
            RemoteViews expandedView =
                    new RemoteViews(getPackageName(), R.layout.textnotification);
            expandedView.setTextViewText(R.id.text_title, title);
            expandedView.setTextViewText(R.id.text, messageBody);
            expandedView.setTextViewText(R.id.text_message, text);
            notification.bigContentView = expandedView;
        }
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(id, notification);

    }
}


