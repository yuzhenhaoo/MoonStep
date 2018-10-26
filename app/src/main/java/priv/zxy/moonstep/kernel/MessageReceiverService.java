package priv.zxy.moonstep.kernel;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import priv.zxy.moonstep.db.Message;
import priv.zxy.moonstep.helper.MoonStepHelper;
import priv.zxy.moonstep.helper.NotificationHelper;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;

import static priv.zxy.moonstep.kernel.bean.NotificationEnum.MessageTip;


/**
 * 创建人: Administrator
 * 创建时间: 2018/10/25
 * 描述: MessageService不能在开机的时候就监听到消息的原因：并没有在开机的时候就设置用户的自动登陆，也就是说，必须
 *       在接收到开机广播的时候，就让用户自动登陆，并且要在SharedPreferences中设置一个登陆的状态值
 *       如果上一次登陆成功的话，那么设置一个当前账号密码正确的标志位，开机的时候检测这个标记位是否存在，如果存在的话，不显示登陆页面，直接从StartActivity
 *       跳转到MainActivity中。
 *       上述操作同样需要增加逻辑来进行验证
 *       但是在开机广播中如果找到了当前的标志位，就要自动启动用户，并在用户点开应用的时候，完成数据的加载。
 *       不然，消息和其它各种事件的监听都将失效——因为用户失去了登陆状态。
 **/


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MessageReceiverService extends IntentService {

    private static final String TAG = "MessageReceiverService";

    private List<EMMessage> emMessages = null;

    private LocalBroadcastManager localBroadcastManager;

    private Intent intent;

    private EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            Log.d(TAG,"接收到了消息:");
            emMessages = messages;
            for(EMMessage message: emMessages){
//                Log.d(TAG,"message来源:    " + message.getFrom().substring(8, message.getFrom().length()));
                String[] msg = MoonStepHelper.getInstance().getMessageTypeWithBody(message.getBody().toString().trim());
                switch (MoonStepHelper.getInstance().transformMessageType(msg[0])){
                    case TEXT://处理文本消息
                        Log.e("Message","来自于Application" + msg[1]);
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
                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(Application.mContext);
                sharedPreferencesUtil.saveIsMessageTip(message.getFrom().substring(8, message.getFrom().length()));//当来消息的时候，将消息提示的标记存储到缓存中。
                localBroadcastManager.sendBroadcast(intent);//发送本地广播
                NotificationHelper.getInstance().showNotification(MessageTip);
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            Log.d(TAG, "收到透传消息");
            localBroadcastManager.sendBroadcast(intent);//发送本地广播
            NotificationHelper.getInstance().showNotification(MessageTip);
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            Log.d(TAG, "收到已读回执");
            localBroadcastManager.sendBroadcast(intent);//发送本地广播
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
            Log.d(TAG, "收到已送达回执");
            localBroadcastManager.sendBroadcast(intent);//发送本地广播
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            Log.d(TAG, "消息状态变动");
            localBroadcastManager.sendBroadcast(intent);//发送本地广播
        }
    };

    public MessageReceiverService() {
        super("MessageReceiverService");
    }



    @Override
    public void onCreate() {
        super.onCreate();
        localBroadcastManager = LocalBroadcastManager.getInstance(Application.mContext);

        intent = new Intent("priv.zxy.moonstep.commerce.view.LOCAL_BROADCAST");

        Log.d(TAG, TAG+"已经启动");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityCollector collector = new ActivityCollector();
        if ( collector.isEmpty()){
            //设置监听器
            EMClient.getInstance().chatManager().addMessageListener(msgListener);//实施消息的监听
        }
    }

    public void savedChattingMessage(String content, int direction, int type, String phoneNumber) {
        Message message = new Message();
        message.setContent(content);
        message.setDirection(direction);//0、对方发送的;1、我发送的;
        message.setObject(phoneNumber);
        message.setType(type);//1、文字；2、图片；3、音频；4、视频；5、红包；6、文件；7、位置
        message.save();
        Log.d(TAG, "savedChattingMessage:" + message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);//移除Listener
        Log.d(TAG,TAG + "已经被销毁");
    }
}
