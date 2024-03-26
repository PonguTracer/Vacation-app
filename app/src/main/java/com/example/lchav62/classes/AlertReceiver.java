package com.example.lchav62.classes;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.lchav62.R;



public class AlertReceiver extends BroadcastReceiver {

    // Define a notification channel ID for Android Oreo and above
    String channelId = "channel_id";

    @Override
    public void onReceive(Context context, Intent intent) {

        // Display a toast with the notification message received from the intent
        Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();

        // Create the notification channel
        createNotificationChannel(context, channelId);

        // Build the notification using NotificationCompat Builder
        Notification n = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("key"))
                .setContentTitle("NotificationTest").build();

        // Get the notification manager and notify the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.notificationID++, n);
    }

    // Method to create the notification channel
    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = "vacationChannel";
        String description = "vacationAlerts";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        // Create the notification channel with the specified ID, name, and importance
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        // Register the channel with the system to enable it for use
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
