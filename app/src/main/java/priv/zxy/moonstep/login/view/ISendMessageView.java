package priv.zxy.moonstep.login.view;

/**
 *  Created by Zxy on 2018/9/21
 */

public interface ISendMessageView {

    void toRegisterPage();

    void finishActivitySelf();

    void sendVoiceCode();

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    void showLoading();

    void hideLoading();
}