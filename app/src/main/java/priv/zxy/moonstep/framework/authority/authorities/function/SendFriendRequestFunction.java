package priv.zxy.moonstep.framework.authority.authorities.function;

import priv.zxy.moonstep.framework.authority.base.AbstractFunctionAuthority;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 功能代码:600001
 *     功能描述：向指定好友发送一此好友请求
 **/

public class SendFriendRequestFunction extends AbstractFunctionAuthority {

    private static final String CODE = "600001";
    private static final String DESCRIPTION = "向指定好友发送一次好友请求";

    @Override
    public void setAuthorityCode(String authorityCode) {
        super.setAuthorityCode(CODE);
    }

    @Override
    public void setAuthorityDescription(String authorityDescription) {
        super.setAuthorityDescription(DESCRIPTION);
    }

    @Override
    public void operator() {
        /**
         * 在这里实现发送一次好友请求的逻辑实现
         */
    }
}
