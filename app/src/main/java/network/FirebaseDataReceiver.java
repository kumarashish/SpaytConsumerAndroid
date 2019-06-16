package network;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;


import com.spaytconsumer.GetOutstandingOrderDetails;
import com.spaytconsumer.R;

import common.AppController;
import prelogin.Login;

public class FirebaseDataReceiver extends BroadcastReceiver {

    private final String TAG = "FirebaseDataReceiver";
    AppController controller;

    public void onReceive(Context context, Intent intent) {
        controller=(AppController)context.getApplicationContext();
        String dataBundle = intent.getStringExtra("data");
        String title=intent.getStringExtra("heading");
        if(dataBundle!=null) {
             Log.d(TAG, dataBundle);
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 sendNotification(title, dataBundle, context);

             }else {
                 showNotification(title, dataBundle, context);
             }
        }
    }


    public void showNotification(String title,String message,Context context)
    {int notifyID = 023444455;
        String CHANNEL_ID = "my_channel_01";
        NotificationManager  mNotificationManager = (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent ;
        if(controller.getPrefManager().isUserLoggedIn())
        {
            notificationIntent = new Intent(context, GetOutstandingOrderDetails.class);
        }else{notificationIntent = new Intent(context, Login.class);}
        notificationIntent.putExtra("orderId",message);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 123, notificationIntent, 0);
        Builder mBuilder = new Builder(context)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle(title)
                .setContentText("You have received new Order, Order Id"+message)
                .setOnlyAlertOnce(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        mBuilder.setAutoCancel(true);
        mBuilder.setLocalOnly(false);
        mBuilder.setContentIntent(contentIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {       // For Oreo and greater than it, we required Notification Channel.
            CharSequence name = "My New Channel";                   // The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name, importance); //Create Notification Channel
            mNotificationManager .createNotificationChannel(channel);
        }


        mNotificationManager.notify(notifyID, mBuilder.build());


    }

    private void sendNotification(String title,String message,Context context) {
       int notifyID = 023444455;


        String CHANNEL_ID = "my_channel_01";            // The id of the channel.
        Intent intent;
        if(controller.getPrefManager().isUserLoggedIn())
        {
            intent = new Intent(context, GetOutstandingOrderDetails.class);
        }else{intent = new Intent(context, Login.class);}
        intent.putExtra("orderId",message);
        intent.putExtra("title", title);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "01")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.app_icon)
                .setContentText("You have received new Order, Order Id : "+message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {       // For Oreo and greater than it, we required Notification Channel.
            CharSequence name = "My New Channel";                   // The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name, importance); //Create Notification Channel
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(notifyID /* ID of notification */, notificationBuilder.build());
    }
}