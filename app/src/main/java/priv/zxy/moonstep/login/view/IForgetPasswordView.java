package priv.zxy.moonstep.login.view;

import android.app.Activity;
import android.content.Context;

/**
 *  Created by Zxy on 2018/9/21
 */

public interface IForgetPasswordView {

    void showLoading();

    void hideLoading();

    String getPhoneNumber();

    String getCodeNumber();

    String getPassword();

    String getConfirmPassword();

    void fixPassword(String country, String phoneNumber, String codeNum, String password, String confirmPassword);

    void toLoginActivity();
}
