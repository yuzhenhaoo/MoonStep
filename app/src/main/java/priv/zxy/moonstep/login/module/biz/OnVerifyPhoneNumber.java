package priv.zxy.moonstep.login.module.biz;

/**
 *  Created by Zxy on 2018/9/21
 */

import priv.zxy.moonstep.data.bean.ErrorCodeEnum;

public interface OnVerifyPhoneNumber {
    void verifySuccess();

    void verifyFail(ErrorCodeEnum errorCode);
}
