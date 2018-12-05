package priv.zxy.moonstep.framework.authority.factory;

import java.util.HashMap;

import priv.zxy.moonstep.framework.authority.base.AbstractAuthority;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 权限的工厂接口（利用工厂模式封装对于子类的选择过程）
 **/

abstract class AbstractFactory {

    static HashMap<String, String> maps;//maps用来存储权限的code-className，目的是为了在子Factory中实现反射，但是maps的存储是一个问题，可以有两种方案

    /**
     * 我们在构造函数中初始化maps
     */
    AbstractFactory(){
        maps = new HashMap<>();
        maps.put("600001", "ForceAddingFriend");
        maps.put("600002", "SendFriendRequestFunction");
    }

    abstract AbstractAuthority createAuthority(String code);


}
