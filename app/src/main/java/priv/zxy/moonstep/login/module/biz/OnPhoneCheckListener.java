package priv.zxy.moonstep.login.module.biz;

import priv.zxy.moonstep.data.bean.ErrorCode;

public interface OnPhoneCheckListener {
    void phoneIsExisted();

    void phoneIsNotExisted();

    void getErrcodeTips(ErrorCode errorCode);
}
