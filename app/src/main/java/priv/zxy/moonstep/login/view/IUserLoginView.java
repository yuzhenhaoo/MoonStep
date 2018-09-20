package priv.zxy.moonstep.login.view;

public interface IUserLoginView {

    String getUserName();

    String getUserPassword();

    void clearUserName();

    void clearUserPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity();

    void showFailedError(int code);
}
