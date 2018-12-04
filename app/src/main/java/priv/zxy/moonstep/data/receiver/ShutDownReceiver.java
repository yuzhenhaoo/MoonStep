package priv.zxy.moonstep.data.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.EMClient;

/**
 * 创建人: Administrator
 * 创建时间: 2018/10/27
 * 描述: 用来监听关机广播
 *       目的是为了在关机的时候完成用户的登出操作
 **/
public class ShutDownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)){
            EMClient.getInstance().logout(true);
        }
    }
}