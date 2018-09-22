package priv.zxy.moonstep.login.view;

public interface IVerifyPhoneView {

    void showLoading();

    void hideLoading();

    String getPhoneNumber();

    void toLoginActivity();

    void toSendMessageActivity();
}
