package priv.zxy.moonstep.login.module.biz;

/**
 *  Created by Zxy on 2018/9/21
 */

import priv.zxy.moonstep.data.bean.ErrorCode;

public interface OnVerifyPhoneNumber {
    void verifySuccess();

    void verifyFail(ErrorCode errorCode);
}
