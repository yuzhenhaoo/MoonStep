package priv.zxy.moonstep.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.preference.Preference;

import priv.zxy.moonstep.Utils.SharedPreferencesUtil;
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
                userLoginView.toMainActivity();
            }

            @Override
            public void loginFail() {

            }
        });
        userLoginView.hideLoading();
    }

    /**
     * 初始化用户登陆时候的用户名和密码
     * 利用Preference的工具类来实现自动填充
     * @param preference SharedPreferences工具类对象
     */
    public void initAccountAndPassword(SharedPreferencesUtil preference){
        userLoginView.initAccount(preference);
        userLoginView.initPassword(preference);
    }

    public void showLoading(){
        userLoginView.showLoading();
    }

    public void hideLoading(){
        userLoginView.hideLoading();
    }

    /**
     * 封装了一个线程休眠的函数
     * @param millis 线程休眠的时间，单位是ms
     */
    public void threadSleep(final long millis){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.obtainMessage(0x01).sendToTarget();
            }
        }).start();
    }
}
