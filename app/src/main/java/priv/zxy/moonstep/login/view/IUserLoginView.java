package priv.zxy.moonstep.login.view;

import priv.zxy.moonstep.utils.SharedPreferencesUtil;
import priv.zxy.moonstep.kernel.bean.ErrorCode;

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

    void showSuccessTip();

    void showErrorTip(ErrorCode errorCode);

    void handleSendMessage();

    void fixLogUtilinPreferences(String username, String password);
}
