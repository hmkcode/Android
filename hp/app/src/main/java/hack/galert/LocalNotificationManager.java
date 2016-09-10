package hack.galert;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Ankit on 9/10/2016.
 */
public class LocalNotificationManager {

    public static LocalNotificationManager mInstance;
    private static Context context;
    private int mNotificationId = 0;

    public LocalNotificationManager() {
    }

    public LocalNotificationManager(Context context) {
        this.context = context;
    }

    public static LocalNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LocalNotificationManager(context);
        }
        return mInstance;
    }

    public void launchNotification(String msg) {

        //TODO: change icon and add pendingIntent , which navigates user to downloads activity

//        Intent intent = new Intent(context, DowloadsActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        // mBuilder.setSmallIcon(R.drawable.app_icon);
        mBuilder.setContentTitle("MusicGenie");
        mBuilder.setContentText(msg);
        //mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        this.mNotificationId += 1;

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(mNotificationId, mBuilder.build());
    }

}
