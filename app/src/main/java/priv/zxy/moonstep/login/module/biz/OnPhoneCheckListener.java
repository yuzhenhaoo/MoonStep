package priv.zxy.moonstep.login.module.biz;

import priv.zxy.moonstep.data.bean.ErrorCodeEnum;

public interface OnPhoneCheckListener {
    void phoneIsExisted();

    void phoneIsNotExisted();

    void getErrcodeTips(ErrorCodeEnum errorCode);
}
