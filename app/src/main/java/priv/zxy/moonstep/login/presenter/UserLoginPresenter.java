package priv.zxy.moonstep.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;

import priv.zxy.moonstep.login.module.biz.OnLoginListener;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.login.module.biz.IUser;
import priv.zxy.moonstep.login.module.biz.UserBiz;
import priv.zxy.moonstep.login.view.IUserLoginView;

/**
 * Created by Zxy on 2018/9/21
 *
 * LogUtilinPresenter：
 * 用来处理LogUtilin逻辑的Module和View层之间的交互关系
 * 处理的方式是通过Module层和View层的接口
 * 通过在View层调用LogUtilinPresenter的对象来简介的调用LogUtilinPresenter的方法来实现对View层和Module层的分离
 * 需要注意的是：View层可以在事件监听中调用自身复写的方法
 * 但是为了保证代码结构的一致性，我们依旧在View层的事件舰艇中通过LogUtilinPresenter的对象来间接的调用View层本身的方法
 * 具体可以看看MVP架构的设计图，Presenter和Module间相互进行的交互，Presenter和View层间相互进行的交互
 * 却没有View层和自身进行的交互，尽管这一点是切实可行的
 */

public class UserLoginPresenter {
    private IUser userBiz;
    private IUserLoginView userLogUtilinView;//创建与LogUtilinView交互的View对象
    private Activity mActivity;
    private Context mContext;

    public UserLoginPresenter(IUserLoginView userLogUtilinView, Activity mActivity, Context mContext){
        this.userLogUtilinView = userLogUtilinView;
        this.userBiz = new UserBiz();
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    /**
     * 这个函数里实现View层和Module层的交互
     */
    public void LogUtilin() throws InterruptedException {
        userBiz.doLogUtilin(userLogUtilinView.getUserPhoneNumber(), userLogUtilinView.getUserPassword(), new OnLoginListener() {
            @Override
            public void LogUtilinSuccess() {
                userLogUtilinView.fixLogUtilinPreferences(userLogUtilinView.getUserPhoneNumber(), userLogUtilinView.getUserPassword());
                userLogUtilinView.handleSendMessage();
                userLogUtilinView.toMainActivity();
            }

            @Override
            public void LogUtilinFail(final ErrorCode errorCode) {
                userLogUtilinView.handleSendMessage();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        userLogUtilinView.showErrorTip(errorCode);
                        Looper.loop();
                    }
                }).start();
            }
        });
    }

    /**
     * 初始化用户登陆时候的用户名和密码
     * 利用Preference的工具类来实现自动填充
     * @param preference SharedPreferences工具类对象
     */
    public void initAccountAndPassword(SharedPreferencesUtil preference){
        userLogUtilinView.initAccount(preference);
        userLogUtilinView.initPassword(preference);
    }

    /**
     * 用来显示刷新页面
     */
    public void showLoading(){
        userLogUtilinView.showLoading();
    }

    /**
     * 用来隐藏刷新页面
     */
    public void hideLoading(){
        userLogUtilinView.hideLoading();
    }

    /**
     * 进入ConfrimPhoneActivity
     */
    public void toConfirmPhoneActivity(){
        userLogUtilinView.toConfirmPhoneActivity();
    }

    /**
     * 进入ForgetPasswordActivity
     */
    public void toForgetPasswordActivity(){
        userLogUtilinView.toForgetPasswordActivity();
    }

}
