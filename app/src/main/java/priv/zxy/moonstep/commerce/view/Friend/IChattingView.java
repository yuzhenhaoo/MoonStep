package priv.zxy.moonstep.commerce.view.Friend;

import android.app.Activity;

import priv.zxy.moonstep.framework.message.Message;

public interface IChattingView {

    void finisheThisActivity(Activity mActivity);

    //用来保存当前通信页面地数据
    Message savedChattingMessage(String message,int direction, int type, String phoneNumber);

    void toPersonInfoPage();

    void sendMessageToMoonFriend();

    String getTime();
}
