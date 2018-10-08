package priv.zxy.moonstep.moonstep_palace.moon_friend.module.biz;

import priv.zxy.moonstep.kernel_data.bean.ErrorCode;

public interface OnInitMoonFriendListener {

    void initSuccess();

    void initFail(ErrorCode errorCode);
}
