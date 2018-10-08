package priv.zxy.moonstep.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;

import priv.zxy.moonstep.kernel_data.bean.ErrorCode;
import priv.zxy.moonstep.login.module.biz.IUser;
import priv.zxy.moonstep.login.module.biz.OnChangePasswordListener;
import priv.zxy.moonstep.login.module.biz.UserBiz;
import priv.zxy.moonstep.login.view.IChangePasswordView;

/**
 * Created by Zxy on 2018/9/20
 */

public class UserChangePasswordPresenter {
    private IUser userBiz;
    private IChangePasswordView changePasswordView;
    private Context mContext;
    private Activity mActivity;

    public UserChangePasswordPresenter(IChangePasswordView changePasswordView, Context mContext, Activity mActivity){
        this.changePasswordView = changePasswordView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.userBiz = new UserBiz();
    }

    public void toLoginActivity(){
        changePasswordView.toLoginActivity();
    }

    public void setChangePassword(final Context mContext, final Activity mActivity, String phoneNumber, String password, String confirmPassword) throws InterruptedException {
        userBiz.setChangePassword(mContext, mActivity, phoneNumber, password, confirmPassword, new OnChangePasswordListener() {
            @Override
            public void changePasswordSuccess() throws InterruptedException {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        changePasswordView.showSuccessTip();
                        Looper.loop();
                    }
                }).start();
                changePasswordView.toLoginActivity();
                changePasswordView.hideLoading();
            }

            @Override
            public void changePasswordFail(final ErrorCode errorCode) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        changePasswordView.showErrorTip(errorCode);
                    }
                }).start();
            }
        });
    }
}
