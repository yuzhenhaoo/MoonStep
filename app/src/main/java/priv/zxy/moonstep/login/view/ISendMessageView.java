package priv.zxy.moonstep.login.view;

public interface ISendMessageView {

    void toRegisterPage();

    void finishActivitySelf();

    void sendVoiceCode();

    String getPhoneNumber();

    void setPhoneNumber();

    void submitInfo(String country, String phone);

    void showLoading();

    void hideLoading();
}
