package priv.zxy.moonstep.login.view;

import priv.zxy.moonstep.kernel.bean.ErrorCode;

/**
 * Created by Zxy on 2018/9/20
 */

public interface IChangePasswordView {

    void showLoading();

    void hideLoading();

    String getPhoneNumber();

    String getPassword();

    String getConfirmPassword();

    void toLogUtilinActivity();

    void showSuccessTip();

    void showErrorTip(ErrorCode errorCode);
}
