package priv.zxy.moonstep.login.view;

import priv.zxy.moonstep.login.module.bean.ErrorCode;

/**
 *  Created by Zxy on 2018/9/21
 */

public interface IVerifyPhoneView {

    void showLoading();

    void hideLoading();

    String getPhoneNumber();

    void toLoginActivity();

    void toSendMessageActivity();

    void finishActivitySelf();

    void showSuccessTip();

    void showFailTip(ErrorCode errorCode);
}
