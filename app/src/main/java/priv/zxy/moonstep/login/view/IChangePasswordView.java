package priv.zxy.moonstep.login.view;

import priv.zxy.moonstep.login.module.bean.ErrorCode;

public interface IChangePasswordView {

    void showLoading();

    void hideLoading();

    String getPhoneNumber();

    String getPassword();

    String getConfirmPassword();

    void toLoginActivity();

    void showSuccessTip();

    void showErrorTip(ErrorCode errorCode);
}
