package priv.zxy.moonstep.data.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import priv.zxy.moonstep.executor.ExecutorManager;
import priv.zxy.moonstep.service.MessageReceiverService;
import priv.zxy.moonstep.util.LogUtil;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.Map;

import priv.zxy.moonstep.util.SharedPreferencesUtil;

/**
 * 用来接收开机广播
 * 在onReceive中我们需要做的事情只有接受boot broadcast和判断之前是否成功登录过两件事情
 * 如果我们判断之前已经成功登录过，那我们直接在接收到开机广播的时候就直接在环信后台登录用户的数据，并且开启服务使后台启动
 * 如果之前没有成功登陆过/上次登录失败，那我们不开启后台Service占用用户的手机资源，而是在用户第一次登录的时候，在LogUtilinUtil中去开启后台Service，
 *              目的是为了保证当app被用户关闭的时候，后台仍然维持着运转。
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        // 在这里开启我们的一个后台Service默默加载一些消息。
        if (intent.getAction() != null && intent.getAction().equals(ACTION) && SharedPreferencesUtil.isFirstLogin()){
            //同时应该在这里登录环信服务器
            final Map<String, String> LoginInfo = SharedPreferencesUtil.readLoginInfo();
            ExecutorManager.getInstance().execute(() -> EMClient.getInstance().login("moonstep" + LoginInfo.get("UserName"), LoginInfo.get("PassWd"), new EMCallBack() {
                @Override
                public void onSuccess() {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    LogUtil.d(TAG, "后台登录服务器成功");
                    //只有服务器登录成功的时候，才可以启动服务
                    context.startService(new Intent(context, MessageReceiverService.class));
                }

                @Override
                public void onProgress(int progress, String status) {
                    LogUtil.d(TAG, "后台登录服务器的进度:" + progress);
                }

                @Override
                public void onError(int code, String message) {
                    LogUtil.d(TAG, "后台登录服务器失败!" + code);
                }
            }));
        }
    }
}
