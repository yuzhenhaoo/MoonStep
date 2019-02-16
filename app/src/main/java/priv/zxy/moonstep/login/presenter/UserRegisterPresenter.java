package priv.zxy.moonstep.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.hyphenate.exceptions.HyphenateException;

import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.login.module.biz.IUser;
import priv.zxy.moonstep.login.module.biz.UserBiz;
import priv.zxy.moonstep.login.view.IUserRegisterView;

/**
 *  Created by Zxy on 2018/9/21
 */

public class UserRegisterPresenter {

    private static final String TAG = "UserRegisterPresenter";

    private IUser userBiz;

    private IUserRegisterView userRegisterView;//创建与LoginView交互的View对象

    public UserRegisterPresenter(IUserRegisterView userRegisterView, Activity mActivity, Context mContext){
        this.userRegisterView = userRegisterView;
        this.userBiz = new UserBiz();
    }

    public void doRegister(String phoneNumber, String nickName, String userPassword, String confirmUserPassword, String gender) throws InterruptedException, HyphenateException {
        userBiz.doRegister(phoneNumber, nickName, userPassword, confirmUserPassword, gender, new UserBiz.OnRegisterListener() {
            @Override
            public void registerSuccess(String raceName, String raceDescription, String raceImage, String raceIcon) {
                Log.d(TAG, "注册成功");
            }

            @Override
            public void registerFail(ErrorCodeEnum errorCode) {
                new Thread(() -> {
                    Looper.prepare();
                    userRegisterView.showErrorTip(errorCode);
                    Looper.loop();
                }).start();
            }
        });
    }
}
