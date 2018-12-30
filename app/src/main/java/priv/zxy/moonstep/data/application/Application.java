package priv.zxy.moonstep.data.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.androidnetworking.AndroidNetworking;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.litepal.LitePalApplication;

import java.util.Iterator;
import java.util.List;

import priv.zxy.moonstep.DAO.PullUserInfoDAO;
import priv.zxy.moonstep.constant.SharedConstant;
import priv.zxy.moonstep.framework.message.MessageOnline;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.guide.StartActivity;
import priv.zxy.moonstep.helper.EMHelper;
import priv.zxy.moonstep.data.bean.EMBase;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.login.view.UserLoginActivity;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.SharedPreferencesUtil;
import priv.zxy.moonstep.helper.MoonStepHelper;
import priv.zxy.moonstep.main.view.MainActivity;
import priv.zxy.moonstep.commerce.view.Friend.ChattingActivity;
import priv.zxy.network.NetworkManager;

public class Application extends LitePalApplication {

    private static final String TAG = "Application";
    public static int START_IMAGE_MAX_NUMBER = 9;//这里设定的是startPage的数量
    private static final String MOONSTEP = "moonstep";

    /**
     * 记录是否已经初始化
     */
    private boolean isInit = false;

    private LocalBroadcastManager localBroadcastManager;

    private LocalReceiver localReceiver;

    private Intent intent;

    private IntentFilter intentFilter;

    private static Context context;

    private static boolean isBackground = false;

    /**
     * 与EM服务器交互的token值
     */
    private static String token = null;

    public static Context getContext(){
        return context;
    }

    public static RefWatcher mRefWatcher;//为了检测Fragment的内存泄漏

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        mRefWatcher = LeakCanary.install(this);

        context = getApplicationContext();

        NetworkManager.getInstance().init(this);

        //初始化AndroidNetworking
        AndroidNetworking.initialize(getApplicationContext());

        localBroadcastManager = LocalBroadcastManager.getInstance(context);//获取实例
        localReceiver = new LocalReceiver();

        intentFilter = new IntentFilter();
        intentFilter.addAction("priv.zxy.moonstep.kernel.bean.LOCAL_BROADCAST");
        intent = new Intent("priv.zxy.moonstep.commerce.view.LOCAL_BROADCAST");

        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        initEaseMob();
    }

    /**
     * 程序终止的时候执行
     * 当终止应用程序对象时调用，不保证一定被调用，
     * 当程序是被内核终止以便为其它应用程序释放资源，那么将不会提醒。
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.d(TAG, "程序被终止了");
        // 移除所有的网络监听观察者
        NetworkManager.getInstance().unRegisterAllObserver();
    }

    /**
     * 低内存的时候调用
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.d(TAG, "进入低内存环境");
    }

    /**
     * 程序再进行内存清理时执行
     * @param level 等级，记录应用即将进入后台运行
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            isBackground = true;
            // 进入后台的通知
//            notifyBackground();
        }
    }

    /**
     * 获取服务端的token值
     * @return token
     */
    public static String getToken(){
        return token;
    }

    /**
     * 存储聊天消息
     * @param content 内容
     * @param direction 传输方向
     * @param type 消息类型
     * @param phoneNumber 消息对象
     */
    public void savedChattingMessage(String content, int direction, int type, String phoneNumber) {
        MessageOnline.getInstance().saveMessageToDataBase(content, direction, type, phoneNumber);
    }

    /**
     * 初始化Mob短信SDK
     */
    private void initEaseMob() {
        // 获取当前进程 id 并取得进程名
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        /*
         * 如果app启用了远程的service，此application:onCreate会被调用2次
         * 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
         * 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
         */
        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            // 则此application的onCreate 是被service 调用的，直接返回
            return;
        }
        if (isInit) {
            return;
        }

        // 调用初始化方法初始化sdk
        EMClient.getInstance().init(context, initOptions());

        // 设置开启debug模式
        EMClient.getInstance().setDebugMode(true);

        // 设置初始化已经完成
        isInit = true;
    }


    /**
     * 清理数据库SQLite中存储的用户名，用户密码等信息
     */
    private void cleanLoginInformation(){
        SharedPreferencesUtil.getInstance(context).setFailLoginInfo();
    }

    /**
     * 获取用户信息
     */
    private void getUserInformation(){
        boolean isLoginResult = SharedPreferencesUtil.getInstance(context).isSuccessLogin();
        if (isLoginResult){
            String phoneNumber = SharedPreferencesUtil.getInstance(context).readLoginInfo().get(SharedConstant.PHONE_NUMBER);
            PullUserInfoDAO.getInstance().getUserInfo(new PullUserInfoDAO.Callback() {
                @Override
                public void onSuccess(User moonFriend) {
                    SharedPreferencesUtil.getInstance(context).saveMySelfInformation(moonFriend);
                }

                @Override
                public void onFail(ErrorCodeEnum errorCode) {
                    LogUtil.d(TAG, "获取个人信息失败");
                }
            }, MOONSTEP + phoneNumber);
        }
    }

    /**
     * SDK初始化的一些配置
     * 关于 EMOptions 可以参考官方的 API 文档
     * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1chat_1_1_e_m_options.html
     */
    private EMOptions initOptions() {

        EMOptions options = new EMOptions();
        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
        // options.setAppKey("lzan13#hxsdkdemo");
        // 设置自动登录
        /*
         * 自动登录在以下几种情况下会被取消：
             用户调用了 SDK 的登出动作；
             用户在别的设备上更改了密码，导致此设备上自动登录失败；
             用户的账号被从服务器端删除；
             用户从另一个设备登录，把当前设备上登录的用户踢出。
         */
//        options.setAutoLogUtilin(true);
        // 设置是否需要发送已读回执
        options.setRequireAck(true);
        // 设置是否需要发送回执，
        options.setRequireDeliveryAck(true);
        // 设置是否根据服务器时间排序，默认是true
        options.setSortMessageByServerTime(false);
        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
        options.setAcceptInvitationAlways(false);
        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
        options.setAutoAcceptGroupInvitation(false);
        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
        options.setDeleteMessagesAsExitGroup(false);
        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
        options.allowChatroomOwnerLeave(true);
        // 设置google GCM推送id，国内可以不用设置
        // options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
        // 设置集成小米推送的appid和appkey
        // options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);

        return options;
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // LogUtil.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    private class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

                EMHelper.getInstance(context).initToken(token -> {
                            Application.token = token;
                            LogUtil.d(TAG, token);
                });
        }
    }

    //通过EM获取好友的消息队列
    private EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            LogUtil.d(TAG,"接收到了消息:");
            for(EMMessage message: messages){
//                LogUtil.d(TAG,"message来源:    " + message.getFrom().substring(8, message.getFrom().length()));
                String[] msg = MoonStepHelper.getInstance().getMessageTypeWithBody(message.getBody().toString().trim());
                switch (MoonStepHelper.getInstance().transformMessageType(msg[0])){
                    case TEXT://处理文本消息
                        LogUtil.e("MessageOnline","来自于Application" + msg[1]);
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
                SharedPreferencesUtil.getInstance(context).saveIsMessageTip(message.getFrom().substring(8, message.getFrom().length()));//当来消息的时候，将消息提示的标记存储到缓存中。
                localBroadcastManager.sendBroadcast(intent);//发送本地广播
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            LogUtil.d(TAG, "收到透传消息");
            localBroadcastManager.sendBroadcast(intent);//发送本地广播
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

    private ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {

        /**
         * 对MainActivity实施的监听：当登陆成功后，进入到MainActivity当中，就已经将登陆信息存入到了Preference当中，
         *     这个时候只要从Preference中直接取出phoneNumber就好
         * @param activity 活动
         * @param savedInstanceState 存储信息
         */
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            /*
            在UserLoginActivity中清空用户的登陆信息
                防止用户在第一登陆失败后，第二次回退后登陆可成功登陆
             */
            if (activity.getClass() == MainActivity.class) {
                EMClient.getInstance().chatManager().addMessageListener(msgListener);//实施消息的监听
                new Thread(() -> getUserInformation()).start();
            }
            if (activity.getClass() == ChattingActivity.class){
                EMClient.getInstance().chatManager().removeMessageListener(msgListener);//移除Listener
            }
            if (activity.getClass() == StartActivity.class){
                localBroadcastManager.registerReceiver(localReceiver, intentFilter);
                EMBase.getInstance().initDataFromDatabase();
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (activity.getClass() == ChattingActivity.class){
                EMClient.getInstance().chatManager().addMessageListener(msgListener);//实施消息的监听
            }
            if (activity.getClass() == MainActivity.class){
                EMClient.getInstance().chatManager().removeMessageListener(msgListener);//移除Listener
            }
            if (activity.getClass() == StartActivity.class){
                localBroadcastManager.unregisterReceiver(localReceiver);
            }
        }
    };
}
