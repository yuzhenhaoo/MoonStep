package priv.zxy.moonstep.framework.good;

import priv.zxy.moonstep.utils.dbUtils.UserGoodUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 抽象物品类
 **/

public abstract class AbstractGood {

    abstract void getUserGoods(UserGoodUtil.CallBack callBack, String phoneNumber);

    abstract void setUserGood(String phoneNumber, String goodCode, int number);
}
