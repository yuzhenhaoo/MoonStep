package priv.zxy.moonstep.login.view;

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

}
