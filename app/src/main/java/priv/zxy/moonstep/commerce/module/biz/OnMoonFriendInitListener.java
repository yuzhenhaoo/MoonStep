package priv.zxy.moonstep.commerce.module.biz;

import priv.zxy.moonstep.data.bean.ErrorCodeEnum;

public interface OnMoonFriendInitListener {

    void initSuccess();

    void initFail(ErrorCodeEnum errorCode);
}
