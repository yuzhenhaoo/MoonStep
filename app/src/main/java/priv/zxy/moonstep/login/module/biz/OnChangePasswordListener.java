package priv.zxy.moonstep.login.module.biz;

import priv.zxy.moonstep.kernel_data.bean.ErrorCode;

/**
 * Created by Zxy on 2018/9/20
 */

public interface OnChangePasswordListener {

    void changePasswordSuccess() throws InterruptedException;

    void changePasswordFail(ErrorCode errorCode);
}
