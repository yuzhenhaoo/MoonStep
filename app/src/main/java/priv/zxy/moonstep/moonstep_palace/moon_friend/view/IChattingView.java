package priv.zxy.moonstep.moonstep_palace.moon_friend.view;

import android.app.Activity;

import priv.zxy.moonstep.db.Message;

public interface IChattingView {

    void FinishesThisActivity(Activity mActivity);

    //用来保存当前通信页面地数据
    Message savedChattingMessage(String message, int type);

    void toPersonInfoPage();

    void sendMessageToMoonFriend();

    String getTime();
}
