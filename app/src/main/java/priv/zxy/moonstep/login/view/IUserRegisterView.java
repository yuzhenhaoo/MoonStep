package priv.zxy.moonstep.login.view;

import priv.zxy.moonstep.data.bean.ErrorCodeEnum;

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

    void showErrorTip(ErrorCodeEnum errorCode);

}
