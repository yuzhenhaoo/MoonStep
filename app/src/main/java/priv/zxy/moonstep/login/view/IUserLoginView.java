package priv.zxy.moonstep.login.view;

import priv.zxy.moonstep.Utils.SharedPreferencesUtil;

public interface IUserLoginView {

    String getUserName();

    String getUserPassword();

    void clearUserName();

    void clearUserPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity();

    void toConfirmPhoneActivity();

    void toForgetPasswordActivity();

    void showFailedError(int code);

    void initAccount(SharedPreferencesUtil preference);

    void initPassword(SharedPreferencesUtil preference);
}
