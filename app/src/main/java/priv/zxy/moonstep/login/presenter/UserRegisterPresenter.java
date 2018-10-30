package priv.zxy.moonstep.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;

import com.hyphenate.exceptions.HyphenateException;

import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.login.module.biz.IUser;
import priv.zxy.moonstep.login.module.biz.OnRegisterListener;
import priv.zxy.moonstep.login.module.biz.UserBiz;
import priv.zxy.moonstep.login.view.IUserRegisterView;

/**
 *  Created by Zxy on 2018/9/21
 */

public class UserRegisterPresenter {
    private IUser userBiz;
    private IUserRegisterView userRegisterView;//创建与LogUtilinView交互的View对象
    private Activity mActivity;
    private Context mContext;

    public UserRegisterPresenter(IUserRegisterView userRegisterView, Activity mActivity, Context mContext){
        this.userRegisterView = userRegisterView;
        this.userBiz = new UserBiz();
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    public void doRegister(String phoneNumber, String nickName, String userPassword, String confirmUserPassword, String gender) throws InterruptedException, HyphenateException {
        userBiz.doRegister(phoneNumber, nickName, userPassword, confirmUserPassword, gender, new OnRegisterListener() {
            @Override
            public void registerSuccess() {
                new Thread(() -> userRegisterView.showRegisterSuccessTip()).start();
                userRegisterView.toMainActivity();
                userRegisterView.finishActivitySelf();
            }

            @Override
            public void registerFail(final ErrorCode errorCode) {
                new Thread(() -> {
                    Looper.prepare();
                    userRegisterView.showRegisterFailTip(errorCode);
                    Looper.loop();
                }).start();
            }
        });
    }
}
