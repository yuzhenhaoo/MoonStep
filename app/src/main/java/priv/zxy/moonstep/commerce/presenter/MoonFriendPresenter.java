package priv.zxy.moonstep.commerce.presenter;

import android.app.Activity;
import android.content.Context;

import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import priv.zxy.moonstep.db.MoonFriend;
import priv.zxy.moonstep.commerce.module.biz.IMoonFriendBiz;
import priv.zxy.moonstep.commerce.module.biz.MoonFriendBiz;
import priv.zxy.moonstep.commerce.view.IMoonFriendView;

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

    public void checkClientAndDatabase(List<MoonFriend> lists) throws HyphenateException, InterruptedException {
        moonFriendBiz.checkClientAndDatabase(lists);
    }

}
