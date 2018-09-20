package priv.zxy.moonstep.login.module.biz;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import priv.zxy.moonstep.Utils.LoginUtil;
import priv.zxy.moonstep.Utils.ToastUtil;

/**
 * Created by Zxy on 2018/9/20
 */
public class UserBiz implements IUser {

    /**
     * 在做doLogin()之前，你必须先开启刷新页面，否则将会影响用户的体验
     * 当doLogin()方法的调用结束以后，你必须关闭刷新页面，否则将会无法使界面对用户进行响应
     * @param mActivity 调用者当前Activity
     * @param mContext 调用者当前的上下文
     * @param userName 用户名
     * @param userPassword 用户密码
     * @param loginListener 登陆监听
     */
    @Override
    public void doLogin(Activity mActivity, Context mContext, String userName, String userPassword, OnLoginListener loginListener) {
        if (userName != null && userPassword != null) {
            LoginUtil loginUtil = new LoginUtil(mContext, mActivity);
            loginUtil.LoginRequest(userName, userPassword);
            boolean isSuccess = false;
            //这里开启一个延迟，用来获得正确的反馈结果
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            isSuccess = loginUtil.isSuccess;//获得反馈的结果
            if(isSuccess){
                loginListener.loginSuccess();
            }else{
                loginListener.loginFail();
            }
        } else {
            ToastUtil toastUtil = new ToastUtil(mContext, mActivity);
            toastUtil.showToast("您的账户/密码不能为空哦！");
        }
    }

    /**
     * 在做doRegister之前，你必须先开启页面的刷新，否则将会影响到用户体验
     * 在doRegister调用完成后，你必须关闭页面的刷新机制，否则程序将会无法对用户进行响应
     * @param mActivity 得到调用的当前Activity
     * @param mContext 得到当前的上下文环境
     * @param userName 用户名
     * @param userPassword 用户密码
     * @param gender 性别
     * @param registerListener 注册监听
     */
    @Override
    public void doRegister(Activity mActivity, Context mContext, String userName, String userPassword, String gender, OnRegisterListener registerListener) {

    }
}
