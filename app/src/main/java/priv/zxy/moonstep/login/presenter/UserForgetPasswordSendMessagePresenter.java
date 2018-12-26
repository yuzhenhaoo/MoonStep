package priv.zxy.moonstep.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;

import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.login.module.biz.IUser;
import priv.zxy.moonstep.login.module.biz.OnPhoneCheckListener;
import priv.zxy.moonstep.login.module.biz.UserBiz;
import priv.zxy.moonstep.login.view.IForgetPasswordSendMessageView;

/**
 * Created by Zxy on 2018/9/20
 */

public class UserForgetPasswordSendMessagePresenter {

    private IUser userBiz;
    private IForgetPasswordSendMessageView forgetPasswordSendMessageView;//创建与LogUtilinView交互的View对象
    private Activity mActivity;
    private Context mContext;

    public UserForgetPasswordSendMessagePresenter(IForgetPasswordSendMessageView forgetPasswordSendMessageView, Activity mActivity, Context mContext){
        this.forgetPasswordSendMessageView = forgetPasswordSendMessageView;
        this.userBiz = new UserBiz();
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    public void toChangePasswordActivity(String phoneNumber, Context mContext, Activity mActivity) throws InterruptedException {
        userBiz.judgeCanJumpToChangePasswordActivity(phoneNumber,  new OnPhoneCheckListener() {
            @Override
            public void phoneIsExisted() {
                forgetPasswordSendMessageView.toChangePasswordActivity();
            }

            @Override
            public void phoneIsNotExisted() {
                new Thread(() -> {
                    Looper.prepare();
                    forgetPasswordSendMessageView.showErrorTip();
                    Looper.loop();
                }).start();
            }

            @Override
            public void getErrcodeTips(ErrorCodeEnum errorCode) {
                new Thread(() -> {
                    Looper.prepare();
                    forgetPasswordSendMessageView.showErrorTip();
                    Looper.loop();
                }).start();
            }
        });
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
