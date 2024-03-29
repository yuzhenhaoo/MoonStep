package priv.zxy.moonstep.login.presenter;

import android.os.Looper;

import priv.zxy.moonstep.login.module.biz.LoginBiz;
import priv.zxy.moonstep.login.module.biz.OnLoginListener;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.login.module.biz.ILogin;
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
    private ILogin userBiz;
    private IUserLoginView userLoginView;//创建与LoginView交互的View对象

    public UserLoginPresenter(IUserLoginView userLoginView){
        this.userLoginView = userLoginView;
        this.userBiz = new LoginBiz();
    }

    /**
     * 这个函数里实现View层和Module层的交互
     */
    public void Login() throws InterruptedException {
        userBiz.doLogin(userLoginView.getUserPhoneNumber(), userLoginView.getUserPassword(), new OnLoginListener() {
            @Override
            public void LoginSuccess() {
                userLoginView.setLoginPreferences(userLoginView.getUserPhoneNumber(), userLoginView.getUserPassword());
                userLoginView.handleSendMessage();
                userLoginView.toMainActivity();
            }

            @Override
            public void LoginFail(final ErrorCodeEnum errorCode) {
                userLoginView.handleSendMessage();
                new Thread(() -> {
                    Looper.prepare();
                    userLoginView.showErrorTip(errorCode);
                    Looper.loop();
                }).start();
            }
        });
    }

    /**
     * 用来显示刷新页面
     */
    public void showLoading(){
        userLoginView.showLoading();
    }

    /**
     * 用来隐藏刷新页面
     */
    public void hideLoading(){
        userLoginView.hideLoading();
    }

    /**
     * 进入ForgetPasswordActivity
     */
    public void toForgetPasswordActivity(){
        userLoginView.toForgetPasswordActivity();
    }

}
