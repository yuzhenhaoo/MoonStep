package priv.zxy.moonstep.moonstep_palace.moon_friend.presenter;

import android.app.Activity;
import android.content.Context;

import java.util.List;
import priv.zxy.moonstep.kernel_data.bean.User;
import priv.zxy.moonstep.moonstep_palace.moon_friend.module.biz.IMoonFriendBiz;
import priv.zxy.moonstep.moonstep_palace.moon_friend.module.biz.MoonFriendBiz;
import priv.zxy.moonstep.moonstep_palace.moon_friend.view.IMoonFriendView;

public class MoonFriendPresenter {
    IMoonFriendBiz moonFriendBiz;
    IMoonFriendView moonFriendView;
    Context mContext;
    Activity mActivity;

    public MoonFriendPresenter(IMoonFriendView moonFriendView, Context mContext, Activity mActivity){
        this.moonFriendView = moonFriendView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.moonFriendBiz = new MoonFriendBiz();
    }

}
