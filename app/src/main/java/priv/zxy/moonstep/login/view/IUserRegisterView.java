package priv.zxy.moonstep.login.view;

import priv.zxy.moonstep.login.module.bean.ErrorCode;

/**
 *  Created by Zxy on 2018/9/21
 */

public interface IUserRegisterView {

    String getUserName();

    String getUserPassWord();

    void clearUserName();

    void clearUserPassword();

    void clearUserConfirmPassword();

    void showLoading();

    void hideLoading();

    void toLoginActivity();

    void toMainActivity();

    String getPhoneNumber();

    void getData();

    void finishActivitySelf();

    void showUserNameSuccessTip();

    void showUserNameFailTip(ErrorCode errorCode);

    void showRegisterSuccessTip();

    void showRegisterFailTip(ErrorCode errorCode);

}
