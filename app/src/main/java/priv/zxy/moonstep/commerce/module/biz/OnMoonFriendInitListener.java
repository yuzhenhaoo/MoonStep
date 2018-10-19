package priv.zxy.moonstep.commerce.module.biz;

import priv.zxy.moonstep.kernel.bean.ErrorCode;

public interface OnMoonFriendInitListener {

    void initSuccess();

    void initFail(ErrorCode errorCode);
}
