package priv.zxy.moonstep.moonstep_palace.moon_friend.view;

import android.app.Activity;

public interface IChattingView {

    void FinishesThisActivity(Activity mActivity);

    //用来保存当前通信页面地数据
    void savedChattingMessage();

    void toPersonInfoPage();

    void sendMessageToMoonFriend();
}
