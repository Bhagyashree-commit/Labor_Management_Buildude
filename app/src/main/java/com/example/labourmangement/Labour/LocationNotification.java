package com.example.labourmangement.Labour;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.labourmangement.R;
import com.google.firebase.messaging.RemoteMessage;

public class LocationNotification {
    private static final String NOTIFICATION_TAG = "Location";


    public static void notify(final Context context,
                              final String title, final String text){
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1){
            notifyPre(context, title, text);
        }else{
            notifyO(context, title, text);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void notifyO(Context context, final String title, final String text) {
        String channelId = createLocationChannel(context);
        Intent iStopService = new Intent(context, LocationTrackerService.class);
        iStopService.putExtra("key", "stop");
        PendingIntent piStopService = PendingIntent.getService(
                context, 1, iStopService, PendingIntent.FLAG_UPDATE_CURRENT);

        @SuppressLint("WrongConstant") Notification notification = new Notification.Builder(context, channelId)

                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.tracking)
                .setContentTitle(title)
                .setContentText(text)

                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


                .setContentIntent(piStopService)

                .addAction(
                        R.drawable.tracking,
                        context.getString(R.string.location),
                        piStopService)

                // Automatically dismiss the notification when it is touched.
                .setAutoCancel(false).build();
        notify(context, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String createLocationChannel(Context ctx) {
        // Create a channel.
        NotificationManager notificationManager =
                (NotificationManager)
                        ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence channelName = ctx.getString(R.string.location);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel notificationChannel =
                new NotificationChannel(
                        ctx.getString(R.string.location), channelName, importance);

        notificationManager.createNotificationChannel(
                notificationChannel);
        return ctx.getString(R.string.location);
    }


    public static void notifyPre(final Context context,
                                 final String title, final String text) {
        final Resources res = context.getResources();

        Intent iStopService = new Intent(context, LocationTrackerService.class);
        iStopService.putExtra("key", "stop");
        PendingIntent piStopService = PendingIntent.getService(
                context, 1, iStopService, PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)

                // Set appropriate defaults for the notification light, sound,
                // and vibration.
                .setDefaults(Notification.DEFAULT_ALL)

                .setSmallIcon(R.drawable.tracking)
                .setContentTitle(title)
                .setContentText(text)


                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                .setContentIntent(piStopService)

                .addAction(
                        R.drawable.tracking,
                        res.getString(R.string.location),
                        piStopService)

                // Automatically dismiss the notification when it is touched.
                .setAutoCancel(false);

        notify(context, builder.build());
    }

    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_TAG, 0, notification);
    }


    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(NOTIFICATION_TAG, 0);
    }
}
