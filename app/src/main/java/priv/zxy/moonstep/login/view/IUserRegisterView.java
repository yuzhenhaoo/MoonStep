package priv.zxy.moonstep.login.view;

import priv.zxy.moonstep.kernel.bean.ErrorCode;

/**
 *  Created by Zxy on 2018/9/21
 */

public interface IUserRegisterView {

    String getUserName();

    void showLoading();

    void hideLoading();

    void toLoginActivity();

    void toMainActivity();

    String getPhoneNumber();

    void getData();

    void finishActivitySelf();

    void showErrorTip(ErrorCode errorCode);

}
