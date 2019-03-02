package priv.zxy.moonstep.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.login.module.biz.ILogin;
import priv.zxy.moonstep.login.module.biz.LoginBiz;
import priv.zxy.moonstep.login.module.biz.OnVerifyPhoneNumber;
import priv.zxy.moonstep.login.view.IVerifyPhoneView;

/**
 *  Created by Zxy on 2018/9/21
 */

public class UserVerifyPhoneNumberPresenter {
    private ILogin userBiz;
    private IVerifyPhoneView verifyPhoneView;//创建与ConfrirmView交互的View对象
    private Activity mActivity;
    private Context mContext;

    public UserVerifyPhoneNumberPresenter(IVerifyPhoneView confirmPhoneView, Activity mActivity, Context mContext){
        this.verifyPhoneView = confirmPhoneView;
        this.userBiz = new LoginBiz();
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    /**
     * 实质上是用来返回登陆页面的
     */
    public void toLogUtilinActivity(){
        verifyPhoneView.toLogUtilinActivity();
    }

    /**
     * this 被用来跳转到一个国家列表
     * 这个国家列表可以用RecyclerView嵌入到Activity中来做
     * 如果选中相应的国家，记得调用View的相应方法将选中国家的手机头（如中国的86)改变到当前View的页面
     */
    public void toCountrySelectedActivity(){
        Toast.makeText(mActivity, "CountrySelected页面还没有写好", Toast.LENGTH_SHORT).show();
    }

    /**
     * doVerifyPhoneNumber是用来做Module和View层交互的一个Presenter的函数
     * 它本质上要实现ConfrimPhoneActivity的一个主要逻辑功能：对于电话号码合法性的判断
     */
    public void doVerifyPhoneNumber() throws InterruptedException {
        userBiz.doVerifyPhoneNumber(verifyPhoneView.getPhoneNumber(), new OnVerifyPhoneNumber() {
            @Override
            public void verifySuccess() {
                new Thread(() -> verifyPhoneView.showSuccessTip()).start();
                verifyPhoneView.toSendMessageActivity();
            }

            @Override
            public void verifyFail(final ErrorCodeEnum errorCode) {
                new Thread(() -> verifyPhoneView.showFailTip(errorCode)).start();
            }
        });
    }
}
