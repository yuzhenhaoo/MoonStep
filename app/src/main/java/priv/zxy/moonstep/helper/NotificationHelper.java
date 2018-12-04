package priv.zxy.moonstep.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.bean.NotificationEnum;

/**
 * 创建人: Administrator
 * 创建时间: 2018/10/23
 * 描述: 构建一个通知的帮助类，用来封装系统中弹出的各种通知。
 **/

public class NotificationHelper {

    private static NotificationHelper instance;

    private static Context mContext;

    private static NotificationManager manager;

    public static NotificationHelper getInstance() {
        if (instance == null){
            synchronized (NotificationHelper.class){
                if (instance == null){
                    instance = new NotificationHelper();
                    mContext = Application.getContext();
                    manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                }
            }
        }
        return instance;
    }

    /**
     * 对于消息提示的通知应该放在后台中，当应用程序整个关闭的时候，只有一个后台在保活，这个时候检测是否有信息传递过来，如果有的话，就发送一条通知让用户看到
     * Notification Level:PRIORITY_LOW
     * @return 通知实体
     */
    private Notification setMessageTipNofitication(){
        return new NotificationCompat.Builder(mContext, "default")
                .setContentText("月友消息提示")
                .setContentText("您的月友给您发送了新的消息")
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setSmallIcon(R.mipmap.small_icon)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_foreground))
                .setVibrate(new long[]{0, 1000, 1000, 1000}) //设置手机震动的时间
                .setLights(Color.WHITE, 1000, 1000)
                .build();
    }

    private int trsNotificationEnumToInt(NotificationEnum notificationEnum){
        switch (notificationEnum){
            case MessageTip:
                return 1;
        }
        return 0;
    }

    public void showNotification(NotificationEnum notificationEnum){
        switch (notificationEnum){
            case MessageTip:
                manager.notify(trsNotificationEnumToInt(notificationEnum), setMessageTipNofitication());
                break;
        }
    }
}
