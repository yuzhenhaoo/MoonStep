package priv.zxy.moonstep.login.module.biz;

import priv.zxy.moonstep.kernel.bean.ErrorCode;

public interface OnPhoneCheckListener {
    void phoneIsExisted();

    void phoneIsNotExisted();

    void getErrcodeTips(ErrorCode errorCode);
}
