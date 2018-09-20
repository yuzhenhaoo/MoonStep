package priv.zxy.moonstep.login.presenter;

import priv.zxy.moonstep.login.module.User;

/**
 * Created by Zxy on 2018/9/20
 */
public interface OnLoginListener {
    void loginSuccess (User user);
    void loginFailed();
}
