package priv.zxy.moonstep.login.view;

import priv.zxy.moonstep.login.module.User;

/**
 * Created by Zxy on 2018/9/20
 */
public interface ILoginView {
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();
}
