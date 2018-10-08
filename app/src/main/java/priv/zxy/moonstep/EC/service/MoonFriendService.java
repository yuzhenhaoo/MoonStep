package priv.zxy.moonstep.EC.service;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import priv.zxy.moonstep.EC.bean.OnMoonFriendListener;
import priv.zxy.moonstep.Utils.dbUtils.GetMoonFriendUtil;
import priv.zxy.moonstep.kernel_data.bean.User;

public class MoonFriendService extends Service {
    private static final String TAG = "MoonFriendService";

    private static List<User> moonFriends = new ArrayList<User>();

    //通过EM获取好友的消息队列
    private EMMessageListener msgListener;

    //利用EMMessage对内部地数据进行去重后返回给Activity
    private List<EMMessage> emMessages;

    //定义onBinder方法所返回地对象
    private MyBinder binder = new MyBinder();

    public class MyBinder extends Binder{

        /**
         * @return 获取当前Service的实例
         */
        public MoonFriendService getService(){
            return MoonFriendService.this;
        }
    }

    /**
     * 供Activity调用获得当前的MoonFriends列表
     * @return moonFriends
     */
    public List<User> getFriendsList(){
        return moonFriends;
    }

    /**
     * 供Activity调用获得当前的消息队列
     * @return emMessages
     */
    public List<EMMessage> getMessages(){
        return emMessages;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    /**
     * 注册回调接口的方法，供外部使用
     * @param onMoonFriendListener 回调接口对象
     */
    public void setOnMoonFriendListener(OnMoonFriendListener onMoonFriendListener){
        this.onMoonFriendListener = onMoonFriendListener;
    }
    @Override
    public void onCreate() {
        initMoonFriends();
        super.onCreate();
    }

    /**
     * 获取好友列表的接口
     */
    private OnMoonFriendListener onMoonFriendListener;

    /**
     * 这里应该是从登陆就可以开始监听到了
     */
    //必须要将获取好友的函数放在子线程里，不然会发生数据异常
    private static List<String> usernames = new ArrayList<>();

    public void initMoonFriends() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    for (String username : usernames) {
                        GetMoonFriendUtil util = new GetMoonFriendUtil(getApplicationContext());
                        util.returnMoonFriendInfo(username);
                        try {
                            Thread.sleep(120);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        moonFriends.add(util.getMoonFriend());
                    }
                    //来通知MoonFriend这个时候应该获取MoonFriend列表了
                    onMoonFriendListener.getMoonFriends(moonFriends);
                    Log.d(TAG, "run: EM获取好友列表成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.d(TAG, "run: EM获取好友列表失败");
                }
            }
        }).start();
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
