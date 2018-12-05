package priv.zxy.moonstep.framework.authority.authorities;

import priv.zxy.moonstep.framework.authority.base.FunctionAuthority;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 强制添加好友功能
 **/

public class ForceAddingFriend extends FunctionAuthority {

    @Override
    public void setAuthorityCode(String authorityCode) {
        super.setAuthorityCode("600002");
    }

    @Override
    public void setAuthorityDescription(String authorityDescription) {
        super.setAuthorityDescription("强制添加对方为好友");
    }

    @Override
    public void operator() {
        /**
         * 这里写强制添加好友的逻辑实现
         */
    }
}
