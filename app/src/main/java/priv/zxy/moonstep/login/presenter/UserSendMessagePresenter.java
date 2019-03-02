package priv.zxy.moonstep.login.presenter;

import android.app.Activity;
import android.content.Context;

import priv.zxy.moonstep.login.module.biz.ILogin;
import priv.zxy.moonstep.login.module.biz.LoginBiz;
import priv.zxy.moonstep.login.view.ISendMessageView;

/**
 *  Created by Zxy on 2018/9/21
 */

public class UserSendMessagePresenter {
    private ILogin userBiz;
    private ISendMessageView sendMessageView;//创建与ConfrirmView交互的View对象
    private Activity mActivity;
    private Context mContext;

    public UserSendMessagePresenter(ISendMessageView iSendMessageView, Context mContext, Activity mActivity){
        this.sendMessageView = iSendMessageView;
        this.mContext = mContext;
        this.userBiz = new LoginBiz();
        this.mActivity = mActivity;
    }

    /**
     * 用来显示刷新页面
     */
    public void showLoading(){
        sendMessageView.showLoading();
    }

    /**
     * 用来隐藏刷新页面
     */
    public void hideLoading(){
        sendMessageView.hideLoading();
    }

    /**
     * 进入注册页面
     */
    public void toRegisterActivity(){
        sendMessageView.toRegisterPage();
    }

    /**
     * 从上衣页面继承电话号码
     */
    public void initPhoneNumber(){
        String phoneNumber = sendMessageView.getPhoneNumber();
        sendMessageView.setPhoneNumber(phoneNumber);
    }

    /**
     * 提交验证码并获得反馈结果
     * @param country 国家电话号头（如中国86)
     * @param phoneNumber 电话号码
     */
    public void submitInfo(String country, String phoneNumber, String code, Context mContext, Activity mActivity) throws InterruptedException {
        userBiz.submitInfo(country,phoneNumber, code, mContext, mActivity);
    }
}
