package priv.zxy.moonstep.login.presenter;

import android.app.Activity;
import android.content.Context;

import priv.zxy.moonstep.login.module.biz.IUser;
import priv.zxy.moonstep.login.module.biz.UserBiz;
import priv.zxy.moonstep.login.view.IForgetPasswordView;

public class UserForgetPasswordPresenter {
    private IUser userBiz;
    private IForgetPasswordView forgetPasswordView;
    private Context mContext;
    private Activity mActivity;

    public UserForgetPasswordPresenter(IForgetPasswordView forgetPasswordView, Context mContext, Activity mActivity){
        this.forgetPasswordView = forgetPasswordView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.userBiz = new UserBiz();
    }

    public void toLoginActivity(){
        forgetPasswordView.toLoginActivity();
    }

    public void fixPassword(String country, String phoneNumber, String codeNum, String password, String confirmPassword){
        userBiz.fixPassword(country, phoneNumber, codeNum, password, confirmPassword, mContext, mActivity);
    }


}
