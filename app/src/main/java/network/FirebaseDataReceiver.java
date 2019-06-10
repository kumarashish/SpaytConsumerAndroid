package network;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;


import com.spaytconsumer.GetOutstandingOrderDetails;
import com.spaytconsumer.R;

public class FirebaseDataReceiver extends BroadcastReceiver {

    private final String TAG = "FirebaseDataReceiver";

    public void onReceive(Context context, Intent intent) {
        String dataBundle = intent.getStringExtra("data");
        String title=intent.getStringExtra("heading");
        if(dataBundle!=null) {
             Log.d(TAG, dataBundle);
            showNotification(title,dataBundle,context);
        }
    }


    public void showNotification(String title,String message,Context context)
    {
        NotificationManager  mNotificationManager = (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, GetOutstandingOrderDetails.class);
        notificationIntent.putExtra("orderId",message);
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
        mNotificationManager.notify(123, mBuilder.build());

    }
}