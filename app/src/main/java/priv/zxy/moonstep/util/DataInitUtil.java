package priv.zxy.moonstep.util;

import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.adapter.AbstractAdapter;
import priv.zxy.moonstep.framework.good.GoodSelfInfo;
import priv.zxy.moonstep.framework.good.Props;
import priv.zxy.moonstep.framework.good.bean.Good;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.framework.user.UserSelfInfo;

/**
 * 创建人: Administrator
 * 创建时间: 2019/1/6
 * 描述:数据初始化类
 *      对当前应用中所有需要进入前申请的数据做一个封装，目的是为了让开发人员便捷的知道到底哪些数据是需要初始化的。
 **/

public class DataInitUtil {

    /**
     * 初始化用户的个人数据
     * @param user 用户的信息类
     */
    public static void initUserSelfInfo(User user) {
        UserSelfInfo.getInstance().setMySelf(user);
    }

    /**
     * 初始化用户的物品数据
     */
    public static void initGoodSelfInfo() {
        new Props().getUserGoods(gs -> GoodSelfInfo.getInstance().addAll(gs), UserSelfInfo.getInstance().getMySelf().getPhoneNumber());
    }
}
