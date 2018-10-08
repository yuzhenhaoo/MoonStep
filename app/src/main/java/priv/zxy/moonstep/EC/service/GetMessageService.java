package priv.zxy.moonstep.EC.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.LinkedHashSet;
import java.util.List;

public class GetMessageService extends Service {
    private static final String TAG = "GetMessageService";

    //通过EM获取好友的消息队列
    private EMMessageListener msgListener;

    //利用EMMessage对内部地数据进行去重后返回给Activity
    private List<EMMessage> emMessages;

    //定义onBinder方法所返回地对象
    private MyBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        public List<EMMessage> getMessages(){
            return emMessages;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"GetMessageService");
        initMessageQueue();
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int listenTime = 1000;//设置监听时间为100ms
        long triggerAtTime = SystemClock.elapsedRealtime() + listenTime;

        Intent i = new Intent(this, GetMessageService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 通过该函数来接收好友发送的消息
     */
    public void initMessageQueue(){
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
//                Log.e("TAG","收到的消息为:");
//                for(EMMessage message : messages){
//                    Log.e("TAG", message.toString());
//                }
                removeDuplicate(messages);
                emMessages = messages;
                for(EMMessage message: emMessages){
                    Log.d(TAG, message.toString());
                }
                Intent intent = new Intent();
                // 设置发送广播的类型，可以随便写一个
                intent.setAction("priv.zxy.moonstep.EC.service");
                sendBroadcast(intent);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
    }

    private static void removeDuplicate(List<EMMessage> list) {
        LinkedHashSet<EMMessage> set = new LinkedHashSet<EMMessage>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
    }

    @Override
    public void onDestroy() {
        removeMessqgeQueueListener();
        super.onDestroy();
    }

    /**
     * 用来移除MainActivity中设置的消息监听
     */
    public void removeMessqgeQueueListener(){
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
}
