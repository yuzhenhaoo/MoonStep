package priv.zxy.moonstep.login.view;

import priv.zxy.moonstep.util.SharedPreferencesUtil;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;

/**
 *  Created by Zxy on 2018/9/21
 */

public interface IUserLoginView {

    String getUserPhoneNumber();

    String getUserPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity();

    void toConfirmPhoneActivity();

    void toForgetPasswordActivity();

    void initAccount(SharedPreferencesUtil preference);

    void initPassword(SharedPreferencesUtil preference);

    void showErrorTip(ErrorCodeEnum errorCode);

    void handleSendMessage();

    void setLoginPreferences(String username, String password);
}
