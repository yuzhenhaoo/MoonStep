package priv.zxy.moonstep.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import priv.zxy.moonstep.login.module.biz.IUser;
import priv.zxy.moonstep.login.module.biz.OnLoginListener;
import priv.zxy.moonstep.login.module.biz.UserBiz;
import priv.zxy.moonstep.login.view.IUserLoginView;

public class UserLoginPresenter {
    private IUser userBiz;
    private IUserLoginView userLoginView;
    private Activity mActivity;
    private Context mContext;
    private Handler handler = new Handler();

    public UserLoginPresenter(IUserLoginView userLoginView, Activity mActivity, Context mContext){
        this.userLoginView = userLoginView;
        this.userBiz = new UserBiz();
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    /**
     * 这个函数里实现View层和Module层的交互
     */
    public void login(){
        userLoginView.showLoading();//登陆的开始，开启加载
        userBiz.doLogin(mActivity, mContext, userLoginView.getUserName(), userLoginView.getUserPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess() {

            }

            @Override
            public void loginFail() {

            }
        });
    }
}
