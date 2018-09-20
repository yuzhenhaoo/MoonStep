package priv.zxy.moonstep.login.presenter;

import android.os.Handler;

import priv.zxy.moonstep.login.module.IUser;
import priv.zxy.moonstep.login.module.User;
import priv.zxy.moonstep.login.view.ILoginView;

/**
 * Created by Zxy on 2018/9/20
 */

public class LoginPresenterComl implements ILoginPresenter {
    private IUser iUser;
    private ILoginView iLoginView;
    private Handler mHandler = new Handler();

    public LoginPresenterComl(ILoginView loginView){
        this.iLoginView = loginView;
    }

    @Override
    public void clear() {

    }

    @Override
    public void doLogin(String userName, String userPassword) {
        //这里进行网络的请求操作
    }

    @Override
    public void setProgressBarVisiblity(int visibility) {

    }
}
