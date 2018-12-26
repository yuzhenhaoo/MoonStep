package priv.zxy.moonstep.framework.authority.authorities.function;

import priv.zxy.moonstep.framework.authority.base.AbstractFunctionAuthority;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 功能代码:600002
 *     功能描述：强制添加对方为好友
 **/

public class ForceAddingFriendFunction extends AbstractFunctionAuthority {

    private static final String CODE = "600002";
    private static final String DESCRIPTION = "强制添加对方为好友";

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
         * 这里写强制添加好友的逻辑实现
         */
    }
}
