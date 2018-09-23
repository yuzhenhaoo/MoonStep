package priv.zxy.moonstep.login.module.biz;

import priv.zxy.moonstep.login.module.bean.ErrorCode;

/**
 *  Created by Zxy on 2018/9/21
 */

public interface OnUserNameCheckListener {
    void success();

    void fail(ErrorCode errorCode);
}
