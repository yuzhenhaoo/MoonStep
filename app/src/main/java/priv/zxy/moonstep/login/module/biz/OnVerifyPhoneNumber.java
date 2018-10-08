package priv.zxy.moonstep.login.module.biz;

/**
 *  Created by Zxy on 2018/9/21
 */

import priv.zxy.moonstep.kernel_data.bean.ErrorCode;

public interface OnVerifyPhoneNumber {
    public void verifySuccess();

    public void verifyFail(ErrorCode errorCode);
}
