package priv.zxy.moonstep.login.module.biz;

import priv.zxy.moonstep.kernel.bean.ErrorCode;

/**
 * Created by Zxy on 2018/9/20
 */

public interface OnLoginListener {
    /**
     * 成功和失败意味着必须要和View层实现共联
     * 那么如果要和View层实现共联的话，就意味着必须要将该接口的实现内容写在presenter层
     */
    void LoginSuccess();

    void LoginFail(ErrorCode errorCode);
}
