package priv.zxy.moonstep.login.view;

import priv.zxy.moonstep.login.module.bean.ErrorCode;

public interface IForgetPasswordSendMessageView {

    void toChangePasswordActivity();

    void finishActivitySelf();

    void sendVoiceCode();

    String getPhoneNumber();

    void showLoading();

    void hideLoading();

    void showSuccessTip();

    void showErrorTip();
}
