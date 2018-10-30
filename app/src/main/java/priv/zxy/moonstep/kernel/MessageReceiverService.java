package priv.zxy.moonstep.kernel;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import priv.zxy.moonstep.utils.LogUtil;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import priv.zxy.moonstep.BuildConfig;
import priv.zxy.moonstep.db.Message;
import priv.zxy.moonstep.helper.MoonStepHelper;
import priv.zxy.moonstep.helper.NotificationHelper;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;

import static priv.zxy.moonstep.kernel.bean.NotificationEnum.MessageTip;

/**
 * 创建人: Administrator
 * 创建时间: 2018/10/27
 * 描述: 创建一个后台Service，用来在用户接收到开机广播的时候启动
 *       为什么这里不使用IntentService呢？
 *       因为IntentService处理逻辑的handleIntent中，确实是一个新的线程，我们也不用再去重复启动新线程的重复性操作
 *       但是handleIntent是集开启线程和自动停止为一身的Service,封装的太好了，致使这里的逻辑处理只能执行一次就要被摧毁，不适合充当事件监听的Service
 **/
public class MessageReceiverService extends Service {

    private static final String TAG = "MessageReceiverService";

    private List<EMMessage> emMessages = null;

    private LocalBroadcastManager localBroadcastManager;

    private Intent intent;

    private EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            LogUtil.d(TAG,"接收到了消息:");
            emMessages = messages;
            for(EMMessage message: emMessages){
//                LogUtil.d(TAG,"message来源:    " + message.getFrom().substring(8, message.getFrom().length()));
                String[] msg = MoonStepHelper.getInstance().getMessageTypeWithBody(message.getBody().toString().trim());
                switch (MoonStepHelper.getInstance().transformMessageType(msg[0])){
                    case TEXT://处理文本消息
                        LogUtil.e(TAG,"来自于MessageReceiverService" + msg[1]);
                        savedChattingMessage(msg[1], 0, 1, message.getFrom().substring(8));
                        break;
                    case IMAGE://处理图片消息
                        break;
                    case VIDEO://处理视频消息
                        break;
                    case LOCATION://处理位置消息
                        break;
                    case VOICE://处理声音消息
                        break;
                }
                SharedPreferencesUtil.getInstance(Application.getContext()).saveIsMessageTip(message.getFrom().substring(8, message.getFrom().length()));//当来消息的时候，将消息提示的标记存储到缓存中。
                localBroadcastManager.sendBroadcast(intent);//发送本地广播
                NotificationHelper.getInstance().showNotification(MessageTip);
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            LogUtil.d(TAG, "收到透传消息");
            localBroadcastManager.sendBroadcast(intent);//发送本地广播
            NotificationHelper.getInstance().showNotification(MessageTip);
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            LogUtil.d(TAG, "收到已读回执");
            localBroadcastManager.sendBroadcast(intent);//发送本地广播
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
            LogUtil.d(TAG, "收到已送达回执");
            localBroadcastManager.sendBroadcast(intent);//发送本地广播
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            LogUtil.d(TAG, "消息状态变动");
            localBroadcastManager.sendBroadcast(intent);//发送本地广播
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        localBroadcastManager = LocalBroadcastManager.getInstance(Application.getContext());

        intent = new Intent("priv.zxy.moonstep.commerce.view.LOCAL_BROADCAST");

        LogUtil.d(TAG, TAG+"已经启动");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(TAG, "Thread is running!");
        new Thread(() -> {
            LogUtil.d(TAG, ActivityCollector.activities.toString());
            if ( ActivityCollector.isEmpty()){//如果当前没有activity在栈中，也就是说，程序没有显示在页面上，我们对消息实施监听。
                //设置监听器
                EMClient.getInstance().chatManager().addMessageListener(msgListener);//实施消息的监听
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, TAG + "已经销毁了");
    }

    public void savedChattingMessage(String content, int direction, int type, String phoneNumber) {
        Message message = new Message();
        message.setContent(content);
        message.setDirection(direction);//0、对方发送的;1、我发送的;
        message.setObject(phoneNumber);
        message.setType(type);//1、文字；2、图片；3、音频；4、视频；5、红包；6、文件；7、位置
        message.save();
        LogUtil.d(TAG, "savedChattingMessage:" + message);
    }
}
