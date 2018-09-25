package priv.zxy.moonstep.login.module.biz;

import priv.zxy.moonstep.login.module.bean.ErrorCode;

public interface OnChangePasswordListener {

    void changePasswordSuccess() throws InterruptedException;

    void changePasswordFail(ErrorCode errorCode);
}
