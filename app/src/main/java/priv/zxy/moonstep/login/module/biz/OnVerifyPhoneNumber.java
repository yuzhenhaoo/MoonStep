package priv.zxy.moonstep.login.module.biz;

import priv.zxy.moonstep.login.module.bean.ErrorCode;

public interface OnVerifyPhoneNumber {
    public void verifySuccess();

    public void verifyFail(ErrorCode errorCode);
}
