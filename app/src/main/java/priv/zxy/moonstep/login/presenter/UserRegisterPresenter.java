package priv.zxy.moonstep.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;

import priv.zxy.moonstep.Utils.ShowErrorReason;
import priv.zxy.moonstep.Utils.ToastUtil;
import priv.zxy.moonstep.login.module.bean.ErrorCode;
import priv.zxy.moonstep.login.module.biz.IUser;
import priv.zxy.moonstep.login.module.biz.OnRegisterListener;
import priv.zxy.moonstep.login.module.biz.OnUserNameCheckListener;
import priv.zxy.moonstep.login.module.biz.UserBiz;
import priv.zxy.moonstep.login.view.IUserRegisterView;

/**
 *  Created by Zxy on 2018/9/21
 */

public class UserRegisterPresenter {
    private IUser userBiz;
    private IUserRegisterView userRegisterView;//创建与LoginView交互的View对象
    private Activity mActivity;
    private Context mContext;

    public UserRegisterPresenter(IUserRegisterView userRegisterView, Activity mActivity, Context mContext){
        this.userRegisterView = userRegisterView;
        this.userBiz = new UserBiz();
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    public void UserNameCheck(String userName) throws InterruptedException {
        userBiz.checkUserName(userName, mContext, mActivity, new OnUserNameCheckListener() {
            @Override
            public void success() {
                userRegisterView.showUserNameSuccessTip();
            }

            @Override
            public void fail(ErrorCode errorCode) {
                userRegisterView.showUserNameFailTip(errorCode);
            }
        });
    }

    public void doRegister(String userName, String userPassword, String confirmUserPassword, String gender) throws InterruptedException {
        userBiz.doRegister(mActivity, mContext, userName, userPassword, confirmUserPassword,gender, new OnRegisterListener() {
            @Override
            public void registerSuccess() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        userRegisterView.showRegisterSuccessTip();
                    }
                }).start();
                userRegisterView.toMainActivity();
                userRegisterView.finishActivitySelf();
            }

            @Override
            public void registerFail(final ErrorCode errorCode) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        userRegisterView.showRegisterFailTip(errorCode);
                        Looper.loop();
                    }
                }).start();
            }
        });
    }


}
