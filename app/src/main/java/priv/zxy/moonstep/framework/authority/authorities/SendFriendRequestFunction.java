package priv.zxy.moonstep.framework.authority.authorities;

import priv.zxy.moonstep.framework.authority.FunctionAuthority;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 发送一次好友请求的功能
 **/

public class SendFriendRequestFunction extends FunctionAuthority {

    @Override
    public void setAuthorityCode(String authorityCode) {
        super.setAuthorityCode("600001");
    }

    @Override
    public void setAuthorityDescription(String authorityDescription) {
        super.setAuthorityDescription("向指定好友发送一次好友请求");
    }

    @Override
    public void operator() {
        /**
         * 在这里实现发送一次好友请求的逻辑实现
         */
    }
}
