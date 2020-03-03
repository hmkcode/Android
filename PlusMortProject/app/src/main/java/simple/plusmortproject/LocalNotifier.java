package simple.plusmortproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.service.notification.StatusBarNotification;

public class LocalNotifier extends NotificationManager {



    @Override
    public void notify(int id, Notification notification) {
        super.notify(id, notification);
    }

    @Override
    public void notify(String tag, int id, Notification notification) {
        super.notify(tag, id, notification);
    }

    @Override
    public void cancel(int id) {
        super.cancel(id);
    }

    @Override
    public void cancel(String tag, int id) {
        super.cancel(tag, id);
    }

    @Override
    public void cancelAll() {
        super.cancelAll();
    }

    @Override
    public boolean isNotificationPolicyAccessGranted() {
        return super.isNotificationPolicyAccessGranted();
    }

    @Override
    public Policy getNotificationPolicy() {
        return super.getNotificationPolicy();
    }

    @Override
    public void setNotificationPolicy(Policy policy) {
        super.setNotificationPolicy(policy);
    }

    @Override
    public StatusBarNotification[] getActiveNotifications() {
        return super.getActiveNotifications();
    }
}
