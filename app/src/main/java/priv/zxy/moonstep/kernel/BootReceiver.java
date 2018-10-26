package priv.zxy.moonstep.kernel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //在这里开启我们的一个后台Service默默加载一些消息。
        Intent service = new Intent(context, MessageReceiverService.class);
        context.startService(service);
    }
}
