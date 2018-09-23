package priv.zxy.moonstep.login.view;

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

    void showFailedError(int code);

    String getPhoneNumber();

    void getData();

    void finishActivitySelf();

}
